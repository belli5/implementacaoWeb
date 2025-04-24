Feature: Avaliação de cliente após conclusão do serviço

  Scenario: Avaliação de cliente após serviço concluído
    Given o serviço entre o prestador "José" e a cliente "Mariana Souza" foi concluído
    When o prestador "José" envia uma avaliação com nota "5 estrelas" e comentário "Cliente educada e pagou em dia"
    Then o sistema registra a avaliação com sucesso
    And associa a avaliação ao cliente correspondente

  Scenario: Avaliação não permitida antes da conclusão do serviço
    Given o serviço entre o prestador "Mateus" e o cliente "Paulo Lima" ainda está em andamento
    When o prestador tenta registrar uma avaliação para esse serviço
    Then o sistema rejeita a ação
    And informa que o cliente só pode ser avaliado após a conclusão do serviço
