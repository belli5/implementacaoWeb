import React, { useState } from 'react';
import { History, Calendar } from 'lucide-react';
import StarRating from '../../components/common/StarRating';

const Historico = () => {
  const [serviceHistory, setServiceHistory] = useState([
    {
      id: 1,
      prestadorId: 1,
      prestadorName: 'Maria Santos',
      service: 'Limpeza Residencial',
      date: '15/05/2024',
      status: 'concluido',
      price: 'R$ 80,00'
    },
    {
      id: 2,
      prestadorId: 2,
      prestadorName: 'Carlos Oliveira',
      service: 'Instalação Elétrica',
      date: '10/05/2024',
      status: 'concluido',
      price: 'R$ 120,00'
    }
  ]);

  const [ratings, setRatings] = useState({});

  const rateService = (serviceId, rating) => {
    setRatings(prev => ({ ...prev, [serviceId]: rating }));
    alert('Avaliação enviada com sucesso!');
  };

  const rescheduleService = (service) => {
    alert(`Reagendando serviço de ${service.service} com ${service.prestadorName}`);
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Histórico de Serviços</h2>
      {serviceHistory.length === 0 ? (
        <div className="text-center py-12">
          <History className="w-16 h-16 text-gray-300 mx-auto mb-4" />
          <p className="text-gray-500">Nenhum serviço contratado ainda</p>
        </div>
      ) : (
        <div className="space-y-4">
          {serviceHistory.map(service => (
            <div key={service.id} className="bg-white rounded-lg shadow-md p-6">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="font-semibold text-lg">{service.service}</h3>
                  <p className="text-gray-600">com {service.prestadorName}</p>
                  <p className="text-sm text-gray-500">{service.date}</p>
                </div>
                <div className="text-right">
                  <p className="font-semibold text-green-600">{service.price}</p>
                  <span className="bg-green-100 text-green-800 text-xs px-2 py-1 rounded">
                    {service.status}
                  </span>
                </div>
              </div>
              
              <div className="flex gap-3">
                <button
                  onClick={() => rescheduleService(service)}
                  className="flex items-center gap-2 px-4 py-2 bg-blue-100 text-blue-700 rounded-lg hover:bg-blue-200 transition-colors"
                >
                  <Calendar className="w-4 h-4" />
                  Reagendar
                </button>
                
                {!ratings[service.id] ? (
                  <div className="flex items-center gap-2">
                    <span className="text-sm text-gray-600">Avaliar:</span>
                    <StarRating
                      rating={0}
                      interactive={true}
                      onRate={(rating) => rateService(service.id, rating)}
                    />
                  </div>
                ) : (
                  <div className="flex items-center gap-2">
                    <span className="text-sm text-gray-600">Sua avaliação:</span>
                    <StarRating rating={ratings[service.id]} />
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Historico;