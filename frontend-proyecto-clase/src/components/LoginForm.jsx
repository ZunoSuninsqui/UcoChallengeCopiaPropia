import React, { useState } from 'react';
import { useAuth } from '../App';
import { motion } from 'framer-motion';
import { authService } from '../services/auth';
// Referencia explícita para que ESLint detecte uso (se usa en JSX como <motion.xxx />)
void motion;

export default function LoginForm() {
  const { loginWithCredentials } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [submitting, setSubmitting] = useState(false);

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
    const validationErrors = validate();
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length > 0) return;

    setSubmitting(true);
    try {
      const response = await authService.login({ email, password });
      await loginWithCredentials(response.user);
    } catch (err) {
      console.error('Error al hacer login:', err);
      setErrors({ general: 'Error al iniciar sesión. Por favor, verifica tus credenciales.' });
    } finally {
      setSubmitting(false);
    }
  };

  const handleOktaLogin = async () => {
    setSubmitting(true);
    try {
      await authService.loginWithOkta();
    } catch (err) {
      console.error('Error iniciando login con Okta:', err);
      setErrors({ general: 'Error al iniciar el proceso de login con Okta.' });
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

      <button type="submit" className="btn" disabled={submitting}>
        {submitting ? 'Accediendo...' : 'Ingresar'}
      </button>

      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', margin: '10px 0' }}>
        <div style={{ flex: 1, height: 1, background: 'rgba(255,255,255,0.05)' }} />
        <span className="muted" style={{ fontSize: '13px' }}>o continúa con</span>
        <div style={{ flex: 1, height: 1, background: 'rgba(255,255,255,0.05)' }} />
      </div>

      <button
        type="button"
        className="btn btn-secondary"
        onClick={handleOktaLogin}
        disabled={submitting}
      >
        {submitting ? 'Iniciando...' : 'Iniciar sesión con Okta'}
      </button>

      {errors.general && (
        <div className="error" style={{ marginTop: '10px', textAlign: 'center' }}>
          {errors.general}
        </div>
      )}

      <p className="muted" style={{ fontSize: '12px', textAlign: 'center', marginTop: '8px' }}>
        Solo administradores autorizados pueden acceder.
      </p>
    </motion.form>
  );
}
