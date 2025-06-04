# Projeto de Requisitos

Este repositório contém a especificação de requisitos para o sistema Prestação de Serviços, que conecta clientes a prestadores (ex: eletricistas, encanadores, mecânicos) e gerencia todo o ciclo de contratação, avaliação e histórico de serviços.

# 📖 Domínio - Aplicativo de Prestação de Serviços

O sistema conecta prestadores de serviços como pedreiros, marceneiros, encanadores, eletricistas e mecânicos com clientes que estão em busca de profissionais para realizar serviços em suas residências ou empresas. A plataforma facilita o cadastro de profissionais, a criação de pedidos de serviço por parte dos clientes e o processo de contratação.

Cliente: Pessoa que contrata serviços.

Prestador: Profissional que oferece serviços (ex: encanador, eletricista).

Serviço: Trabalho específico solicitado por um cliente e realizado pelo prestador.

# 🗣️ Linguagem Onipresente

Prestador: Profissionais que oferecem um ou mais tipos de serviços (ex: encanador, eletricista etc.).

Cliente: Pessoa que pretende contratar um prestador para realizar algum tipo de serviço.

Serviço: Tipo de trabalho que um prestador pode oferecer (ex: instalar um chuveiro, reformar a cozinha).

Pedido de Serviço: Solicitação feita pelo cliente detalhando o serviço que necessita, o local e quando deseja (data e hora).

Proposta: Resposta de um prestador interessado em realizar o pedido, incluindo preço e prazo.

Contratação: Ação em que o cliente escolhe um prestador para realizar um serviço solicitado.

Avaliação: Feedback dado por um cliente após a conclusão do serviço, atribuindo uma nota e um comentário.

# 🚀 Documentação Externa

Histórias e Cenários BDD: https://docs.google.com/document/d/1AXrL6elS9H3-nyVWdiUt-6mJM2_Uifz94ZalWvoYugE/edit?usp=sharing

Mapa de Histórias e Personas: https://www.figma.com/design/N4CYtO4mzZJMHILO6nxinc/Projetos-Requisitos?node-id=47-1200&p=f&t=ljenmHa3GwALgNdG-0

Protótipos de Baixa Fidelidade: https://www.figma.com/design/N4CYtO4mzZJMHILO6nxinc/Projetos-Requisitos?node-id=0-1&p=f&t=ljenmHa3GwALgNdG-0

## Histórias Implementadas 

- Como cliente, desejo contratar um prestador de serviço para resolver uma necessidade específica, de forma prática e segura.
- Como cliente, quero adicionar prestadores à minha lista de favoritos, para facilitar futuras contratações de profissionais personalizados.
- Como cliente, desejo filtrar os serviços por categoria para encontrar prestadores que atendam minhas necessidades específicas.
- Como prestador, quero editar meu perfil para alterar meus serviços ofertados.
- Como cliente, desejo avaliar o prestador após a conclusão do serviço para registrar minha satisfação com o atendimento recebido.
- Como prestador, desejo visualizar as avaliações recebidas dos meus clientes, para acompanhar o feedback sobre os serviços que realizei.
- Como cliente, desejo visualizar o histórico dos meus serviços contratados para acompanhar os serviços realizados anteriormente.
- Como cliente, desejo agendar novamente um serviço que já contratei anteriormente, para facilitar contratações recorrentes.
- Como prestador, desejo avaliar o cliente após a conclusão do serviço para registrar minha experiência com ele.


## Padrões de Projeto Adotados
No backend da aplicação, utilizamos o padrão de projeto comportamental Strategy para a validação de entidades Prestador. Este padrão nos permite definir uma família de algoritmos (neste caso, diferentes lógicas de validação para Prestador). Podem ser encontradas na pasta ``backend/src/main/java/com/exemple/backend/dominio/strategies/``

Também foi utilizado o Template Method para definir o esqueleto de um algoritmo em uma superclasse, permitindo que as subclasses sobrescrevam certos passos do algoritmo sem mudar a estrutura geral. Isso pode ser encontrado dentro de services e nos Services de Prestador e Cliente.


## Como Executar
É necessário executar o backend(Spring Boot) e o frontend(Angular) para rodar o projeto. 

Para isso, precisamos ter os seguintes softwares instalados:
- JDK 17 ou acima (https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

- Maven (https://maven.apache.org/download.cgi)

- Node.js (https://nodejs.org/en/download)

- Angular( execute npm install -g @angular/cli para instalar globalmente)

- PostgresSQL 

####  Execução do backend:
	
 Basta navegar até o diretório do backend:
	``\mplementacaoWeb\backend\src\main\java\com\exemple\backend``
 
 E executar os seguintes comandos:

``
mvn clean install 
mvn spring-boot:run
``

#### Execução do frontend:

Estando no diretório 
		``\implementacaoWeb\frontend``

Instalação dos packages: ``npm i`` e ``npm install -g @angular/cli``

Para ligar o frontend e acessar página inicial, executar o comando: ``ng serve --open``


#### Postgres

Nome da DB: requisitos_db
Nome do usuário: teste123
senha: 12345test




# 👥 Grupo

Gabriel Belliato, Gabriel Andrade, Victor Guilherme, Leticia Gomes, Talita Fraga, Julia Felix, Rafaela Vidal, Antonio Paulo Barros, Clara Machado.
