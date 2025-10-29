import React from 'react';
import { Link } from 'react-router-dom';

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-[#121212] text-gray-100">
      <div className="container mx-auto px-6 py-12">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold mb-4">Bienvenido a UCO Challenge</h1>
          <p className="text-xl text-gray-400">
            Plataforma de gestión y seguimiento de proyectos académicos
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8 mb-12">
          <div className="bg-[#1E1E1E] p-6 rounded-lg border border-gray-700">
            <h3 className="text-xl font-semibold mb-3">Gestión de Usuarios</h3>
            <p className="text-gray-400">
              Administra usuarios y permisos de forma eficiente y segura
            </p>
          </div>

          <div className="bg-[#1E1E1E] p-6 rounded-lg border border-gray-700">
            <h3 className="text-xl font-semibold mb-3">Seguimiento de Proyectos</h3>
            <p className="text-gray-400">
              Monitorea el progreso y estado de los proyectos en tiempo real
            </p>
          </div>

          <div className="bg-[#1E1E1E] p-6 rounded-lg border border-gray-700">
            <h3 className="text-xl font-semibold mb-3">Reportes y Estadísticas</h3>
            <p className="text-gray-400">
              Genera informes detallados y visualiza métricas clave
            </p>
          </div>
        </div>

        <div className="text-center">
          <Link
            to="/login"
            className="inline-block bg-indigo-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-indigo-700 transition-colors"
          >
            Comenzar ahora
          </Link>
        </div>
      </div>
    </div>
  );
}