// ...existing code...
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
// import Navbar from "../components/Navbar";  <-- eliminado
import UserForm from "../components/UserForm";
import { motion } from "framer-motion";

const RegisterUserPage = () => {
  const [submittedData, setSubmittedData] = useState(null);
  const navigate = useNavigate();

  const handleUserSubmit = (data) => {
    setSubmittedData(data);
    console.log("Usuario registrado (simulación):", data);
  };

  return (
    <div className="min-h-screen flex flex-col bg-[#121212] text-gray-100">
      <main className="flex flex-col items-center justify-start px-6 py-10">
        <motion.div initial={{ opacity: 0, y: 30 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.6 }} className="w-full max-w-lg bg-[#1E1E1E] rounded-2xl p-8 shadow-lg border border-gray-700">
          <div className="flex items-center justify-between mb-4">
            <button onClick={() => navigate(-1)} className="btn btn-ghost" aria-label="Regresar">Regresar</button>
            <h2 className="text-2xl font-semibold text-center text-white mb-0">Formulario de Registro</h2>
            <div style={{ width: 72 }} />
          </div>

          <UserForm onSubmit={handleUserSubmit} />
          {submittedData && <p className="mt-6 text-sm text-success text-center">✅ Usuario Creado Correctamente</p>}
        </motion.div>
      </main>
    </div>
  );
};

export default RegisterUserPage;
// ...existing code...