# language: pt
Funcionalidade: Visualizar Histórico de Contratações como Cliente

  Como um cliente
  Eu quero visualizar o histórico dos meus serviços contratados
  Para acompanhar os serviços realizados anteriormente.

  Cenário: Visualização de histórico de contratações
    Dado que o cliente está logado no sistema
    E já contratou serviços anteriormente
    Quando acessa o histórico de contratações
    Então o sistema exibe uma lista com todos os serviços contratados

  Cenário: Cliente visualiza histórico, mas ainda não contratou nenhum serviço
    Dado que o cliente está logado no sistema
    E ainda não contratou nenhum serviço
    Quando acessa a área de histórico de contratações
    Então o sistema informa que não há serviços contratados
    E não exibe itens na lista