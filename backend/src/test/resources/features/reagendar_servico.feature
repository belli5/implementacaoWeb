# language: pt
Funcionalidade: Reagendar Serviço como Cliente

  Como um cliente
  Eu quero agendar novamente um serviço que já contratei anteriormente
  Para facilitar contratações recorrentes.

  Cenário: Agendar novamente um serviço já realizado
    Dado que o cliente está logado no sistema
    E já concluiu um serviço com o prestador "Carlos"
    Quando acessar o histórico de contratações
    E solicita o reagendamento do serviço
    Então o sistema exibe o formulário de agendamento

  Cenário: Prestador indisponível para reagendamento
    Dado que o cliente está logado no sistema
    E acessa o histórico de contratações
    Quando solicita o reagendamento de um serviço para a data "28/04/2025"
    E o prestador estiver indisponível nesse dia
    Então o sistema informa que o prestador não está disponível na data escolhida
    E impede a finalização do agendamento