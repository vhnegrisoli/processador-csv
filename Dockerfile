FROM gradle:6.6.1-jdk11 AS build
COPY --chown=gradle:gradle . /usr/processador-csv/src
WORKDIR /usr/processador-csv/src
RUN gradle build
CMD ["gradle", "bootRun"]