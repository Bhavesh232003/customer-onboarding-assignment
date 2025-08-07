# Use an official OpenJDK 17 runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project definition
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Add this line to make the mvnw script executable
RUN chmod +x ./mvnw

# Download project dependencies
RUN ./mvnw dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Build the application into a JAR file
RUN ./mvnw package -DskipTests

# Copy the built JAR file to a standard name
COPY target/*.jar app.jar

# This is the command that will run when the container starts
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","app.jar"]
