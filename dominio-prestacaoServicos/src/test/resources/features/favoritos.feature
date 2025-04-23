Feature: Gerenciamento da Lista de Favoritos

  Scenario: Cliente adiciona um prestador à lista de favoritos
    Given o prestador "João Silva" não está na lista de favoritos
    When o cliente solicita a inclusão de "João Silva" nos seus favoritos
    Then o sistema adiciona o prestador à lista de favoritos
    And confirma a operação

  Scenario: Cliente tenta favoritar um prestador que já está na sua lista de favoritos
    Given o prestador "João Silva" já está na lista de favoritos
    When o cliente tenta adicioná-lo novamente
    Then o sistema não duplica o prestador na lista
    And informa que ele já está favoritado

  Scenario: Cliente atinge o limite de prestadores favoritados
    Given o cliente possui 50 prestadores na lista de favoritos
    And o limite máximo de favoritos por cliente é 50
    When o cliente tenta adicionar um novo prestador à lista
    Then o sistema rejeita a inclusão
    And informa que o limite foi atingido
