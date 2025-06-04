import React from 'react';
import { Heart } from 'lucide-react';
import StarRating from '../common/StarRating';

const PrestadorCard = ({ prestador, favorites, toggleFavorite, onContract }) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
      <div className="flex justify-between items-start mb-4">
        <div className="flex items-center gap-3">
          <div className="w-12 h-12 bg-blue-500 rounded-full flex items-center justify-center text-white font-bold">
            {prestador.avatar}
          </div>
          <div>
            <h3 className="font-semibold text-lg">{prestador.name}</h3>
            <div className="flex items-center gap-2">
              <StarRating rating={prestador.rating} />
              <span className="text-sm text-gray-600">
                {prestador.rating} ({prestador.totalRatings} avaliações)
              </span>
            </div>
          </div>
        </div>
        <button
          onClick={() => toggleFavorite(prestador.id)}
          className={`p-2 rounded-full ${
            favorites.includes(prestador.id) 
              ? 'text-red-500 bg-red-50' 
              : 'text-gray-400 hover:text-red-500'
          }`}
        >
          <Heart className={`w-5 h-5 ${favorites.includes(prestador.id) ? 'fill-current' : ''}`} />
        </button>
      </div>
      
      <p className="text-gray-600 mb-3">{prestador.description}</p>
      
      <div className="mb-3">
        <p className="font-medium text-green-600">{prestador.price}</p>
      </div>
      
      <div className="mb-4">
        <p className="text-sm text-gray-500 mb-1">Serviços:</p>
        <div className="flex flex-wrap gap-1">
          {prestador.services.map((service, index) => (
            <span key={index} className="bg-blue-100 text-blue-800 text-xs px-2 py-1 rounded">
              {service}
            </span>
          ))}
        </div>
      </div>
      
      <button
        onClick={() => onContract(prestador)}
        className="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition-colors"
      >
        Contratar Serviço
      </button>
    </div>
  );
};

export default PrestadorCard;
