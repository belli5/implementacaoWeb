export const API_ENDPOINTS = {
  AUTH: '/auth',
  PRESTADORES: '/prestadores',
  CLIENTES: '/clientes',
  SERVICOS: '/servicos',
  AVALIACOES: '/avaliacoes'
};

export const USER_TYPES = {
  CLIENTE: 'cliente',
  PRESTADOR: 'prestador'
};

export const SERVICE_CATEGORIES = [
  { id: 'todos', name: 'Todos os Serviços' },
  { id: 'limpeza', name: 'Limpeza' },
  { id: 'eletrica', name: 'Elétrica' },
  { id: 'encanamento', name: 'Encanamento' },
  { id: 'beleza', name: 'Beleza' },
  { id: 'jardinagem', name: 'Jardinagem' }
];

export const SERVICE_STATUS = {
  PENDING: 'pendente',
  CONFIRMED: 'confirmado',
  IN_PROGRESS: 'em_andamento',
  COMPLETED: 'concluido',
  CANCELLED: 'cancelado'
};

