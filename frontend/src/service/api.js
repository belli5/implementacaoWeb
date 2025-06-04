// src/services/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // URL do seu backend Spring
});

// Antes de cada request, injeta o Basic Token se existir
api.interceptors.request.use(
  (config) => {
    const basicToken = localStorage.getItem('basicToken');
    if (basicToken) {
      config.headers.Authorization = basicToken;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
