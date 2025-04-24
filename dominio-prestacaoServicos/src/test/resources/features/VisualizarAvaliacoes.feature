Feature: Visualização de Avaliações pelo Prestador

  Scenario: Visualizar avaliações recebidas com sucesso
    Given que o prestador "Rivaldo" possui avaliações de clientes sobre o seus serviços: "5 estrelas, ótimo serviço!, mariana lopes", "2 estrelas, demorou para chegar, josé carlos" e "4 estrelas, bem humorado, ana lins"
    When o prestador solicita a listagem de suas avaliações recebidas
    Then o sistema retorna todas as avaliações associadas a seus serviços concluídos, "5 estrelas, ótimo serviço!", "2 estrelas, demorou para chegar" e "4 estrelas, bem humorado"

  Scenario: Prestador visualiza avaliações, mas ainda não recebeu nenhuma
    Given que o prestador "Rivaldo" ainda não recebeu avaliações de nenhum cliente
    When ele solicita a listagem de avaliações recebidas
    Then o sistema informa que não há avaliações registradas para esse prestador
