# Use the official Maven image to create a build artifact.
# This image comes with a JDK installation.
FROM maven:3.8.4-openjdk-17 as builder

LABEL authors="gabriel.cerioni"

# Copy the pom.xml and source code
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller JDK runtime image to create the final image
FROM openjdk:17-slim

WORKDIR /app

# Copy the built artifact from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]