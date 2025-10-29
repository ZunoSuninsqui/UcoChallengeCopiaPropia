import React from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../App";
import { authService } from "../services/auth";

export default function Navbar() {
  const location = useLocation();
  const { isAuthenticated, user, logout } = useAuth();
  const isPublicPage = location.pathname === "/";

  const handleLogin = () => {
    if (location.pathname !== "/login") {
      authService.loginWithOkta();
    }
  };

  return (
    <nav className="bg-[#1E1E1E] border-b border-gray-800">
      <div className="container mx-auto px-6 py-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <Link to="/" className="text-xl font-semibold text-white">
              UCO Challenge
            </Link>
            
            {isAuthenticated && !isPublicPage && (
              <div className="hidden md:flex ml-10 space-x-8">
                <Link to="/dashboard" className="text-gray-300 hover:text-white transition-colors">
                  Dashboard
                </Link>
                <Link to="/search" className="text-gray-300 hover:text-white transition-colors">
                  Usuarios
                </Link>
              </div>
            )}
          </div>

          <div className="flex items-center space-x-4">
            {isAuthenticated ? (
              <>
                <span className="text-gray-300">
                  {user?.name || 'Usuario'}
                </span>
                <button
                  onClick={logout}
                  className="text-gray-300 hover:text-white transition-colors"
                >
                  Cerrar Sesión
                </button>
              </>
            ) : (
              <button
                onClick={handleLogin}
                className="bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700 transition-colors"
              >
                Iniciar Sesión
              </button>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}