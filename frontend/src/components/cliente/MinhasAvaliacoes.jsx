import React from 'react';
import StarRating from '../../components/common/StarRating';

const MinhasAvaliacoes = () => {
  const mockRatings = [
    { id: 1, clientName: 'João Silva', rating: 5, comment: 'Excelente profissional!', date: '15/05/2024' },
    { id: 2, clientName: 'Maria Costa', rating: 4, comment: 'Bom trabalho, pontual.', date: '10/05/2024' },
    { id: 3, clientName: 'Pedro Santos', rating: 5, comment: 'Muito satisfeito com o serviço.', date: '05/05/2024' }
  ];

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Minhas Avaliações</h2>
      <div className="space-y-4">
        {mockRatings.map(rating => (
          <div key={rating.id} className="bg-white rounded-lg shadow-md p-6">
            <div className="flex justify-between items-start mb-3">
              <div>
                <h3 className="font-semibold">{rating.clientName}</h3>
                <p className="text-sm text-gray-500">{rating.date}</p>
              </div>
              <StarRating rating={rating.rating} />
            </div>
            <p className="text-gray-700">{rating.comment}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MinhasAvaliacoes;
