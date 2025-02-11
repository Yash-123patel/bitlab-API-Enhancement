# Build Stage: Compile the Spring Boot Application
FROM maven:3.8.2-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage: Create the final image for running the application and LibreOffice
FROM openjdk:17-jdk-slim

# Install LibreOffice and required dependencies
RUN apt-get update && apt-get install -y \
    libreoffice \
    libfontconfig1 \
    fonts-dejavu \
    && apt-get clean

# Copy the built application from the build stage
COPY --from=build /target/docker-spring-boot.jar TalentTrack.jar

# Expose the port
EXPOSE 8080

# Entry point for the Spring Boot application
ENTRYPOINT ["java", "-jar", "TalentTrack.jar"]
