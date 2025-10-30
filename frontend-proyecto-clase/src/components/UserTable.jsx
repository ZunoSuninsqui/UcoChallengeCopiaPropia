import React, { useState } from "react";
import { motion } from "framer-motion";
import { ArrowUpDown, ChevronLeft, ChevronRight } from "lucide-react";

const UserTable = ({ users, currentPage, totalPages, totalCount = 0, usersPerPage = 10, onPageChange }) => {
  const [sortConfig, setSortConfig] = useState({ key: null, direction: "asc" });

  const handleSort = (key) => {
    let direction = "asc";
    if (sortConfig.key === key && sortConfig.direction === "asc") {
      direction = "desc";
    }
    setSortConfig({ key, direction });
  };

  const sortedUsers = [...users].sort((a, b) => {
    if (!sortConfig.key) return 0;
    const valueA = a[sortConfig.key].toString().toLowerCase();
    const valueB = b[sortConfig.key].toString().toLowerCase();

    if (valueA < valueB) return sortConfig.direction === "asc" ? -1 : 1;
    if (valueA > valueB) return sortConfig.direction === "asc" ? 1 : -1;
    return 0;
  });

  const renderSortIcon = (key) => (
    <ArrowUpDown
      className={`w-4 h-4 ml-2 inline-block ${
        sortConfig.key === key ? "text-indigo-400" : "text-gray-500"
      }`}
    />
  );

  return (
    <motion.div
      initial={{ opacity: 0, y: 15 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.4 }}
      className="bg-[#1E1E1E] rounded-2xl shadow-lg border border-gray-700 overflow-hidden"
    >
      <table className="w-full text-left text-gray-200">
        <thead className="bg-[#2A2A2A] text-gray-300">
          <tr>
            <th
              onClick={() => handleSort("nombre")}
              className="px-6 py-4 cursor-pointer select-none hover:text-white transition-colors"
              style={{ width: '40%' }}
            >
              Nombre {renderSortIcon("nombre")}
            </th>
            <th
              onClick={() => handleSort("email")}
              className="px-6 py-4 cursor-pointer select-none hover:text-white transition-colors break-words"
              style={{ width: '45%' }}
            >
              Correo {renderSortIcon("email")}
            </th>
            <th
              onClick={() => handleSort("rol")}
              className="px-6 py-4 cursor-pointer select-none hover:text-white transition-colors"
              style={{ width: '15%' }}
            >
              Rol {renderSortIcon("rol")}
            </th>
          </tr>
        </thead>
        <tbody>
          {sortedUsers.length === 0 ? (
            <tr>
              <td
                colSpan="3"
                className="text-center py-6 text-gray-500 italic bg-[#181818]"
              >
                No se encontraron usuarios.
              </td>
            </tr>
          ) : (
            sortedUsers.map((user, index) => (
              <motion.tr
                key={user.id}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ delay: index * 0.05 }}
                className="hover:bg-[#252525] transition-colors border-t border-gray-800"
              >
                <td className="px-6 py-4">{user.nombre}</td>
                <td className="px-6 py-4">{user.email}</td>
                <td className="px-6 py-4 capitalize">
                  <span
                    className={`px-3 py-1 rounded-lg text-sm font-medium ${
                      user.rol === "admin"
                        ? "bg-indigo-600/30 text-indigo-300"
                        : "bg-emerald-600/30 text-emerald-300"
                    }`}
                  >
                    {user.rol}
                  </span>
                </td>
              </motion.tr>
            ))
          )}
        </tbody>
      </table>

      {/* Paginación */}
      {totalPages > 0 && (
        <div className="flex flex-col gap-2 px-6 py-4 bg-[#2A2A2A] text-gray-300">
          <div className="flex items-center justify-between">
            <div className="text-sm">
              Mostrando <span className="text-indigo-400">{(currentPage - 1) * usersPerPage + 1}</span> - <span className="text-indigo-400">{Math.min(currentPage * usersPerPage, totalCount)}</span> de <span className="text-indigo-400">{totalCount}</span>
            </div>

            <div className="flex items-center gap-2">
              <button
                disabled={currentPage === 1}
                onClick={() => onPageChange(currentPage - 1)}
                className={`page-btn flex items-center justify-center p-1 rounded-md ${currentPage === 1 ? 'opacity-40 cursor-not-allowed' : 'text-indigo-300 hover:bg-[#333]'}`}
                aria-label="Anterior"
              >
                <ChevronLeft className="w-5 h-5" />
              </button>

              {/* page numbers: show up to 5 page numbers centered around current */}
              {Array.from({ length: totalPages }).map((_, i) => {
                const page = i + 1;
                // show if near current page
                if (Math.abs(page - currentPage) > 2 && page !== 1 && page !== totalPages) return null;
                return (
                  <button
                    key={page}
                    onClick={() => onPageChange(page)}
                    className={`page-btn px-3 py-1 rounded-md text-sm ${page === currentPage ? 'bg-indigo-600/50 text-white font-semibold scale-105' : 'text-gray-300 hover:bg-[#2f2f2f]'}`}
                    aria-current={page === currentPage ? 'page' : undefined}
                    aria-label={page === currentPage ? `Página actual ${page}` : `Ir a la página ${page}`}
                  >
                    {page}
                  </button>
                );
              })}

              <button disabled={currentPage === totalPages} onClick={() => onPageChange(currentPage + 1)} className="page-btn"><ChevronRight className="w-4 h-4" /></button>
            </div>
          </div>
        </div>
      )}
    </motion.div>
  );
};

export default UserTable;
