// src/services/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // URL do seu back-end Spring
});

// Antes de cada request, injeta o Basic Token (caso exista)
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('basicToken');
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
