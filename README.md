
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
   Create a Dockerfile in the root of your Java project in [Task 1](https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash)
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
<img width="1000" alt="Screenshot 2024-01-14 at 2 13 58 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/e0ed2f13-c4c0-432d-b4c6-d09e70bde289">

#### Running the Container
```bash
docker run -p 8080:8080 task-api
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 14 25 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/05355d9a-3f42-4f16-b231-2a1b718fa08a">


### Testing the Connection
- Check your local MongoDB database to see if the new entry appears.
  
  `POSTMAN`
  <img width="1000" alt="Screenshot 2024-01-14 at 2 16 05 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/b7033bcd-0b04-47ab-a31e-8266260efa6a">

  `MongoDb`
  <img width="1000" alt="Screenshot 2024-01-14 at 2 16 43 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/f5ce902c-09eb-4152-a714-56675b6e4433">


## Kubernetes
Creating Kubernetes manifests for deploying your Java REST API application and MongoDB

### Project Structure
```bash
Java-API-Project/
│
├── src/                         # Java source files
│
├── target/                      # Compiled outputs
│
├── Dockerfile                   # Dockerfile for building your Java application
│
├── pom.xml                      # Maven configuration file
│
├── app-deployment.yaml          # Kubernetes deployment file for the app
├── app-service.yaml             # Kubernetes service file for the app
├── mongodb-deployment.yaml      # Kubernetes deployment file for MongoDB
├── mongodb-service.yaml         # Kubernetes service file for MongoDB
└── mongodb-pvc.yaml             # Kubernetes PersistentVolumeClaim for MongoDB
```


#### STEP-1 Create Kubernetes Manifests
- **Deployment Manifest**
  Create a file named app-deployment.yaml:This manifest describes a deployment for your application

##### app-deployment.yaml
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: task-api-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      app: task-api
  template:
    metadata:
      labels:
        app: task-api
    spec:
      containers:
      - name: task-api
        image: task-api:latest
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATA_MONGODB_URI
          value: "mongodb://mongodb-service:27017/JavaApiJai"
```
  
<img width="1000" alt="Screenshot 2024-01-14 at 2 23 50 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/ca4708b4-e467-4fa1-8077-90904634cfec">



- **Service Manifest**
  Create a file named app-service.yaml:This manifest exposes your application on a specific port (NodePort) on your cluster nodes.

  ##### app-service.yam
```bash
apiVersion: v1
kind: Service
metadata:
  name: task-api-service
spec:
  type: NodePort
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 30007
  selector:
    app: task-api
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 26 04 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/dd2cd904-e319-4a89-8d69-10c3ba238e37">


- **MongoDB Deployment and Service**
  Use the following manifests for deploying MongoDB. It includes a Deployment, Service, and a PersistentVolumeClaim.

  `mongodb-deployment.yaml`
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mongodb-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      role: mongodb
  template:
    metadata:
      labels:
        role: mongodb
    spec:
      containers:
      - name: mongodb
        image: mongo
        ports:
        - containerPort: 27017
        volumeMounts:
        - name: mongo-storage
          mountPath: /data/db
      volumes:
      - name: mongo-storage
        persistentVolumeClaim:
          claimName: mongo-pvc
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 27 21 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/c7c65dfd-efbc-41ba-b3d8-5009076f6f7e">

`mongodb-service.yaml`
```bash
apiVersion: v1
kind: Service
metadata:
  name: mongodb-service
spec:
  ports:
  - port: 27017
    targetPort: 27017
  selector:
    role: mongodb
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 28 01 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/3e7cb86a-e6d0-4668-bde8-a23986be55dd">

`mongodb-pvc.yaml`
```bash
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mongo-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 1Gi
```

<img width="1000" alt="Screenshot 2024-01-14 at 2 28 35 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/9e1387fa-0a21-4e73-b287-fa85a4a47af1">

