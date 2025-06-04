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



## Como Executar
É necessário executar o backend(Spring Boot) e o frontend(Angular) para rodar o projeto. 

Para isso, precisamos ter os seguintes softwares instalados:
- JDK 17 ou acima (https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

- Maven (https://maven.apache.org/download.cgi)

- Node.js (https://nodejs.org/en/download)

- Angular( execute npm install -g @angular/cli para instalar globalmente)

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

