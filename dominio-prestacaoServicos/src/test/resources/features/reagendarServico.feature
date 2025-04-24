Feature: Reagendar serviço já contratado


  Scenario: Agendar novamente um serviço já realizado
    Given que o cliente já concluiu um serviço de "Reparo hidráulico" com o prestador "Carlos"
    And o prestador "Carlos" esteja disponível na data "22/07/2025"
    When o cliente solicita um novo agendamento do serviço "Reparo hidráulico" com o prestador "Carlos" para a data "22/07/2025"
    Then o sistema autoriza o reagendamento
    And inicia o processo de criação de um novo pedido de serviço com os dados informados

  Scenario: Prestador indisponível para reagendamento
    Given que o cliente já concluiu um serviço de "Conserto de microondas" com o prestador "João"
    And o prestador "João" esteja indisponível na data "28/04/2025"
    When o cliente solicita um novo agendamento do mesmo serviço para essa data
    Then o sistema rejeita o reagendamento
    And informa que o prestador não está disponível na data escolhida