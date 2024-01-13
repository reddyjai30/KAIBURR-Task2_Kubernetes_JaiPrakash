
![1583864622173-removebg-preview](https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/9f5a6975-67cd-4218-99a8-9e7a9312faa1)

# KAIBURR Coding Assignment
##### Name: Jai Prakash Reddy D 
##### R.no: CB.EN.U4CSE20027
###### Mail: reddyjai30@gmail.com



# Task 2 - Kubernetes

## Overview 
 To accomplish this task, you will need to create Dockerfiles for your application and MongoDB, build Docker images, create Kubernetes manifests, and deploy  them to a Kubernetes cluster. 
 
------  


## Getting Started

### Prerequisites

- **Docker Desktop**: Install the latest version of [Docker Desktop](https://www.docker.com/products/docker-desktop/)


## Setting Up the Project 

### Step 1: Create Dockerfiles
   Create a Dockerfile in the root of your Java project
   ```bash
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
```
<img width="1000" alt="Screenshot 2024-01-14 at 1 39 19 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/87e6b0a0-5915-4b3b-8ed3-75646193ecf7">


### Step 2: Build the Docker Image
In your terminal, ensure you are still in the root directory of your project where the Dockerfile is located. Run the following command to build the Docker image

#### We will add a edit the `application.properties` to make the mongoDB get conneted within the docker and not locally
<img width="1000" alt="Screenshot 2024-01-14 at 1 56 05 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/922a4ebb-cbad-4dab-b747-80950fa25c26">

```bash
docker build -t task-api .
```
<img width="1000" alt="Screenshot 2024-01-14 at 1 53 18 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/8819eb37-89a8-414d-99a9-62872d7f16d2">

<img width="1000" alt="Screenshot 2024-01-14 at 1 53 41 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/0a4806f8-459c-47a4-8520-4a958d132686">


### View the docker Images
```docker
docker images
```
<img width="1280" alt="Screenshot 2024-01-14 at 2 13 58 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/e0ed2f13-c4c0-432d-b4c6-d09e70bde289">

#### Running the Container
```bash
docker run -p 8080:8080 task-api
```
<img width="1280" alt="Screenshot 2024-01-14 at 2 14 25 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/05355d9a-3f42-4f16-b231-2a1b718fa08a">


### Testing the Connection
- Check your local MongoDB database to see if the new entry appears.
  
  `POSTMAN`
  <img width="1000" alt="Screenshot 2024-01-14 at 2 16 05 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/b7033bcd-0b04-47ab-a31e-8266260efa6a">

  `MongoDb`
  <img width="1000" alt="Screenshot 2024-01-14 at 2 16 43 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/f5ce902c-09eb-4152-a714-56675b6e4433">



