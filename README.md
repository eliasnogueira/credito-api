# Credito API

Este projeto foi criado como aplicacão alvo dos testes para o livro [Testes para uma API com Postman e RestAssured](https://leanpub.com/testes-api-postman-rest-assured-v1)

# Softwares necessários

Você precisará dos seguintes softwares para executar este aplicação:
* Java JDK 11+
* Maven configurado no seu classpath

# Sobre a aplicação

## Como inicializar a API

### Localmente

Após ter efetuado o clone do projeto:
1. Navegue até a pasta do projeto pelo Terminal / Prompt de Comando
2. Execute o seguinte comando: `mvn spring-boot:run`
3. Aguarde o seguinte texto aparecer: _Aplicação iniciada! Bons testes!_

#### Como alterar a porta padrão a porta

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

### Usando Docker

Você também pode usar Docker para acessae diretamente a aplicação.

1. Inicie o Docker no seu computador
2. Abra o seu Terminal / Prompt de Comando
3. Faca o download da imagem docker: `docker pull eliasnogueira/credito-api `
4. Execute o seguinte comando: `docker run --name credito-api -p 8088:8088 eliasnogueira/credito-api`

Você poderá acessar normalmente a aplicação.

Você pode parar a execução da aplicação executando `docker stop credito-api`, porque anteriormente atribuimos este nome 
no comando de execução.

#### Como alterar a porta padrão a porta

A porta padrão é a `8088`.
Se você deseja alterar a porta padrão para alguma outra de sua escolha, substitua a primeira atribuição de porta no 
comando `docker run`. A seguinda porta deve se manter `8088`.

No exemplo abaixo a porta for alterada para `8089`:

```
docker run --name credito-api -p 8089:8088 eliasnogueira/credito-api
```

## Como acessar a documentação (OpenAPI)
Após ter iniciado a aplicação acesse o seguinte link: http://localhost:8088/swagger-ui/index.html

# Quer ajudar?
Por favor, leia o [Guia de Contribuição](CONTRIBUTING.md)

