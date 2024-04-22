# Use an OpenJDK Runtime image
FROM openjdk:17-slim

LABEL authors="gabriel.cerioni"

# Set working directory in the container
WORKDIR /app

# Copy the pre-built jar file from your local target folder to the container
COPY target/rdi-cpf-high-volume-0.1.0-GABS.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
