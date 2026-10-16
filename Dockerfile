
# ARQUIVO: Dockerfile
#
# RESPONSABILIDADE:
# Construir uma imagem Docker para executar
# o sistema Cadastro de Usuários.
#
# TECNOLOGIAS:
# - Java 21
# - Maven 3.9
# - Spring Boot
# - Docker
#
# ESTRATÉGIA:
# Multi-stage Build
#
# ESTÁGIO 1:
# Compilar e empacotar a aplicação.
#
# ESTÁGIO 2:
# Executar o arquivo JAR gerado.


# ========================================
# ESTÁGIO 1 - COMPILAÇÃO
# ========================================

# Utiliza uma imagem que contém Maven 3.9
# e JDK 21 para compilar o projeto.
#
# AS build:
# Define o nome do estágio de compilação.
FROM maven:3.9-eclipse-temurin-21 AS build

# Define /app como diretório de trabalho
# dentro do contêiner de compilação.
WORKDIR /app

# Copia o pom.xml para o diretório /app.
#
# O pom.xml contém as dependências
# e configurações Maven do projeto.
COPY pom.xml .

# Copia a pasta src do projeto para
# o diretório /app/src.
#
# Essa pasta contém o código-fonte Java
# e os recursos da aplicação.
COPY src ./src

# Executa o Maven para:
#
# clean:
# Limpar os arquivos de builds anteriores.
#
# package:
# Compilar e empacotar a aplicação.
#
# -DskipTests:
# Não executar os testes durante o build.
#
# O resultado esperado é um arquivo JAR
# dentro do diretório target.
RUN mvn clean package -DskipTests


# ========================================
# ESTÁGIO 2 - EXECUÇÃO
# ========================================

# Utiliza uma imagem com Java 21 JRE.
#
# JRE:
# Ambiente necessário para executar
# a aplicação Java já compilada.
#
# alpine:
# Variante baseada no Alpine Linux.
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho
# da aplicação no contêiner final.
WORKDIR /app

# Copia somente o JAR gerado
# no estágio de compilação.
#
# --from=build:
# Indica o estágio de origem.
#
# O arquivo é renomeado para app.jar.
COPY --from=build /app/target/cadastro-usuarios-0.0.1-SNAPSHOT.jar app.jar

# Documenta que a aplicação utiliza
# a porta 8080 dentro do contêiner.
#
# IMPORTANTE:
# EXPOSE não publica a porta no computador.
# O mapeamento é feito no compose.yml.
EXPOSE 8080

# Define o comando executado quando
# o contêiner da aplicação é iniciado.
#
# java:
# Executa a máquina virtual Java.
#
# -jar:
# Indica a execução de um arquivo JAR.
#
# app.jar:
# Arquivo executável da aplicação.
ENTRYPOINT ["java", "-jar", "app.jar"]
