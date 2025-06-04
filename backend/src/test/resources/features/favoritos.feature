# language: pt
Funcionalidade: Favoritar prestadores

  Como cliente
  Quero adicionar prestadores à minha lista de favoritos
  Para facilitar futuras contratações de profissionais personalizados

  Cenário: Cliente adiciona um prestador à lista de favoritos
    Dado que o cliente está logado no sistema
    E está visualizando o perfil de um prestador que ainda não está na sua lista de favoritos
    Quando o cliente favoritar o perfil de “João Silva”
    Então o sistema adiciona o prestador “João Silva” à lista de favoritos do cliente
    E exibe uma mensagem confirmando que o prestador foi favoritado com sucesso

  Cenário: Cliente tenta favoritar um prestador que já está na sua lista de favoritos
    Dado que o cliente está logado no sistema
    E está visualizando o perfil de um prestador que já está na lista de favoritos do cliente
    Quando ele favoritar o colaborador
    Então o sistema não o adiciona novamente à lista
    E exibe uma mensagem informando que o prestador já está favoritado

  Cenário: Cliente atinge o limite de prestadores favoritados
    Dado que o cliente está logado
    E já possui 50 prestadores na sua lista de favoritos
    Quando tenta favoritar um novo prestador
    Então o sistema exibe uma mensagem de erro informando que o limite foi atingido
