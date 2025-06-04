import { useState, useEffect } from 'react';

const useFavorites = () => {
  const [favorites, setFavorites] = useState([]);

  // Carrega favoritos do localStorage na inicialização
  useEffect(() => {
    const savedFavorites = localStorage.getItem('favorites');
    if (savedFavorites) {
      setFavorites(JSON.parse(savedFavorites));
    }
  }, []);

  // Salva favoritos no localStorage sempre que mudar
  useEffect(() => {
    localStorage.setItem('favorites', JSON.stringify(favorites));
  }, [favorites]);

  const toggleFavorite = (prestadorId) => {
    setFavorites(prev => 
      prev.includes(prestadorId) 
        ? prev.filter(id => id !== prestadorId)
        : [...prev, prestadorId]
    );
  };

  const isFavorite = (prestadorId) => {
    return favorites.includes(prestadorId);
  };

  return {
    favorites,
    toggleFavorite,
    isFavorite
  };
};

export default useFavorites;