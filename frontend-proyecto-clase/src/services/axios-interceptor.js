import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8086',
});

let tokenResolver = null;

axiosInstance.interceptors.request.use(
  async (config) => {
    if (typeof tokenResolver === 'function') {
      try {
        const token = await tokenResolver();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
      } catch (err) {
        if (import.meta.env.DEV) {
          console.warn('No fue posible adjuntar el token de Auth0 a la petición:', err);
        }
      }
    }
    return config;
  },
  (error) => Promise.reject(error),
);

export const setupInterceptors = (getToken) => {
  tokenResolver = getToken;
  return axiosInstance;
};


export default axiosInstance;
