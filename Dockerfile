# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /home/app

# Copy the POM and source code into the image
COPY pom.xml .
COPY src ./src

# Build the application without running tests to speed up the build
RUN mvn clean package -DskipTests

# Stage 2: Create the final runtime image
FROM openjdk:17-oracle
WORKDIR /usr/local/runme

# Copy only the built JAR from the build stage to the runtime stage
COPY --from=build /home/app/target/*.jar ./app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
