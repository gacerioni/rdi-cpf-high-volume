# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the Docker image
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime container
FROM amazoncorretto:17

LABEL authors="gabriel.cerioni"

# Set working directory in the container
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
