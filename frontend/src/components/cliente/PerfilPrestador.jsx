import React, { useState } from 'react';
import { Edit } from 'lucide-react';

const PerfilPrestador = () => {
  const [userProfile, setUserProfile] = useState({
    name: 'Carlos Oliveira',
    services: ['Instalação Elétrica', 'Manutenção'],
    description: 'Eletricista certificado com 10 anos de experiência',
    price: 'R$ 120/serviço'
  });

  const [editMode, setEditMode] = useState(false);
  const [tempProfile, setTempProfile] = useState(userProfile);

  const saveProfile = () => {
    setUserProfile(tempProfile);
    setEditMode(false);
    alert('Perfil atualizado com sucesso!');
  };

  return (
    <div className="max-w-2xl mx-auto">
      <div className="bg-white rounded-lg shadow-md p-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold">Meu Perfil (Prestador)</h2>
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
            {/* campos de edição... */}
            {/* igual ao seu componente atual */}
          </div>
        ) : (
          <div className="space-y-4">
            {/* campos de visualização */}
          </div>
        )}
      </div>
    </div>
  );
};

export default PerfilPrestador;
