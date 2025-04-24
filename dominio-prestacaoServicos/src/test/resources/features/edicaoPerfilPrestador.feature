Feature: Edição de perfil do prestador

  Como prestador
  Quero editar meu perfil
  Para alterar meus serviços ofertados

  Scenario: Prestador altera serviços ofertados com sucesso
    Given que o prestador oferece apenas "Instalação elétrica"
    When ele adiciona "Reparo hidráulico" à sua lista de serviços e confirma a edição
    Then o sistema atualiza os serviços ofertados para incluir "Instalação elétrica" e "Reparo hidráulico"

  Scenario: Tentar salvar perfil com campos obrigatórios vazios
    Given que o prestador deseja adicionar "Conserto de microondas" aos seus serviços
    When ele tenta salvar a edição sem preencher a descrição do serviço
    Then o sistema rejeita a operação
    And informa que a descrição do serviço é obrigatória
