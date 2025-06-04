# language: pt
Funcionalidade: Avaliar Cliente como Prestador

  Como um prestador
  Eu quero avaliar o cliente após a conclusão do serviço
  Para registrar minha experiência com ele.

  Cenário: Avaliação de cliente após serviço concluído
    Dado que o prestador está logado no sistema
    E o serviço com o cliente "Mariana Souza" foi concluído
    Quando o prestador acessa o sistema
    E registra uma avaliação com nota "5 estrelas" e comentário "Cliente educada e pagou em dia"
    Então o sistema salva a avaliação
    E exibe uma mensagem de confirmação do envio

  Cenário: Avaliação não permitida antes da conclusão do serviço
    Dado que o prestador está logado no sistema
    E o serviço com o cliente "Paulo Lima" ainda está em andamento
    Quando acessar a seção "Serviços em andamento"
    Então a ação "Avaliar cliente" não está disponível
    E o sistema informa que a avaliação só pode ser feita após a conclusão do serviço