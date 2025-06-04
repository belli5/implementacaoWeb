import React from 'react';
import { NavLink } from 'react-router-dom';
import { Search, Heart, History, User, MessageSquare } from 'lucide-react';

const Navigation = ({ userType }) => {
  const baseClass = "flex-1 flex items-center justify-center gap-2 py-2 px-4 rounded-md transition-colors";
  const activeClass = "bg-blue-500 text-white";
  const inactiveClass = "text-gray-600 hover:bg-gray-100";

  return (
    <nav className="mb-8">
      <div className="flex gap-1 bg-white rounded-lg p-1 shadow-sm">
        {userType === 'cliente' ? (
          <>
            <NavLink
              to="/buscar"
              className={({ isActive }) => `${baseClass} ${isActive ? activeClass : inactiveClass}`}
            >
              <Search className="w-4 h-4" />
              Buscar Serviços
            </NavLink>
            <NavLink
              to="/favoritos"
              className={({ isActive }) => `${baseClass} ${isActive ? activeClass : inactiveClass}`}
            >
              <Heart className="w-4 h-4" />
              Favoritos
            </NavLink>
            <NavLink
              to="/historico"
              className={({ isActive }) => `${baseClass} ${isActive ? activeClass : inactiveClass}`}
            >
              <History className="w-4 h-4" />
              Histórico
            </NavLink>
          </>
        ) : (
          <>
            <NavLink
              to="/perfil"
              className={({ isActive }) => `${baseClass} ${isActive ? activeClass : inactiveClass}`}
            >
              <User className="w-4 h-4" />
              Meu Perfil
            </NavLink>
            <NavLink
              to="/avaliacoes"
              className={({ isActive }) => `${baseClass} ${isActive ? activeClass : inactiveClass}`}
            >
              <MessageSquare className="w-4 h-4" />
              Minhas Avaliações
            </NavLink>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navigation;
