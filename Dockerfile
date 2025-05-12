
# Use an official Maven image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container


COPY target/weather-api-0.0.1-SNAPSHOT.jar app.jar

# Set the command to run the application
CMD ["java", "-jar", "app.jar"]

# commands used
# docker build -t lib-demo:v1 .
# docker run -p 8080:8080 lib-demo:v1
# docker run -p 8080:8081 lib-demo:v1
# docker run -p 8080:8081 lib-demo:v1   # postgress

# docker run -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres