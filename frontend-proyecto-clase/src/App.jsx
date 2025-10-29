/* eslint-disable react-refresh/only-export-components */


import React, { createContext, useContext, useState, useEffect } from 'react';
import { Routes, Route, Navigate, useNavigate, useLocation } from 'react-router-dom';
import { authService } from './services/auth';

import apiClient, { setupInterceptors } from './services/axios-interceptor';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import RegisterUserPage from './pages/RegisterUserPage';
import UserSearchPage from './pages/UserSearchPage';
import LandingPage from './pages/LandingPage';
import Navbar from './components/Navbar';

const AuthContext = createContext({
  user: null,
  isAuthenticated: false,
  loginWithCredentials: async () => {},
  logout: () => {},
  isAdmin: false,
});

export const useAuth = () => useContext(AuthContext);

function PrivateRoute({ children }) {
  const { isAuthenticated, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) return <div>Cargando...</div>;
  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
  return children;
}

export default function App() {
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (auth0Error) console.error('Auth0 error detectado:', auth0Error);
  }, [auth0Error]);

  useEffect(() => {
    setupInterceptors(async () => {
      try {
        const token = await getAccessTokenSilently({ authorizationParams: resourceAuthorizationParams });
        return token;
      } catch {
        return null;
      }
    });
  }, [getAccessTokenSilently, resourceAuthorizationParams]);

  const decodeToken = useCallback((token) => {
    if (!token) return null;
    try {
      const [, payload] = token.split('.');
      if (!payload) return null;
      const normalized = payload.replace(/-/g, '+').replace(/_/g, '/');
      const padded = normalized + '='.repeat((4 - (normalized.length % 4)) % 4);
      const base64Decode = (value) => {
        if (typeof atob === 'function') return atob(value);
        if (typeof globalThis !== 'undefined' && typeof globalThis.Buffer === 'function') return globalThis.Buffer.from(value, 'base64').toString('utf8');
        throw new Error('No base64 decoder available');
      };
      return JSON.parse(base64Decode(padded));
    } catch (err) {
      console.warn('decodeToken failed', err);
      return null;
    }
  }, []);

  const collectRoles = useCallback((idTokenPayload, decodedIdToken, decodedAccessToken) => {
    const roles = new Set();
    const sources = [idTokenPayload, decodedIdToken, decodedAccessToken];
    for (const src of sources) {
      if (!src) continue;
      for (const candidate of roleClaimCandidates) {
        const value = src[candidate];
        if (!value) continue;
        if (Array.isArray(value)) {
          value.filter((v) => typeof v === 'string').forEach((v) => roles.add(v));
        } else if (typeof value === 'string') {
          value.split(/\s+/).filter(Boolean).forEach((v) => roles.add(v));
        }
      }
    }
    return Array.from(roles);
  }, [roleClaimCandidates]);

  useEffect(() => {
    if (isLoading) return;
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

          const snapshot = {
            retrievedAt: new Date().toISOString(),
            idTokenRaw: idToken?.__raw || null,
            accessTokenRaw: accessToken || null,
            idTokenClaims: decodedIdToken,
            accessTokenClaims: decodedAccessToken,
          };
          setTokenSnapshot(snapshot);

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
            if (location.pathname !== '/login') navigate('/login', { replace: true });
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

          if (location.pathname === '/login' || location.pathname === '/') navigate('/dashboard', { replace: true });
        } catch (error) {
          console.error('No fue posible sincronizar la sesión de Auth0:', error);
        }
      } else {
        setUser(null);
        hasShownRoleErrorRef.current = false;
        hasVerifiedTokenRef.current = false;
        setTokenSnapshot(null);
        setTokenDiagnostics(null);
        if (location.pathname !== '/login' && location.pathname !== '/') navigate('/login', { replace: true });
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

  const verifyWithBackend = useCallback(async (reason = 'manual') => {
    const traceId = `${Date.now().toString(36)}-${Math.random().toString(36).slice(2, 8)}`;
    try {
      const response = await apiClient.get('/debug/token', { headers: { 'X-Debug-Trace': traceId } });
      const diagnostics = { traceId, reason, at: new Date().toISOString(), data: response.data };
      setTokenDiagnostics(diagnostics);
      return diagnostics;
    } catch (error) {
      console.error('Error verificando el token con el backend:', error);
      throw error;
    }
  }, []);

  useEffect(() => {
    if (user?.isAdmin && !hasVerifiedTokenRef.current) {
      hasVerifiedTokenRef.current = true;
      verifyWithBackend('post-login').catch((e) => console.warn('Verificación automática falló:', e));
    }
  }, [user, verifyWithBackend]);

  useEffect(() => {
    if (!import.meta.env.DEV) return;
    const currentDebug = window.__AUTH0_DEBUG__ || {};
    const debugApi = { ...currentDebug, tokens: tokenSnapshot, lastVerification: tokenDiagnostics, verifyNow: verifyWithBackend };
    window.__AUTH0_DEBUG__ = debugApi;
    return () => { if (window.__AUTH0_DEBUG__ === debugApi) delete window.__AUTH0_DEBUG__; };
  }, [tokenSnapshot, tokenDiagnostics, verifyWithBackend]);

  useEffect(() => {
    const checkAuth = async () => {
      const token = authService.getToken();
      if (token) {
        const currentUser = authService.getUser();
        if (currentUser) {
          setUser(currentUser);
        }
      }
      setIsLoading(false);
    };
    checkAuth();
  }, []);

  const loginWithCredentials = async (userData) => {
    setUser(userData);
    const from = location.state?.from?.pathname || '/dashboard';
    navigate(from, { replace: true });
  };

  const logout = async () => {
    try {
      await authService.logout();
    } finally {
      setUser(null);
      navigate('/login', { replace: true });
    }
  };

  useEffect(() => {
    // Manejo del callback de Okta
    const handleOktaCallback = async () => {
      const params = new URLSearchParams(location.search);
      const code = params.get('code');
      if (code) {
        try {
          const { user } = await authService.handleOktaCallback(code);
          await loginWithCredentials(user);
        } catch (error) {
          console.error('Error procesando callback de Okta:', error);
          navigate('/login', { replace: true });
        }
      }
    };

    if (location.pathname === '/callback') {
      handleOktaCallback();
    }
  }, [location]);

  const authValue = {
    user,
    isAuthenticated: Boolean(user),
    isAdmin: Boolean(user?.isAdmin),
    loginWithCredentials,
    logout,
    isLoading
  };

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  return (
    <AuthContext.Provider value={authValue}>
      <div className="min-h-screen bg-[#121212] text-gray-100">
        <Navbar />
        <main>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/callback" element={<div>Procesando autenticación...</div>} />
            <Route path="/register" element={<RegisterUserPage />} />
            <Route path="/search" element={<PrivateRoute><UserSearchPage /></PrivateRoute>} />
            <Route path="/dashboard" element={<PrivateRoute><DashboardPage /></PrivateRoute>} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </main>
      </div>
    </AuthContext.Provider>
  );
}
