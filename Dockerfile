#Docker-Jai
# Use an official Java and Maven runtime as a parent image

FROM maven:3.8.4-openjdk-17

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the project files
COPY . .

# Build the application
RUN mvn install -DskipTests

# Run the jar file 
ENTRYPOINT ["java", "-jar", "target/taskapi-0.0.1-SNAPSHOT.jar"]
