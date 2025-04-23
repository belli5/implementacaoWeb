Feature: Contratação de serviços

  Scenario: Cliente contrata um prestador de serviço com sucesso
    Given os serviços "Conserto de geladeira", "Reparo hidráulico" e "Jardinagem" estão disponíveis
    And o prestador "Luiz Neto" está disponível para o dia "2025-04-23"
    When a cliente "Maria Flor" solicita a contratação do serviço "Jardinagem" com o prestador "Luiz Neto" para o dia "2025-04-23"
    Then o sistema registra a contratação com sucesso
    And associa o pedido de serviço "Jardinagem" ao prestador "Luiz Neto" e à cliente "Maria Flor" para o dia "2025-04-23"

  Scenario: Cliente tenta contratar um prestador indisponível na data escolhida
    Given o prestador "Lucas Arruda" está indisponível para o dia "2025-04-25"
    When a cliente "João Carlos" solicita a contratação do serviço "Reparo hidráulico" com o prestador "Lucas Arruda" para o dia "2025-04-25"
    Then o sistema rejeita a contratação
    And informa que o prestador está indisponível na data selecionada

