import React, { useState } from 'react';
import { useAuth } from '../App';
import { motion } from 'framer-motion';
import OAuthButtons from './OAuthButtons';

/**
 * Formulario de login de administrador.
 * Incluye:
 * - Validaciones básicas de email y contraseña
 * - Integración con AuthContext para login simulado
 * - Botones de OAuth (Google / GitHub)
 */
export default function LoginForm() {
  const { loginWithCredentials, loginWithOAuth } = useAuth();

  // estado del formulario
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [submitting, setSubmitting] = useState(false);

  // --- Validaciones frontend ---
  const validate = () => {
    const newErrors = {};
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!email.trim()) newErrors.email = 'El correo es obligatorio.';
    else if (!emailRegex.test(email)) newErrors.email = 'Formato de correo no válido.';

    if (!password.trim()) newErrors.password = 'La contraseña es obligatoria.';
    else if (password.length < 6) newErrors.password = 'Debe tener al menos 6 caracteres.';
    else if (password.length > 40) newErrors.password = 'Demasiado larga.';

    return newErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const v = validate();
    setErrors(v);

    if (Object.keys(v).length > 0) return; // hay errores

    setSubmitting(true);
    try {
      await loginWithCredentials({ email, name: 'Administrador' });
    } catch (err) {
      console.error('Error al simular login:', err);
    } finally {
      setSubmitting(false);
    }
  };

  const handleOAuth = async (provider) => {
    setSubmitting(true);
    try {
      await loginWithOAuth(provider);
    } catch (err) {
      console.error('Error OAuth simulado:', err);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <motion.form
      onSubmit={handleSubmit}
      className="form"
      initial={{ opacity: 0, y: 8 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.45 }}
    >
      <h2 className="h1" style={{ marginBottom: '6px' }}>Iniciar sesión</h2>
      <p className="muted">Accede con tus credenciales de administrador</p>

      {/* Campo de correo */}
      <div>
        <label htmlFor="email" className="label">Correo electrónico</label>
        <input
          id="email"
          type="email"
          className="input"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="admin@empresa.com"
          autoComplete="username"
          disabled={submitting}
        />
        {errors.email && <div className="error">{errors.email}</div>}
      </div>

      {/* Campo de contraseña */}
      <div>
        <label htmlFor="password" className="label">Contraseña</label>
        <input
          id="password"
          type="password"
          className="input"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="********"
          autoComplete="current-password"
          disabled={submitting}
        />
        {errors.password && <div className="error">{errors.password}</div>}
      </div>

      {/* Botón principal */}
      <button type="submit" className="btn" disabled={submitting}>
        {submitting ? 'Accediendo...' : 'Ingresar'}
      </button>

      {/* Línea divisoria */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', margin: '10px 0' }}>
        <div style={{ flex: 1, height: 1, background: 'rgba(255,255,255,0.05)' }} />
        <span className="muted" style={{ fontSize: '13px' }}>o continúa con</span>
        <div style={{ flex: 1, height: 1, background: 'rgba(255,255,255,0.05)' }} />
      </div>

      {/* Botones de OAuth (Google y GitHub) */}
      <OAuthButtons
        onGoogle={() => handleOAuth('google')}
        onGithub={() => handleOAuth('github')}
        disabled={submitting}
      />

      <p className="muted" style={{ fontSize: '12px', textAlign: 'center', marginTop: '8px' }}>
        Solo administradores autorizados pueden acceder.
      </p>
    </motion.form>
  );
}
