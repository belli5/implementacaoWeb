

Feature: Filtrar serviços por categoria

  Scenario: Cliente filtra serviços pela categoria desejada
  Given que o sistema possui prestadores para as categorias "Jardineiro", "Eletricista" e "Encanador"
  When o cliente solicitar prestadores da categoria "Eletricista"
  Then o sistema retorna apenas os prestadores associados à categoria "Eletricista"

  Scenario: Cliente não encontra resultados ao aplicar filtros específicos
  Given que o sistema não possui prestadores na categoria "Cozinheiro"
  When o cliente solicitar prestadores da categoria "Cozinheiro"
  Then o sistema retorna uma lista vazia
  And informa que não há prestadores para a categoria selecionada

