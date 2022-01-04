<h1>Desafio By Coders</h1>

    O serviço se resume em importar transferencais bancarias serializadas em protobuf e listar as mesmas
através de um endpoint.

<h2>Como executar ?</h2>

1. Baixe o projeto através do Git ou zip, caso seja um zip deverá ser descompacto antes de seguir para o proximo passo.
2. Através do terminal utilize o comando .\mvnw spring-boot:run
3. Acesse http://localhost:8080/api/v1/statement?clientId=1
4. Para acessar o banco acesse: http://localhost:8080/h2/console e pressione em "Connect"

<h2>Estrutura</h2>
 - commands - payloads da aplicação
 - config - configuração das dependencias, no caso o TestConfig realiza uma especie de bootstrap para a aplicação já começar com alguns dados
 - controllers - controladores ou resourcers
 - core - classes abstratas ou interfaces que garante determinados comportamentos padrões
 - domain - A regra de negocio da aplicação
   - entities - Entidades do negocio
   - enums - Enums
   - factories - factory pattern para criação das entidades e value objects
   - interfaces - contratros da camada de dominio para serviços da camada de aplicação
   - value objects - Objetos de valores, agregam informações das entidades
 - exceptions - Exceções do sistema
 - repositories - camada de acesso aos dados, devem respeitar o contrato do dominio
 - use case - Operações que o sistema pode realizar
 - utils - utilitarios
 - proto - Modelo dos objetos protobuf



