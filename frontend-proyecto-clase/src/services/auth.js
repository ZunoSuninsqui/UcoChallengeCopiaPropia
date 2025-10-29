import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:3000/api';

class AuthService {
  constructor() {
    this.token = localStorage.getItem('token');
    if (this.token) {
      this.setAuthHeader(this.token);
    }
  }

  setAuthHeader(token) {
    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      delete axios.defaults.headers.common['Authorization'];
    }
  }

  async login(credentials) {
    try {
      const response = await axios.post(`${API_URL}/auth/login`, credentials);
      const { token, user } = response.data;
      
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      
      this.setAuthHeader(token);
      
      return { token, user };
    } catch (error) {
      console.error('Error en login:', error.response?.data || error.message);
      throw error;
    }
  }

  async loginWithOkta() {
    try {
      // Redirige al endpoint del backend que inicia el flujo de Okta
      window.location.href = `${API_URL}/auth/okta/login`;
    } catch (error) {
      console.error('Error iniciando login con Okta:', error);
      throw error;
    }
  }

  async handleOktaCallback(code) {
    try {
      const response = await axios.post(`${API_URL}/auth/okta/callback`, { code });
      const { token, user } = response.data;
      
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      
      this.setAuthHeader(token);
      
      return { token, user };
    } catch (error) {
      console.error('Error procesando callback de Okta:', error);
      throw error;
    }
  }

  async logout() {
    try {
      await axios.post(`${API_URL}/auth/logout`);
    } catch (error) {
      console.error('Error en logout:', error);
    } finally {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      this.setAuthHeader(null);
    }
  }

  isAuthenticated() {
    return Boolean(this.getToken());
  }

  getToken() {
    return localStorage.getItem('token');
  }

  getUser() {
    const userStr = localStorage.getItem('user');
    try {
      return userStr ? JSON.parse(userStr) : null;
    } catch {
      return null;
    }
  }
}

export const authService = new AuthService();

// Configurar interceptor para manejar errores de autenticación
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      delete axios.defaults.headers.common['Authorization'];
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);