/**
 * Simulación de un módulo de conexión API.
 * Más adelante se reemplazará por peticiones reales a un backend (Node, Spring, etc.)
 */

const API_BASE_URL = "https://api.example.com"; // Placeholder temporal

// --------------------- FUNCIONES DE AUTENTICACIÓN --------------------- //

/**
 * Simula un inicio de sesión de administrador.
 * @param {string} email
 * @param {string} password
 * @returns {Promise<Object>}
 */
export const loginAdmin = async (email, password) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (email === "admin@example.com" && password === "123456") {
        resolve({
          token: "fake-jwt-token",
          user: { id: 1, nombre: "Administrador", rol: "admin", email },
        });
      } else {
        reject(new Error("Credenciales inválidas."));
      }
    }, 800);
  });
};

/**
 * Simula registro de un usuario nuevo.
 * @param {Object} userData
 * @returns {Promise<Object>}
 */
export const registerUser = async (userData) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({ ...userData, id: Math.floor(Math.random() * 1000) });
    }, 600);
  });
};

// --------------------- FUNCIONES DE USUARIOS --------------------- //

/**
 * Simula una consulta de usuarios en una base de datos relacional.
 * Retorna datos en lotes de 5.
 * @param {number} page
 * @param {number} limit
 * @returns {Promise<Object>}
 */
export const getUsers = async (page = 1, limit = 5) => {
  const allUsers = [
    { id: 1, nombre: "Juan Pérez", email: "juan@example.com", rol: "admin" },
    { id: 2, nombre: "María López", email: "maria@example.com", rol: "user" },
    { id: 3, nombre: "Carlos Ramírez", email: "carlos@example.com", rol: "user" },
    { id: 4, nombre: "Ana Gómez", email: "ana@example.com", rol: "admin" },
    { id: 5, nombre: "Lucía Méndez", email: "lucia@example.com", rol: "user" },
    { id: 6, nombre: "Pedro Ortiz", email: "pedro@example.com", rol: "user" },
    { id: 7, nombre: "Laura García", email: "laura@example.com", rol: "admin" },
    { id: 8, nombre: "Juan Pérez", email: "juan@example.com", rol: "admin" },
    { id: 9, nombre: "María López", email: "maria@example.com", rol: "user" },
    { id: 10, nombre: "Carlos Ramírez", email: "carlos@example.com", rol: "user" },
    { id: 11, nombre: "Ana Gómez", email: "ana@example.com", rol: "admin" },
    { id: 12, nombre: "Lucía Méndez", email: "lucia@example.com", rol: "user" },
    { id: 13, nombre: "Pedro Ortiz", email: "pedro@example.com", rol: "user" },
    { id: 14, nombre: "Laura García", email: "laura@example.com", rol: "admin" },
    { id: 15, nombre: "Juan Pérez", email: "juan@example.com", rol: "admin" },
    { id: 16, nombre: "María López", email: "maria@example.com", rol: "user" },
    { id: 17, nombre: "Carlos Ramírez", email: "carlos@example.com", rol: "user" },
    { id: 18, nombre: "Ana Gómez", email: "ana@example.com", rol: "admin" },
    { id: 19, nombre: "Lucía Méndez", email: "lucia@example.com", rol: "user" },
    { id: 20, nombre: "Pedro Ortiz", email: "pedro@example.com", rol: "user" },
    { id: 21, nombre: "Laura García", email: "laura@example.com", rol: "admin" },
  ];

  return new Promise((resolve) => {
    setTimeout(() => {
      const startIndex = (page - 1) * limit;
      const endIndex = page * limit;
      const paginated = allUsers.slice(startIndex, endIndex);
      resolve({
        data: paginated,
        total: allUsers.length,
        totalPages: Math.ceil(allUsers.length / limit),
      });
    }, 500);
  });
};
