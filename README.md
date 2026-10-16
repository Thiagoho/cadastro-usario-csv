# Sistema de Cadastro de Usuários

## 1. Sobre o projeto

O Sistema de Cadastro de Usuários é uma aplicação web desenvolvida com Java e Spring Boot, com o objetivo de realizar o gerenciamento de usuários por meio de operações de cadastro, consulta, atualização e exclusão (CRUD).

O sistema utiliza Thymeleaf para integrar a interface HTML ao backend, Spring Data JPA para realizar a persistência dos dados e MySQL como banco de dados relacional.

A aplicação também disponibiliza a exportação dos usuários cadastrados para um arquivo CSV, permitindo a consulta dos dados em programas de planilhas.

Para facilitar a configuração e execução do ambiente, o projeto utiliza Docker e Docker Compose, mantendo a aplicação e o banco de dados em contêineres separados.

## 2. Objetivo

Desenvolver uma aplicação web que permita gerenciar informações de usuários, aplicando conceitos de programação orientada a objetos, arquitetura em camadas, regras de negócio, persistência de dados e conteinerização.

O projeto também tem como objetivo colocar em prática os conhecimentos de desenvolvimento backend com Java e Spring Boot e sua integração com uma interface web.

## 3. Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 21 | Linguagem utilizada na aplicação |
| Spring Boot | Framework para desenvolvimento da aplicação web |
| Spring MVC | Gerenciamento de requisições HTTP |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate | Implementação JPA e mapeamento objeto-relacional |
| MySQL 8 | Banco de dados relacional |
| Thymeleaf | Integração entre backend e páginas HTML |
| HTML5 | Estrutura da interface |
| JavaScript | Interatividade do formulário de cadastro e edição |
| Maven | Gerenciamento de dependências e compilação |
| Docker | Conteinerização da aplicação |
| Docker Compose | Configuração e execução dos serviços |

## 4. Funcionalidades implementadas

### 4.1 Cadastro de usuários

Permite cadastrar um novo usuário informando nome, e-mail e telefone.

Antes de salvar o registro, o sistema verifica se o e-mail já está cadastrado.

### 4.2 Listagem de usuários

Exibe os usuários armazenados no banco de dados em uma tabela HTML, apresentando nome, e-mail e telefone.

### 4.3 Atualização de usuários

Permite modificar os dados de um usuário existente.

O sistema verifica se o identificador informado corresponde a um usuário cadastrado e impede a utilização de um e-mail já associado a outro registro.

### 4.4 Exclusão de usuários

Permite excluir um usuário por meio de seu identificador.

A interface apresenta uma confirmação antes do envio da solicitação de exclusão.

### 4.5 Contagem de usuários

Apresenta a quantidade total de usuários cadastrados no sistema.

### 4.6 Exportação CSV

Permite exportar os dados dos usuários para um arquivo denominado `usuarios.csv`.

A exportação inclui nome, e-mail e telefone, com tratamento de aspas e valores nulos.

## 5. Regras de negócio

As principais regras de negócio estão implementadas na classe `UsuarioService`.

**RN01 — E-mail duplicado no cadastro**

O sistema verifica se o e-mail informado já existe no banco de dados antes de cadastrar um novo usuário. Caso exista, a operação é interrompida.

**RN02 — Usuário inexistente**

Ao buscar um usuário por ID, o sistema lança uma exceção quando não encontra o registro solicitado.

**RN03 — Atualização de e-mail**

O usuário pode manter seu próprio e-mail ou alterá-lo para outro disponível. Não é permitido utilizar um e-mail já cadastrado por outro usuário.

**RN04 — Exclusão de usuário**

Antes de solicitar a exclusão, o sistema verifica se o usuário existe.

**RN05 — Exportação de dados**

A exportação utiliza os usuários cadastrados e gera o conteúdo CSV com tratamento dos campos textuais.

## 6. Arquitetura do sistema

O projeto utiliza uma organização em camadas, separando as responsabilidades de apresentação, regras de negócio e persistência.

```text
Navegador
    |
    v
Frontend - HTML / Thymeleaf / JavaScript
    |
    v
UsuarioWebController
    |
    v
UsuarioService
    |
    v
UsuarioRepository
    |
    v
Banco de Dados MySQL
```

Na funcionalidade de exportação CSV, o Controller utiliza o `CsvService`, que consulta os usuários através do `UsuarioService`.

### 6.1 Controller

A classe `UsuarioWebController` recebe as requisições HTTP, encaminha as operações aos serviços e retorna as páginas HTML ou respostas para download.

### 6.2 Service

A classe `UsuarioService` centraliza as regras de negócio e coordena as operações relacionadas aos usuários.

A classe `CsvService` é responsável por gerar o conteúdo da exportação CSV.

### 6.3 Repository

A interface `UsuarioRepository` estende `JpaRepository<Usuario, Long>` e fornece operações de persistência, além do método `existsByEmail()`.

### 6.4 Entity

A classe `Usuario` representa a entidade persistente associada à tabela `usuarios`.

Seus atributos são:

- `id`: identificador do usuário.
- `nome`: nome do usuário.
- `email`: endereço de e-mail.
- `telefone`: número de telefone.

## 7. Estrutura principal do projeto

```text
cadastro-usuarios/
|
|-- src/
|   |-- main/
|       |-- java/
|       |   |-- com/projeto/cadastro/
|       |       |-- CadastroUsuariosApplication.java
|       |       |-- controller/
|       |       |   |-- UsuarioWebController.java
|       |       |-- entities/
|       |       |   |-- Usuario.java
|       |       |-- repository/
|       |       |   |-- UsuarioRepository.java
|       |       |-- service/
|       |           |-- UsuarioService.java
|       |           |-- CsvService.java
|       |
|       |-- resources/
|           |-- templates/
|           |   |-- usuarios.html
|           |-- application.properties
|
|-- pom.xml
|-- Dockerfile
|-- compose.yml
|-- README.md
```

A estrutura representa os arquivos principais apresentados na documentação do projeto.

## 8. Rotas da aplicação

| Método HTTP | Rota | Responsabilidade |
|---|---|---|
| GET | `/usuarios` | Exibir o formulário e listar usuários |
| POST | `/usuarios/salvar` | Cadastrar usuário |
| POST | `/usuarios/atualizar/{id}` | Atualizar usuário |
| POST | `/usuarios/excluir/{id}` | Excluir usuário |
| GET | `/usuarios/exportar-csv` | Exportar usuários para CSV |

As rotas pertencem ao Controller MVC e são utilizadas pela interface HTML.

## 9. Configuração do banco de dados

O sistema utiliza MySQL como banco de dados relacional.

A configuração apresentada no Docker Compose utiliza:

```text
Banco de dados: cadastro_usuarios
Serviço Docker: mysql
Porta interna: 3306
Porta publicada no computador: 3308
Usuário da aplicação: cadastro_user
```

A entidade `Usuario` é mapeada para a tabela `usuarios` por meio das anotações JPA.

A propriedade `spring.jpa.hibernate.ddl-auto=update` permite ao Hibernate tentar atualizar a estrutura do banco de acordo com as entidades.

## 10. Execução com Docker Compose

### Pré-requisitos

Para executar a aplicação utilizando Docker, é necessário ter instalado:

- Docker.
- Docker Compose.

### Passo 1 — Obter o projeto

Clone o repositório ou faça o download dos arquivos do projeto.

### Passo 2 — Configurar o ambiente

Verifique as credenciais de conexão com o MySQL no `compose.yml`.

As senhas utilizadas durante o desenvolvimento devem ser substituídas por credenciais próprias do ambiente e não devem ser publicadas em repositórios.

### Passo 3 — Construir e iniciar os serviços

No terminal, dentro da pasta principal do projeto, execute:

```bash
docker compose up -d --build
```

O Docker Compose construirá a imagem da aplicação e iniciará os serviços MySQL e Spring Boot.

### Passo 4 — Verificar os contêineres

```bash
docker compose ps
```

### Passo 5 — Acessar a aplicação

Com os serviços inicializados, acesse:

http://localhost:8080/usuarios

### Passo 6 — Visualizar os logs

```bash
docker compose logs -f api-app
```

### Passo 7 — Encerrar os serviços

```bash
docker compose down
```

Esse comando encerra e remove os contêineres e a rede do projeto, preservando o volume nomeado de dados.

## 11. Dockerfile

O projeto utiliza um Dockerfile com construção em múltiplos estágios.

No primeiro estágio, Maven 3.9 e JDK 21 são utilizados para compilar e empacotar a aplicação.

No segundo estágio, uma imagem com Java 21 JRE executa o arquivo JAR gerado.

Essa estratégia evita incluir as ferramentas de compilação na imagem final da aplicação.

## 12. Interface web

A interface foi desenvolvida utilizando HTML5, Thymeleaf e JavaScript.

O Thymeleaf recebe as informações enviadas pelo Controller e apresenta os registros na tabela.

O JavaScript permite utilizar o mesmo formulário para cadastrar e editar usuários, alterando os campos e o endereço de envio conforme a operação selecionada.

A exclusão utiliza uma confirmação no navegador antes de enviar a solicitação ao backend.

## 13. Exportação CSV

O sistema disponibiliza a rota:

```text
GET /usuarios/exportar-csv
```

Ao acessar essa rota, o Controller solicita ao `CsvService` a geração do conteúdo CSV.

O arquivo é enviado ao navegador com codificação UTF-8 e marcador BOM para auxiliar a identificação de caracteres acentuados em determinados programas de planilhas.

O formato utiliza ponto e vírgula para separar os campos de cada usuário.

**Observação:** para manter o cabeçalho consistente com os registros, recomenda-se utilizar `NOME;EMAIL;TELEFONE` na primeira linha do arquivo.

## 14. Melhorias futuras

Entre as melhorias planejadas ou recomendadas para a evolução do projeto estão:

- Validação de dados com Jakarta Bean Validation.
- Tratamento de exceções com mensagens amigáveis na interface.
- Restrição de unicidade de e-mail diretamente no banco de dados.
- Implementação de autenticação e autorização.
- Proteção dos dados pessoais dos usuários.
- Testes unitários e de integração.
- Melhorias na responsividade da interface.
- Paginação e filtros de pesquisa.
- Configuração de credenciais por variáveis de ambiente.
- Gerenciamento de migrações do banco com Flyway ou Liquibase.

Essas funcionalidades representam possíveis evoluções e não devem ser confundidas com recursos já implementados.

## 15. Conhecimentos aplicados

Durante o desenvolvimento deste projeto foram utilizados conceitos de:

- Programação orientada a objetos.
- Encapsulamento.
- Injeção de dependências.
- Arquitetura em camadas.
- Desenvolvimento web com Spring MVC.
- Regras de negócio.
- Persistência com Spring Data JPA.
- Mapeamento objeto-relacional.
- Integração com banco de dados MySQL.
- Requisições HTTP GET e POST.
- Templates dinâmicos com Thymeleaf.
- Manipulação de formulários com JavaScript.
- Exportação de arquivos CSV.
- Conteinerização com Docker.
- Orquestração de serviços com Docker Compose.

## 16. Considerações finais

O Sistema de Cadastro de Usuários foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento Java e Spring Boot por meio de uma aplicação web com operações CRUD.

O projeto demonstra a integração entre frontend, backend e banco de dados, além da organização das responsabilidades em camadas e da utilização de Docker para facilitar sua execução.

A implementação também permite futuras evoluções relacionadas à validação de dados, segurança, testes automatizados e melhorias de interface, contribuindo para o aprimoramento das práticas de desenvolvimento de software.
