# Changelog
Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.6.0] - 31-01-2020

# Changed
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