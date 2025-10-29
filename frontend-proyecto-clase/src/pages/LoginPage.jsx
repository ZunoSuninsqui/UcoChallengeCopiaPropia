
import LoginForm from '../components/LoginForm';
import { motion } from 'framer-motion';
// Referencia para que ESLint reconozca el uso en JSX
void motion;

/**
 * Página principal de login del administrador
 * Contiene el layout visual (lado ilustración + formulario)
 */
export default function LoginPage() {
  return (
    <div className="app-root">
      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="login-layout"
      >
        {/* Sección visual / descriptiva */}
        <div className="login-hero card logo-float">
          <h1 className="h1">Bienvenido al Panel de Administración</h1>
          <p className="muted">
            Accede con tu cuenta de administrador o mediante tus credenciales
            corporativas de Google o GitHub.
          </p>
          <ul className="muted" style={{ fontSize: '13px', marginTop: '12px' }}>
            <li>UCO CHALLENGE</li>
          </ul>
        </div>

        {/* Formulario de login */}
        <div className="card">
          <LoginForm />
        </div>
      </motion.div>
    </div>
  );
}
