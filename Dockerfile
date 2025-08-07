FROM openjdk:17-jdk-slim
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","app.jar"]
