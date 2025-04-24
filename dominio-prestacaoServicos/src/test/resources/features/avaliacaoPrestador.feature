Feature: Avaliação do prestador após conclusão do serviço

  Scenario: Cliente avalia o prestador após o serviço com sucesso
    Given o serviço entre o cliente "Mariana Souza" e o prestador "José" foi concluído
    When a cliente "Mariana Souza" envia uma avaliação com nota "5 estrelas" para o prestador "José"
    Then a avaliação é registrada com sucesso no sistema

  Scenario: Cliente tenta avaliar antes da finalização do serviço
    Given o serviço entre o cliente "Paulo Lima" e o prestador "Mateus" ainda está em andamento
    When o cliente tenta enviar uma avaliação para o prestador "Mateus"
    Then o sistema rejeita a avaliação
    And informa que o prestador só pode ser avaliado após a conclusão do serviço

  Scenario: Avaliação com campos obrigatórios vazios
    Given o serviço entre o cliente "Gusttavo Lima" e o prestador "João" foi concluído
    When o cliente tenta enviar uma avaliação sem informar a nota
    Then o sistema recusa a avaliação
    And indica que a nota é obrigatória para registrar a avaliação