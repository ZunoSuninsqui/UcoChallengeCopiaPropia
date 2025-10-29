// ...existing code...
import React, { useState } from "react";
import { motion } from "framer-motion";
// Evitar falso positivo de ESLint (uso en JSX)
void motion;
import { validateEmail, validateRequired } from "../utils/validators";

const UserForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    tipoIdentificacion: "",
    numeroIdentificacion: "",
    primerNombre: "",
    segundoNombre: "",
    primerApellido: "",
    segundoApellido: "",
    ciudadResidencia: "",
    email: "",
    telefonoMovil: ""
  });
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    // validate as user types
    validateField(name, value);
  };

  const validateField = (name, value) => {
    let err = "";
    if (!validateRequired(value)) {
      err = "Requerido.";
    } else if (name === 'email' && !validateEmail(value)) {
      err = 'Correo inválido.';
    }
    setErrors(prev => ({ ...prev, [name]: err }));
    return err === "";
  };

  const validateForm = () => {
    const newErrors = {};
    // All fields required (including optional-sounding ones per request)
    if (!validateRequired(formData.tipoIdentificacion)) newErrors.tipoIdentificacion = "Requerido.";
    if (!validateRequired(formData.numeroIdentificacion)) newErrors.numeroIdentificacion = "Requerido.";
    if (!validateRequired(formData.primerNombre)) newErrors.primerNombre = "Requerido.";
    if (!validateRequired(formData.segundoNombre)) newErrors.segundoNombre = "Requerido.";
    if (!validateRequired(formData.primerApellido)) newErrors.primerApellido = "Requerido.";
    if (!validateRequired(formData.segundoApellido)) newErrors.segundoApellido = "Requerido.";
    if (!validateRequired(formData.ciudadResidencia)) newErrors.ciudadResidencia = "Requerido.";
    if (!validateEmail(formData.email)) newErrors.email = "Correo inválido.";
    if (!validateRequired(formData.telefonoMovil)) newErrors.telefonoMovil = "Requerido.";
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    setErrors({});
    onSubmit(formData);
    setFormData({
      tipoIdentificacion: "",
      numeroIdentificacion: "",
      primerNombre: "",
      segundoNombre: "",
      primerApellido: "",
      segundoApellido: "",
      ciudadResidencia: "",
      email: "",
      telefonoMovil: ""
    });
  };

  return (
    <form onSubmit={handleSubmit} className="form form-grid">
      {/* Grid: two columns for paired fields */}
      <div className="grid-2">
        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <select
            name="tipoIdentificacion"
            value={formData.tipoIdentificacion}
            onChange={handleChange}
            className="input"
            aria-label="Tipo de identificación"
          >
            <option value="">Seleccionar tipo de identificación</option>
            <option value="cc">Cédula de ciudadanía</option>
            <option value="ti">Tarjeta de identidad</option>
            <option value="passport">Pasaporte</option>
            <option value="nit">NIT</option>
          </select>
          {errors.tipoIdentificacion && <p className="error">{errors.tipoIdentificacion}</p>}
        </motion.div>

        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input
            name="numeroIdentificacion"
            value={formData.numeroIdentificacion}
            onChange={handleChange}
            placeholder="Número de identificación"
            className="input"
            aria-label="Número de identificación"
          />
          {errors.numeroIdentificacion && <p className="error">{errors.numeroIdentificacion}</p>}
        </motion.div>
      </div>

      <div className="grid-2">
        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input name="primerNombre" value={formData.primerNombre} onChange={handleChange} placeholder="Primer nombre" className="input" aria-label="Primer nombre" />
          {errors.primerNombre && <p className="error">{errors.primerNombre}</p>}
        </motion.div>

        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input name="segundoNombre" value={formData.segundoNombre} onChange={handleChange} placeholder="Segundo nombre" className="input" aria-label="Segundo nombre" />
          {errors.segundoNombre && <p className="error">{errors.segundoNombre}</p>}
        </motion.div>
      </div>

      <div className="grid-2">
        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input name="primerApellido" value={formData.primerApellido} onChange={handleChange} placeholder="Primer apellido" className="input" aria-label="Primer apellido" />
          {errors.primerApellido && <p className="error">{errors.primerApellido}</p>}
        </motion.div>

        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input name="segundoApellido" value={formData.segundoApellido} onChange={handleChange} placeholder="Segundo apellido" className="input" aria-label="Segundo apellido" />
          {errors.segundoApellido && <p className="error">{errors.segundoApellido}</p>}
        </motion.div>
      </div>

      <div className="grid-2">
        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <select name="ciudadResidencia" value={formData.ciudadResidencia} onChange={handleChange} className="input" aria-label="Ciudad de residencia">
            <option value="">Seleccionar ciudad de residencia</option>
            <option value="medellin">Medellín</option>
            <option value="bogota">Bogotá</option>
            <option value="cali">Cali</option>
          </select>
          {errors.ciudadResidencia && <p className="error">{errors.ciudadResidencia}</p>}
        </motion.div>

        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input name="email" value={formData.email} onChange={handleChange} placeholder="Correo electrónico" className="input" aria-label="Correo electrónico" type="email" />
          {errors.email && <p className="error">{errors.email}</p>}
        </motion.div>
      </div>

      <div className="grid-2">
        <motion.div className="field" initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }}>
          <input name="telefonoMovil" value={formData.telefonoMovil} onChange={handleChange} placeholder="Número teléfono móvil (ej. +57 300 0000000)" className="input" aria-label="Número teléfono móvil" type="tel" />
          {errors.telefonoMovil && <p className="error">{errors.telefonoMovil}</p>}
        </motion.div>

        {/* empty placeholder to keep grid consistent */}
        <div />
      </div>

      <motion.button whileHover={{ scale: 1.03 }} whileTap={{ scale: 0.97 }} type="submit" className="btn">
        Registrar Usuario
      </motion.button>
    </form>
  );
};

export default UserForm;
// ...existing code...