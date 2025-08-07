# Use an official OpenJDK 21 runtime to match the project
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project definition
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make the mvnw script executable
RUN chmod +x ./mvnw

# Download project dependencies
RUN ./mvnw dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# This is the updated, more robust command
# It builds the app AND copies the JAR in a single step
RUN ./mvnw package -DskipTests && cp target/*.jar app.jar

# This is the command that will run when the container starts
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","app.jar"]
