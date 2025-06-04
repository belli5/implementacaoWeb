import React, { useState } from 'react';
import SearchFilters from '../../components/cliente/SearchFilters';
import PrestadorCard from '../../components/cliente/PrestadorCard';
import { SERVICE_CATEGORIES } from '../../utils/constants';

const BuscarServicos = ({ 
  prestadores, 
  favorites, 
  toggleFavorite, 
  contractService 
}) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('todos');

  const filteredPrestadores = prestadores.filter(prestador => {
    const matchesSearch = prestador.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         prestador.services.some(service => service.toLowerCase().includes(searchTerm.toLowerCase()));
    const matchesCategory = selectedCategory === 'todos' || prestador.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div>
      <SearchFilters
        searchTerm={searchTerm}
        setSearchTerm={setSearchTerm}
        selectedCategory={selectedCategory}
        setSelectedCategory={setSelectedCategory}
        categories={SERVICE_CATEGORIES}
      />

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredPrestadores.map(prestador => (
          <PrestadorCard
            key={prestador.id}
            prestador={prestador}
            favorites={favorites}
            toggleFavorite={toggleFavorite}
            onContract={contractService}
          />
        ))}
      </div>
    </div>
  );
};

export default BuscarServicos;
