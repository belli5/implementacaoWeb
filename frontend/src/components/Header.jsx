import React from 'react';
import { User } from 'lucide-react';

const Header = ({ currentUser, setCurrentUser }) => {
  return (
    <header className="bg-white shadow-sm border-b">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center py-4">
          <h1 className="text-2xl font-bold text-gray-900">ServiçoPro</h1>
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <User className="w-5 h-5 text-gray-600" />
              <span className="text-gray-700">{currentUser.name}</span>
            </div>
            <select
              value={currentUser.type}
              onChange={(e) => setCurrentUser({...currentUser, type: e.target.value})}
              className="border border-gray-300 rounded-lg px-3 py-1"
            >
              <option value="cliente">Cliente</option>
              <option value="prestador">Prestador</option>
            </select>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;