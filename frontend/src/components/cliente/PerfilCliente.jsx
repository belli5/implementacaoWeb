import React, { useState } from 'react';
import { Edit } from 'lucide-react';

const PerfilCliente = () => {
  const [profile, setProfile] = useState({
    name: 'João Silva',
    email: 'joao.silva@email.com',
    phone: '(11) 91234-5678'
  });

  const [editMode, setEditMode] = useState(false);
  const [tempProfile, setTempProfile] = useState(profile);

  const saveProfile = () => {
    setProfile(tempProfile);
    setEditMode(false);
    alert('Perfil do cliente atualizado com sucesso!');
  };

  return (
    <div className="max-w-2xl mx-auto">
      <div className="bg-white rounded-lg shadow-md p-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold">Meu Perfil (Cliente)</h2>
          <button
            onClick={() => setEditMode(!editMode)}
            className="flex items-center gap-2 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
          >
            <Edit className="w-4 h-4" />
            {editMode ? 'Cancelar' : 'Editar'}
          </button>
        </div>

        {editMode ? (
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Nome</label>
              <input
                type="text"
                className="w-full p-2 border border-gray-300 rounded-lg"
                value={tempProfile.name}
                onChange={(e) => setTempProfile({ ...tempProfile, name: e.target.value })}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">E-mail</label>
              <input
                type="email"
                className="w-full p-2 border border-gray-300 rounded-lg"
                value={tempProfile.email}
                onChange={(e) => setTempProfile({ ...tempProfile, email: e.target.value })}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Telefone</label>
              <input
                type="tel"
                className="w-full p-2 border border-gray-300 rounded-lg"
                value={tempProfile.phone}
                onChange={(e) => setTempProfile({ ...tempProfile, phone: e.target.value })}
              />
            </div>

            <button
              onClick={saveProfile}
              className="w-full bg-green-500 text-white py-2 rounded-lg hover:bg-green-600 transition-colors"
            >
              Salvar Alterações
            </button>
          </div>
        ) : (
          <div className="space-y-4">
            <div>
              <h3 className="font-semibold text-lg">{profile.name}</h3>
              <p className="text-gray-600">{profile.email}</p>
              <p className="text-gray-600">{profile.phone}</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default PerfilCliente;
