Feature: Visualização de histórico de contratações

  Scenario: Cliente possui contratações anteriores.
    Given o cliente já contratou serviços anteriormente sendo eles "Reparo hidráulico", "Conserto de microondas" e "Instalação elétrica"
    When o cliente acessa o histórico de contratações
    Then o sistema exibe uma lista com todos os serviços que já foram contratados, "Reparo hidráulico", "Conserto de microondas" e "Instalação elétrica"

  Scenario: Cliente não possui contratações anteriores.
    Given que o cliente ainda não contratou nenhum serviço
    When o cliente acessa o histórico de contratações
    Then o sistema não informa nenhum item na lista

