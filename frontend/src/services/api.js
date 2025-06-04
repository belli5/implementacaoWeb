import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // ajuste conforme seu backend
});

// Toda requisição incluirá, se existir, o header Basic Auth salvo em localStorage
api.interceptors.request.use((config) => {
  const basicToken = localStorage.getItem('basicToken');
  if (basicToken) {
    config.headers['Authorization'] = basicToken;
  }
  return config;
});

export default api;
