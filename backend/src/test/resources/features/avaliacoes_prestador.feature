# language: pt
Funcionalidade: Visualizar Avaliações Recebidas como Prestador

  Como um prestador
  Eu quero visualizar as avaliações que recebi dos meus clientes
  Para acompanhar o feedback sobre os serviços que realizei.

  Cenário: Visualizar avaliações recebidas com sucesso
    Dado que o prestador está logado no sistema
    E já recebeu avaliações de clientes anteriores
    Quando acessa a área de avaliações recebidas
    Então o sistema exibe uma lista com todas as avaliações recebidas
    E cada avaliação contém a nota, o comentário e o nome do cliente

  Cenário: Prestador visualiza avaliações, mas ainda não recebeu nenhuma
    Dado que o prestador está logado no sistema
    E ainda não recebeu nenhuma avaliação
    Quando acessa a área de avaliações recebidas
    Então o sistema informa que não há avaliações
    E a lista de avaliações está vazia