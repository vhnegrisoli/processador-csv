FROM openjdk:11.0.5-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/processador-csv-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} processador-csv-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/processador-csv-0.0.1-SNAPSHOT.jar"]

# FROM gradle:6.6.1-jdk11 AS build
# COPY --chown=gradle:gradle . /usr/processador-csv/src
# WORKDIR /usr/processador-csv/src
# RUN gradle build -x test
# CMD ["gradle", "bootRun"]