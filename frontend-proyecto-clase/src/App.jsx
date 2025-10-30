import React, { createContext, useContext, useState } from 'react';
import { Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';

/* Pages (existentes en tu estructura) */
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import RegisterUserPage from './pages/RegisterUserPage';
import UserSearchPage from './pages/UserSearchPage';

/* Components (se asume que existan) */
import Navbar from './components/Navbar';

/**
 * AuthContext (frontend-only, simulado)
 * - Mantiene estado de "admin" conectado
 * - Provee funciones de login/logout para que los componentes del frontend las usen
 *
 * Nota: aquí NO se implementa autenticación real con Google/GitHub — sólo mapeos/placeholder.
 */
const AuthContext = createContext({
  user: null,
  loginWithCredentials: async () => {},
  loginWithOAuth: async () => {},
  logout: () => {}
});

export const useAuth = () => useContext(AuthContext);

/* Componente que protege rutas para administradores */
function PrivateRoute({ children }) {
  const { user } = useAuth();
  if (!user) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

/* Mock: usuario admin por defecto (null hasta login) */
export default function App() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  /**
   * Simula login con usuario/password (frontend-only).
   * Recibe un objeto { email, name } del LoginForm y setea el usuario.
   * Después de "loguear", navega al dashboard.
   */
  const loginWithCredentials = async ({ email, name }) => {
    // Validaciones / checks pueden hacerse en el LoginForm; aquí solo simulamos éxito.
    const adminUser = {
      id: 'admin-1',
      name: name || 'Administrador',
      email,
      role: 'admin',
    };
    setUser(adminUser);
    navigate('/dashboard', { replace: true });
  };

  /**
   * Simula login vía OAuth (Google / GitHub).
   * `provider` es 'google' | 'github' (mapeo visual en OAuthButtons).
   */
  const loginWithOAuth = async (provider) => {
    // placeholder: aquí llamarías a tu flujo OAuth real
    const oauthUser = {
      id: `oauth-${provider}-1`,
      name: provider === 'google' ? 'Admin Google' : 'Admin GitHub',
      email: provider === 'google' ? 'admin@google.com' : 'admin@github.com',
      provider,
      role: 'admin',
    };

    // simulación suave de animación/retardo
    await new Promise((res) => setTimeout(res, 350));
    setUser(oauthUser);
    navigate('/dashboard', { replace: true });
  };

  const logout = () => {
    setUser(null);
    navigate('/login', { replace: true });
  };

  const authValue = {
    user,
    loginWithCredentials,
    loginWithOAuth,
    logout,
  };

  return (
    <AuthContext.Provider value={authValue}>
      <div className="app-shell">
        {/* Topbar minimal */}
        <header className="topbar card">
          <div className="brand">
            <div className="logo" aria-hidden>
              {/* Letra/icono simple — puedes reemplazar por un SVG */}
              
            </div>
            <div>
              <div className="title">Admin Panel</div>
              <div className="text-xs muted">UCO Challenge</div>
            </div>
          </div>

          <div className="header-actions">
            {/* Si hay usuario, mostrar nombre y botón logout */}
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
            {/* Area principal con rutas (SE ELIMINA SIDEBAR/ASIDE completamente) */}
            <section style={{ minHeight: 520 }} className="card">
              <Routes>
                 <Route
                   path="/"
                   element={<Navigate to="/login" replace />}
                 />
 
                 <Route
                   path="/login"
                   element={<LoginPage />}
                 />
 
                 {/* Rutas protegidas */}
                 <Route
                   path="/dashboard"
                   element={
                     <PrivateRoute>
                       <DashboardPage />
                     </PrivateRoute>
                   }
                 />
 
                 <Route
                   path="/register-user"
                   element={
                     <PrivateRoute>
                       <RegisterUserPage />
                     </PrivateRoute>
                   }
                 />
 
                 <Route
                   path="/users"
                   element={
                     <PrivateRoute>
                       <UserSearchPage />
                     </PrivateRoute>
                   }
                 />
 
                 {/* fallback */}
                 <Route path="*" element={<div className="empty-state"><h3 className="h1">Página no encontrada</h3><p className="muted">Revisa la URL o vuelve al panel.</p></div>} />
               </Routes>
             </section>
           </motion.div>
        </main>
      </div>
    </AuthContext.Provider>
  );
}
