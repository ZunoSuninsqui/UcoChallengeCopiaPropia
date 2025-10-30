// ...existing code...
import React from "react";

/**
 * Navbar -> ahora actúa como componente de navegación lateral simple (sin botón de logout).
 * El logout se maneja sólo en el topbar (App.jsx).
 * Props:
 *  - title (opcional): si se pasa, se renderiza como título del menú lateral
 */
const Navbar = ({ title }) => {
  return (
    <nav className="flex flex-col gap-2">
      {title && <div className="text-sm font-medium text-gray-300 mb-2">{title}</div>}
      {/* Aquí puedes añadir nav-items si quieres */}
      <div className="nav-item">Dashboard</div>
      <div className="nav-item">Usuarios</div>
    </nav>
  );
};

export default Navbar;
// ...existing code...