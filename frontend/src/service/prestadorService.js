import api from './api';

export const prestadorService = {
  // Buscar todos os prestadores
  getAll: async (filters = {}) => {
    const response = await api.get('/prestadores', { params: filters });
    return response.data;
  },

  // Buscar prestador por ID
  getById: async (id) => {
    const response = await api.get(`/prestadores/${id}`);
    return response.data;
  },

  // Atualizar perfil do prestador
  updateProfile: async (id, data) => {
    const response = await api.put(`/prestadores/${id}`, data);
    return response.data;
  },

  // Buscar avaliações do prestador
  getRatings: async (id) => {
    const response = await api.get(`/prestadores/${id}/avaliacoes`);
    return response.data;
  },

  // Contratar serviço
  contractService: async (prestadorId, serviceData) => {
    const response = await api.post(`/prestadores/${prestadorId}/contratar`, serviceData);
    return response.data;
  }
};
