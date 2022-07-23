# Changelog
Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.8.0]

### Added
- Added the openapi spec as a yaml file at /src/main/resources/static
- Added `springdoc-openapi-ui` dependency and related changes
- `Dockerfile` to build the project as an image

### Changed
- Removed the spring-fox annotation from several classes
- Update `spring-boot-starter-parent`, `junit`, `model-mapper`, `hibernate-jpamodelgen` and `lombok` library version
- Update actions in the GitHub workflow

### Removed
- Removed the spring-fox dependency and related code

## [1.7.0] - 15-02-2021

### Changed
- `RestricaoController` agora response o objeto `MensagemDto` por padrão em ambos os métodos com `HttpStatus.OK`
- `RestricaoException` agora possui uma única classe
- `RestricaoAdvice` retorna `HttpStatus.NOT_FOUND`
- Atualização de bibliotecas
  - `junit-5.8.0-M1`
  - `hibernate-jpamodelgen-6.0.0.Alpha8`
  - `modelmapper-2.4.3`
  - `spring-boot-starter-parent-2.5.0`
  - `lombok-1.18.20`

### Removed
- Removido pacote `exception.v1`
- Removido `RestricaoException` no pacote `exception.v2`

## [1.6.2] - 07-02-2021

### Changed
- Alterado exception quando uma simulação pelo atributo nome não é encontrado

### Removed
- Exception para pesquisa por simulação pelo nome

## [1.6.1] - 07-02-2021

### Added
- Adicionado profile padrão do spring (default)
- Exception customizada para pesquisa por nome não encontrado no endpoint the simulações

### Changed
- Documentação atualizada com exemplo de execução do projeto através de arquivo `.jar`
- Atualização de dependências
  - `junit.version-5.7.1`
  - `lombok.version-1.18.18`

## [1.6.0] - 31-01-2020

### Changed
- Alteração do log level para remover a verbosidade do console

## [1.5.0] - 17-01-2020

### Changed
- Correção no Controler de Restrições para versão 2, onde o retorno do detalhe foi alterado para o tipo de restrição
- Adicionado construtor em `MensagemDto`

### Added
- Testes de Integração para Restrições

## [1.4.0] - 09-01-2020

### Added
- Changlog

### Changed
- Atualizado as seguintes bibliotecas
   - `spring-boot-starter-parent` para `2.4.1`
   - `modelmapper` para `2.3.9`
- Atualizado `application.properties` forçando a utilização do swagger model para v2
- Alterado nome do artefato principal (este projeto) para `credit-restricao`


### Added
- Adicionado novas dependências do SpringFox
   - `springfox-boot-starter`
   - `springfox-swagger-ui`
   - `springfox-swagger2`
  
### Removed
- Dependência `springfox-boot-starter`
- Remoção da anotação `@EnableSwagger2` em `SwaggerConfig`
- Removido o passo 4 em _Como inicializar a API_ do `README.md` para evitar erros de entendimento