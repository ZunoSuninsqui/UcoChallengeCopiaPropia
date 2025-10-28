
import React, { createContext, useCallback, useContext, useEffect, useMemo, useRef, useState } from 'react';
import { Routes, Route, Navigate, useNavigate, useLocation } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useAuth0 } from '@auth0/auth0-react';

import { setupInterceptors } from './services/axios-interceptor';
import { auth0Config, buildLoginScope, buildResourceAuthorizationParams, resolveRoleClaimCandidates } from './config/auth0';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import RegisterUserPage from './pages/RegisterUserPage';
import UserSearchPage from './pages/UserSearchPage';

const AuthContext = createContext({
  user: null,
  loginWithCredentials: async () => { },
  logout: () => { },
  isAdmin: false,
});

export const useAuth = () => useContext(AuthContext);

function PrivateRoute({ children }) {
  const { isAuthenticated, isLoading } = useAuth0();
  const { isAdmin } = useAuth();

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  if (!isAuthenticated || !isAdmin) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default function App() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();
  const hasShownRoleErrorRef = useRef(false);

  const {
    user: auth0User,
    isAuthenticated,
    isLoading,
    loginWithRedirect,
    logout: auth0Logout,
    getAccessTokenSilently,
    getIdTokenClaims,
    error: auth0Error,
  } = useAuth0();

  const resourceAuthorizationParams = useMemo(() => buildResourceAuthorizationParams(), []);
  const loginScope = useMemo(() => buildLoginScope(), []);
  const roleClaimCandidates = useMemo(() => resolveRoleClaimCandidates(), []);

  useEffect(() => {
    if (auth0Error) {
      console.error('Auth0 error:', auth0Error);
    }
  }, [auth0Error]);

  useEffect(() => {
    setupInterceptors(async () => {
      try {
        return await getAccessTokenSilently({
          authorizationParams: resourceAuthorizationParams,
        });
      } catch (error) {
        if (import.meta.env.DEV) {
          console.warn('No fue posible obtener el token de acceso:', error);
        }
        return null;
      }
    });
  }, [getAccessTokenSilently, resourceAuthorizationParams]);

  const decodeToken = useCallback((token) => {
    if (!token) {
      return null;
    }

    try {
      const [, payload] = token.split('.');
      if (!payload) {
        return null;
      }

      const normalized = payload.replace(/-/g, '+').replace(/_/g, '/');
      const padded = normalized + '='.repeat((4 - (normalized.length % 4)) % 4);

      const base64Decode = (value) => {
        if (typeof atob === 'function') {
          return atob(value);
        }
        if (typeof Buffer === 'function') {
          return Buffer.from(value, 'base64').toString('binary');
        }
        throw new Error('No hay un decodificador Base64 disponible.');
      };

      const binary = base64Decode(padded);
      const json = decodeURIComponent(
        binary
          .split('')
          .map((char) => `%${`00${char.charCodeAt(0).toString(16)}`.slice(-2)}`)
          .join(''),
      );

      return JSON.parse(json);
    } catch (error) {
      console.warn('No se pudo decodificar el token JWT:', error);
      return null;
    }
  }, []);

  const collectRoles = useCallback((...sources) => {
    const roles = new Set();

    sources
      .filter(Boolean)
      .forEach((claims) => {
        roleClaimCandidates.forEach((claimName) => {
          const value = claims[claimName];
          if (!value) return;

          if (Array.isArray(value)) {
            value.filter((item) => typeof item === 'string').forEach((item) => roles.add(item));
            return;
          }

          if (typeof value === 'string') {
            value
              .split(/\s+/)
              .filter(Boolean)
              .forEach((item) => roles.add(item));
          }
        });
      });

    return Array.from(roles);
  }, [roleClaimCandidates]);

  useEffect(() => {
    if (isLoading) {
      return;
    }

    const syncSession = async () => {
      if (isAuthenticated && auth0User) {
        try {
          const [idToken, accessToken] = await Promise.all([
            getIdTokenClaims(),
            getAccessTokenSilently({ authorizationParams: resourceAuthorizationParams }),
          ]);

          const idTokenPayload = idToken ? { ...idToken } : null;
          const decodedIdToken = idToken?.__raw ? decodeToken(idToken.__raw) : null;
          const decodedAccessToken = decodeToken(accessToken);

          const roles = collectRoles(idTokenPayload, decodedIdToken, decodedAccessToken);
          const hasAdminRole = roles.includes(auth0Config.adminRole);

          if (!hasAdminRole) {
            if (!hasShownRoleErrorRef.current) {
              hasShownRoleErrorRef.current = true;
              const message = roles.length === 0
                ? 'Acceso denegado: el token recibido no contiene el claim de roles configurado en Auth0.'
                : 'Acceso denegado: tu cuenta no tiene el rol requerido para usar el panel.';
              setTimeout(() => window.alert(message), 250);
            }

            setUser(null);
            await auth0Logout?.({ logoutParams: { returnTo: window.location.origin } });
            if (location.pathname !== '/login') {
              navigate('/login', { replace: true });
            }
            return;
          }

          setUser({
            id: auth0User.sub,
            name: auth0User.name || auth0User.nickname || 'Usuario',
            email: auth0User.email || '',
            role: auth0Config.adminRole,
            roles,
            provider: auth0User.sub?.split('|')[0] || 'auth0',
            isAdmin: true,
          });

          if (location.pathname === '/login' || location.pathname === '/') {
            navigate('/dashboard', { replace: true });
          }

          if (import.meta.env.DEV) {
            console.debug('Auth0 session sincronizada', { roles });
          }
        } catch (error) {
          console.error('No fue posible sincronizar la sesión de Auth0:', error);
        }
      } else {
        setUser(null);
        hasShownRoleErrorRef.current = false;
        if (location.pathname !== '/login' && location.pathname !== '/') {
          navigate('/login', { replace: true });
        }
      }
    };

    syncSession();
  }, [
    auth0Logout,
    auth0User,
    collectRoles,
    decodeToken,
    getAccessTokenSilently,
    getIdTokenClaims,
    isAuthenticated,
    isLoading,
    location.pathname,
    navigate,
    resourceAuthorizationParams,
  ]);

  const loginWithCredentials = useCallback(async ({ email }) => {
    try {
      await loginWithRedirect({
        authorizationParams: {
          redirect_uri: window.location.origin,
          login_hint: email,
          scope: loginScope,
          ...(auth0Config.audience ? { audience: auth0Config.audience } : {}),
        },
      });
    } catch (error) {
      console.error('Error iniciando sesión con Auth0:', error);
    }
  }, [loginWithRedirect, loginScope]);

  const logout = useCallback(() => {
    try {
      auth0Logout?.({ logoutParams: { returnTo: window.location.origin } });
    } finally {
      setUser(null);
      navigate('/login', { replace: true });
    }
  }, [auth0Logout, navigate]);

  const authValue = useMemo(() => ({
    user,
    loginWithCredentials,
    logout,
    isAdmin: Boolean(user?.isAdmin),
  }), [loginWithCredentials, logout, user]);

  return (
    <AuthContext.Provider value={authValue}>
      <div className="app-shell">
        <header className="topbar card">
          <div className="brand">
            <div className="logo" aria-hidden />
            <div>
              <div className="title">Admin Panel</div>
              <div className="text-xs muted">UCO Challenge</div>
            </div>
          </div>

          <div className="header-actions">
            {user ? (
              <>
                <div className="badge">{user.name}</div>
                <button className="btn btn-ghost" onClick={logout} title="Cerrar sesión">
                  Cerrar sesión
                </button>
              </>
            ) : (
              <div className="badge">No autorizado</div>
            )}
          </div>
        </header>

        <main className="container page">
          <motion.div
            initial={{ opacity: 0, y: 6 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.45 }}
          >
            <section style={{ minHeight: 520 }} className="card">
              <Routes>
                <Route path="/" element={<Navigate to="/login" replace />} />
                <Route path="/login" element={<LoginPage />} />
                <Route
                  path="/dashboard"
                  element={(
                    <PrivateRoute>
                      <DashboardPage />
                    </PrivateRoute>
                  )}
                />
                <Route
                  path="/register-user"
                  element={(
                    <PrivateRoute>
                      <RegisterUserPage />
                    </PrivateRoute>
                  )}
                />
                <Route
                  path="/users"
                  element={(
                    <PrivateRoute>
                      <UserSearchPage />
                    </PrivateRoute>
                  )}
                />
                <Route
                  path="*"
                  element={(
                    <div className="empty-state">
                      <h3 className="h1">Página no encontrada</h3>
                      <p className="muted">Revisa la URL o vuelve al panel.</p>
                    </div>
                  )}
                />
              </Routes>
            </section>
          </motion.div>
        </main>
      </div>
    </AuthContext.Provider>
  );
}
