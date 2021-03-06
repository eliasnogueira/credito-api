# Credito API
Este projeto foi criado como aplicacão alvo dos testes para o livro [Testes para uma API com Postman e RestAssured](https://leanpub.com/testes-api-postman-rest-assured-v1)

# Softwares necessários
Você precisará dos seguintes softwares para executar este aplicação:
* Java JDK 11+
* Maven configurado no seu classpath

# Sobre a aplicação

## Como inicializar a API
Após ter efetuado o clone do projeto:
1. Navegue até a pasta do projeto pelo Terminal / Prompt de Comando
2. Execute o seguinte comando: `mvn spring-boot:run`
3. Aguarde o seguinte texto aparecer: _Aplicação iniciada! Bons testes!_

## Como alterar a porta padrão a porta
A porta padrão é a `8088`.
Se você deseja alterar a porta padrão para alguma outra de sua escolha, execute o seguinte comando, substituindo
o texto `<PORTA_DE_SUA_ESCOLHA>` para uma porta não utilizada.

Alterando a porta executando a aplicação através do arquivo `.jar`
```shell
java -jar credit-api-VERSION.jar --server-port=8089
```

Alterando a porta executando a aplicação usando `mvn`
```shell
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=<PORTA_DE_SUA_ESCOLHA>
```

## Como acessar a documentação (Swagger)
Após ter iniciado a aplicação acesse o seguinte link: http://localhost:8088/swagger-ui/index.html

# Quer ajudar?
Por favor, leia o [Guia de Contribuição](CONTRIBUTING.md)

