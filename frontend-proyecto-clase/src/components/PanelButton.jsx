import React from "react";

const PanelButton = ({ label, icon, onClick, variant = "primary", className = "", align = "center" }) => {
  // keep API compatible; we center label visually while icon is absolute-left
  const justify = align === "left" ? "justify-start" : "justify-center";

  const baseClass =
    variant === "primary"
      ? `panel-btn btn ${className}`
      : `panel-btn btn-secondary ${className}`;

  return (
    <div className={`w-full ${className}`}>
      <button onClick={onClick} className={baseClass} aria-label={label} type="button">
        {icon && <span className="btn-icon" aria-hidden><i className={`fas fa-${icon}`} /></span>}
        <span className="btn-content">{label}</span>
      </button>
    </div>
  );
};

export default PanelButton;