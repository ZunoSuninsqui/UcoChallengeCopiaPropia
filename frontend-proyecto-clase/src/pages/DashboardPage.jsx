import React from "react";
import { useNavigate } from "react-router-dom";
import PanelButton from "../components/PanelButton";
import imagen from "../assets/imagen.png";

const DashboardPage = () => {
  const navigate = useNavigate();

  const goToRegister = () => navigate("/register-user");
  const goToSearch = () => navigate("/users");

  return (
    <div className="min-h-screen flex flex-col bg-[#121212] text-gray-100">
      <main className="flex flex-col flex-1 items-center justify-center p-10 gap-8">
        <div className="w-full max-w-5xl bg-[#1E1E1E] rounded-2xl p-8 shadow-lg border border-gray-700">
          <div className="dashboard-grid">
            <div className="dashboard-left">
              <h1 className="text-3xl md:text-4xl font-semibold text-white tracking-wide mb-3">
                Bienvenido, Administrador
              </h1>

              <p className="muted mb-6">¿Qué desea hacer hoy?</p>

              <div className="buttons-stack">
                <PanelButton
                  label="Registrar Nuevo Usuario"
                  onClick={goToRegister}
                  icon="user-plus"
                  variant="primary"
                  align="left"
                  className="panel-btn"
                />

                <PanelButton
                  label="Consultar Usuarios"
                  onClick={goToSearch}
                  icon="users"
                  variant="primary"
                  align="left"
                  className="panel-btn"
                />
              </div>
            </div>

            <div className="dashboard-right" aria-hidden>
              <img
                src={imagen}
                alt="Ilustración"
                className="image-frame"
              />
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default DashboardPage;