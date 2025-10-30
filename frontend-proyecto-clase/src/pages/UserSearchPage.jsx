import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import UserTable from "../components/UserTable";
import { getUsers } from "../services/api";
import { motion } from "framer-motion";
import { Search } from "lucide-react";


const UserSearchPage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const usersPerPage = 10;
  const navigate = useNavigate();

  React.useEffect(() => {
    let mounted = true;
    // cargar todos los usuarios simulados (limit grande para obtener los 21 registros)
    getUsers(1, 100).then((res) => {
      if (!mounted) return;
      setUsers(res.data || []);
    });
    return () => (mounted = false);
  }, []);

  const filteredUsers = users.filter(
    (u) =>
      u.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
      u.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
      u.rol.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const totalPages = Math.ceil(filteredUsers.length / usersPerPage);
  const currentUsers = filteredUsers.slice(
    (currentPage - 1) * usersPerPage,
    currentPage * usersPerPage
  );

  const handlePageChange = (page) => setCurrentPage(page);

  return (
    <div className="min-h-screen flex flex-col bg-[#121212] text-gray-100">
      <main className="flex flex-col items-center px-6 py-10 gap-8">
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
          className="w-full max-w-5xl bg-[#1E1E1E] rounded-2xl p-6 shadow-lg border border-gray-700"
        >
          <div className="flex items-center justify-between mb-6">
            <button onClick={() => navigate(-1)} className="btn btn-ghost" aria-label="Regresar">
              Regresar
            </button>
            <h2 className="text-lg font-semibold text-white">Consulta de Usuarios</h2>
            <div />
          </div>

          <div className="search flex items-center gap-3 mb-6 bg-[#161616] px-4 py-3 rounded-lg border border-gray-700">
            <Search className="w-5 h-5 text-gray-400" />
            <input
              type="text"
              placeholder="Buscar por nombre, correo o rol..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="input bg-transparent text-gray-100 placeholder-gray-500"
            />
          </div>

          <UserTable
            users={currentUsers}
            currentPage={currentPage}
            totalPages={totalPages}
            totalCount={filteredUsers.length}
            usersPerPage={usersPerPage}
            onPageChange={handlePageChange}
          />
        </motion.div>
      </main>
    </div>
  );
};

export default UserSearchPage;