# Use Java 21 runtime
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy built JAR
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
