<h1>Desafio By Coders</h1>

    O serviço se resume em importar transações bancarias serializadas em protobuf e listar as mesmas
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

<h2>Arquitetura</h2>

    O estilo arquitetural adotado foi o estilo cebelo (Onion) visto que conseguimos desacoplar muito bem as camadas de forma simples, sem inserir muita complexidade no projeto

    Como não sabemos quem iria consumir o projeto, os endpoints foram desenvolvidos seguindo o padrão rest.

    Os errors do payload deveriam ser validados no command, porem como nestes caso exigiria realizar todo o parse para isso, e isso significa usar mais recuso computacional para isso, a validação foram incluidas apenas nas entidades. 

    As entidades foram dividas em Clients, Statemente e Client Balance. Os statementes funcionam conforme os extrato bancario, neles conseguimos acompanhar a evolução bancaria de cada cliente enquanto o Client Balance é um snapshot de todas as transferencias realizadas na conta do usuario, assim se quisermos ver o total não vamos precisar ficar realizar window functions em SQl, simplismente recuperamos informações dessa tabela.
    Obs: Para uma base de produção de periodo em periodo, geralmente os bancos realizam de 3 em 3 meses, deveria ser realizar um snapshot da tabela de statements também, e seus discriminados deveria ser transferidos para outra base de dados, assim evitariamos gargalos na base de dados principal. 

<h2>Pontos de melhoria</h2>

    Devido ao espaço curto de tempo para o desenvolvimento alguns pontos ficaram pendentes como:
        - Desacoplar o use case para cadastrar usuario do use case de importar lista de transações
        - Desacoplar o use case de atualizar balanço do usuario
        - Terminar de desenvolver o envio de eventos para: Novo usuario cadastrado, Nova transação importada, Saldo do usuário atualziado
        - Desacoplar validação de erro do use case
        - Implementar paginação, sorts e filtros por parametros no endpoint de recuperar Transações

        
    

