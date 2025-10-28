// --------------------- VALIDADORES BÁSICOS --------------------- //

/**
 * Verifica que el campo no esté vacío ni solo con espacios.
 * @param {string} value
 * @returns {boolean}
 */
export const validateRequired = (value) => {
  return value && value.trim().length > 0;
};

/**
 * Verifica el formato de un correo electrónico.
 * @param {string} email
 * @returns {boolean}
 */
export const validateEmail = (email) => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
};

/**
 * Verifica que un valor tenga una longitud mínima.
 * @param {string} value
 * @param {number} minLength
 * @returns {boolean}
 */
export const validateLength = (value, minLength) => {
  return value && value.length >= minLength;
};

/**
 * Verifica si un valor cumple con formato alfanumérico básico.
 * @param {string} value
 * @returns {boolean}
 */
export const validateAlphanumeric = (value) => {
  const regex = /^[a-zA-Z0-9\sáéíóúÁÉÍÓÚñÑ]+$/;
  return regex.test(value);
};

/**
 * Valida una contraseña básica (mínimo 6 caracteres, mayúscula, número y símbolo opcional).
 * @param {string} password
 * @returns {boolean}
 */
export const validatePassword = (password) => {
  const regex = /^(?=.*[A-Z])(?=.*[0-9])(?=.{6,})/;
  return regex.test(password);
};

// --------------------- FUNCIONES AUXILIARES --------------------- //

/**
 * Ejecuta una validación general en varios campos.
 * Retorna un objeto con los errores encontrados.
 * @param {Object} fields
 * @returns {Object} errors
 */
export const validateFormFields = (fields) => {
  const errors = {};
  if (!validateRequired(fields.nombre)) errors.nombre = "El nombre es obligatorio.";
  if (!validateEmail(fields.email)) errors.email = "El correo electrónico no es válido.";
  if (!validateLength(fields.contraseña, 6))
    errors.contraseña = "La contraseña debe tener al menos 6 caracteres.";
  if (!validateRequired(fields.rol)) errors.rol = "Debe seleccionar un rol válido.";
  return errors;
};
