import React from 'react';
import { Heart } from 'lucide-react';
import PrestadorCard from '../../components/cliente/PrestadorCard';

const Favoritos = ({ prestadores, favorites, toggleFavorite, contractService }) => {
  const favoritePrestadores = prestadores.filter(p => favorites.includes(p.id));

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Meus Favoritos</h2>
      {favoritePrestadores.length === 0 ? (
        <div className="text-center py-12">
          <Heart className="w-16 h-16 text-gray-300 mx-auto mb-4" />
          <p className="text-gray-500">Nenhum prestador favoritado ainda</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {favoritePrestadores.map(prestador => (
            <PrestadorCard
              key={prestador.id}
              prestador={prestador}
              favorites={favorites}
              toggleFavorite={toggleFavorite}
              onContract={contractService}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default Favoritos;