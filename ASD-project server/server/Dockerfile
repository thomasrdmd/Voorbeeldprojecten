FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/main/java src/main/java
COPY dockerFiles/server.properties src/main/resources/application.properties

RUN mvn package

FROM openjdk:17.0.2-jdk

WORKDIR /app

COPY --from=build /app/target/veilig-veilen-server-0.0.1-SNAPSHOT.jar server.jar

CMD [ "java", "-jar", "server.jar" ]
