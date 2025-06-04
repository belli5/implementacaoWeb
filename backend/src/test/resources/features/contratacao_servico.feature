# language: pt
Funcionalidade: Contratar um prestador de serviço

  Como cliente
  Desejo contratar um prestador de serviço para resolver uma necessidade específica
  De forma prática e segura

  Cenário: Cliente contrata um prestador de serviço com sucesso
    Dado que o cliente está logado no sistema
    E há um serviço disponível na plataforma
    Quando ele seleciona o serviço desejado
    E escolhe um prestador da lista
    Então o sistema registra a contratação com o prestador
    E exibe uma confirmação para o cliente

  Cenário: Cliente tenta contratar um prestador indisponível na data escolhida
    Dado que o cliente está logado no sistema
    E está na página de contratação de um serviço
    E o prestador selecionado está indisponível para o dia 25/04/2025
    Quando o cliente tenta agendar o serviço para o dia 25/04/2025
    Então o sistema exibe uma mensagem informando que o prestador está indisponível nesta data
    E a contratação não é realizada
