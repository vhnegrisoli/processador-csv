# Projeto: Processador de CSV com Spring Batch, ElasticSearch e Kibana

## Resumo

Projeto de uma API de processamento de CSV em batch utilizando Spring Boot com Spring Batch, e persistindo os dados processados no PostgreSQL e no ElasticSearch, monitorando e consultando dados com Kibana em um ambiente Docker com docker-compose.

## Tecnologias

* **Java 11**
* **Spring Boot 2**
* **Spring Data JPA**
* **Spring Data ElasticSearch**
* **Spring Security**
* **OAuth2.0 Authentication**
* **ElasticSearch 7.9.3**
* **Kibana 7.9.3**
* **PostgreSQL**
* **REST Api**
* **Docker**
* **Docker-compose*

### Pré-requisitos

É possível inicializar o projeto localmente via Docker ou rodando a aplicação completa na máquina.

#### Para rodar via Docker com *docker-compose*

Processo de criação:

O projeto está composto por um `Dockerfile` que contém um `Multi-Stage Build` da aplicação, que roda primeiramente 
uma imagem do `Gradle`, que por fim instala todas as dependências necessárias, roda os testes automatizados (unitários e de integração) e
por fim gera um `jar` de execução.
 
O segundo estágio do `Dockerfile` copia o `jar` gerado pelo primeiro estágio ao segundo estágio, que por fim expõe
a porta `8080` e o executa. Há também no projeto um arquivo `docker-compose.yml`, que, ao ser executado, executa os containers:

```   
* es01                (ElasticSearch)
* kib01               (Kibana)
* processador-csv-api (Aplicação Spring)
* processador-csv-db  (Banco de dados PostgreSQL 11)
```

Processo de execução:

Para realizar o build das imagens e inicializar todos os containers, basta executar:

`docker-compose up --build`

Para não acompanhar os logs, apenas execute com a flag `-d` ao fim do comando. Para não realizar o build, apenas remova a flag `--build`.

Para parar todos os containers:

`docker-compose stop`

Para apagar todos os containers parados:

`docker container prune`

Em seguida, aperte `y` para confirmar a exlcusão.

Para apagar todas as imagens geradas (caso os containers estejam parados):

`docker image prune`

Em seguida, aperte `y` para confirmar a exlcusão.

Para acompanhar os logs de qualquer container, basta executar:

`docker logs --follow NOME_DO_CONTAINER`

#### Para rodar localmente via IDE ou Maven

É necessário ter as seguintes ferramentas para inicializar o projeto:

```
Java 11
Gradle
gradlew
Docker (para inicializar o ElasticSearch e o Kibana)
```

### Instalação

Primeiramente, rode a instalação através da mvn, sem os testes:

```
gradle build
```

## Iniciando a aplicação

Após instalar a aplicação, dar o build e gerar o jar, basta, na raiz, executar:

```
gradle bootRun
```

Ou então:

```
cd build/libs/java -jar nome_do_jar.jar
```

A aplicação estará disponível em:

```
http://localhost:8080
```

## Autores

* **Victor Hugo Negrisoli** - *Desenvolvedor Back-End Sênior* - [vhnegrisoli](https://github.com/vhnegrisoli)

## Licença

Este projeto possui a licença do MIT. Veja mais em: [LICENSE.txt](LICENSE.txt)
