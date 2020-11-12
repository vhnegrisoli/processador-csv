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

## Fluxo de atividade

Dentro do diretório `process`, logo na raiz do projeto, há um arquivo chamado `data.csv`, sendo um CSV sobre a descrição de personagens de quadrinhos da DC e Marvel Comics, contendo exatamente 23.272 linhas de dados, com algumas inválidas, por exemplo, quando o campo YEAR for vazio.

Exemplo de CSV utilizado: 

|page_id|name                          |urlslug                               |ID             |ALIGN          |EYE       |HAIR      |SEX              |GSM|ALIVE            |APPEARANCES|FIRST APPEARANCE|YEAR|
|-------|------------------------------|--------------------------------------|---------------|---------------|----------|----------|-----------------|---|-----------------|-----------|----------------|----|
|1422   |Batman (Bruce Wayne)          |\/wiki\/Batman_(Bruce_Wayne)          |Secret Identity|Good Characters|Blue Eyes |Black Hair|Male Characters  |   |Living Characters|3093       |1939, May       |1939|
|23387  |Superman (Clark Kent)         |\/wiki\/Superman_(Clark_Kent)         |Secret Identity|Good Characters|Blue Eyes |Black Hair|Male Characters  |   |Living Characters|2496       |1986, October   |1986|
|1458   |Green Lantern (Hal Jordan)    |\/wiki\/Green_Lantern_(Hal_Jordan)    |Secret Identity|Good Characters|Brown Eyes|Brown Hair|Male Characters  |   |Living Characters|1565       |1959, October   |1959|
|1659   |James Gordon (New Earth)      |\/wiki\/James_Gordon_(New_Earth)      |Public Identity|Good Characters|Brown Eyes|White Hair|Male Characters  |   |Living Characters|1316       |1987, February  |1987|
|1576   |Richard Grayson (New Earth)   |\/wiki\/Richard_Grayson_(New_Earth)   |Secret Identity|Good Characters|Blue Eyes |Black Hair|Male Characters  |   |Living Characters|1237       |1940, April     |1940|
|1448   |Wonder Woman (Diana Prince)   |\/wiki\/Wonder_Woman_(Diana_Prince)   |Public Identity|Good Characters|Blue Eyes |Black Hair|Female Characters|   |Living Characters|1231       |1941, December  |1941|
|1486   |Aquaman (Arthur Curry)        |\/wiki\/Aquaman_(Arthur_Curry)        |Public Identity|Good Characters|Blue Eyes |Blond Hair|Male Characters  |   |Living Characters|1121       |1941, November  |1941|
|1451   |Timothy Drake (New Earth)     |\/wiki\/Timothy_Drake_(New_Earth)     |Secret Identity|Good Characters|Blue Eyes |Black Hair|Male Characters  |   |Living Characters|1095       |1989, August    |1989|
|71760  |Dinah Laurel Lance (New Earth)|\/wiki\/Dinah_Laurel_Lance_(New_Earth)|Public Identity|Good Characters|Blue Eyes |Blond Hair|Female Characters|   |Living Characters|1075       |1969, November  |1969|

Ao iniciar a aplicação, será definido um `Job` do `Spring Batch` que irá rodar a cada 1 minuto. O `Job` será responsável por definir um `Reader` para ler os dados do CSV e transformar em um objeto Java do tipo `PersonagemCsvDTO`, um `Processor` para processar e converter o objeto criado anteriormente em um objeto do tipo `Personagem` (uma entidade JPA), e por fim, chamar o `Writer` que irá realizar a operação de `INSERT` dos dados no PostgreSQL. 

O `Job` foi definido com um `chunk` com tamanho 100, ou seja, o processamento será executado de 100 em 100 lotes de processamento.

Ao fim do `Job`, será chamado o `Listener` de finalização, e, neste processo, será realizada a inserção dos dados que estão inseridos no `PostgreSQL` para dentro do `ElasticSearch`, filtrando apenas por dados que estiverem válidos. Por fim, caso existam dados inválidos no `PostgreSQL`, serão removidos.

#### Configurações iniciais do ElasticSearch

É necessário alterar a quantidade máxima de itens permitidos a serem retornados
no resultado de uma busca acima de 10.000 itens. Vamos alterar para 1.000.000 itens.

Para isso, caso esteja usando o `Kibana`, execute a consulta:

````
PUT personagem/_settings
{
  "index" : { 
     "max_result_window" : 1000000 
   } 
}
````

Caso esteja usando uma aplicação para teste de APIs, utilize assim:

Endpoint: `http://localhost:9200/personagem/_settings`

Método: `PUT`

Body: 

```
{
  "index" : { 
     "max_result_window" : 1000000
   } 
}
```

Ou então, via cURL:

```
curl -i -X PUT \
   -H "Content-Type:application/json" \
   -d \
    '{
      "index" : { 
         "max_result_window" : 1000000 
       } 
    }' \
 'http://localhost:9200/personagem/_settings'
```

A resposta em qualquer caso será:

HTTP Status Code: `200`

Resposta: 

```
{
    "acknowledged" : true
}
```

## Autores

* **Victor Hugo Negrisoli** - *Desenvolvedor Back-End Sênior* - [vhnegrisoli](https://github.com/vhnegrisoli)

## Licença

Este projeto possui a licença do MIT. Veja mais em: [LICENSE](LICENSE)
