
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


#### Step 2: Deploy to Kubernetes
-**Start your Kubernetes cluster using Minikube**
 - Install minikube using
   ```bash
   brew install minikube
   ```
Starting
```bash
minikube start
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 35 49 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/884d05e3-9de4-44e1-a67e-666e308543e6">

-**Apply the manifests**
```bash
kubectl apply -f mongodb-deployment.yaml
kubectl apply -f mongodb-service.yaml
kubectl apply -f mongodb-pvc.yaml
kubectl apply -f app-deployment.yaml
kubectl apply -f app-service.yaml
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 52 26 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/c9528462-074e-4ac8-90ef-be586eda43bc">


-**Verify Pods**
```bash
kubectl get pods
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 50 30 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/72918408-1a17-47e7-8998-4b2aab7209dc">

-**Start Minikube**
```bash
minikube service task-api-service
```
<img width="1000" alt="Screenshot 2024-01-14 at 2 55 29 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/4e770135-531a-4234-bcd0-80704541b3ca">

<img width="1000" alt="Screenshot 2024-01-14 at 2 56 10 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/9a172d16-82ba-4dc4-8508-0f28850efa6f">


#### Lets Verify if we are able to PUT data and See in the mongoDb pod which is indepent and within the separate pod
##### Lets put some task through POSTMAN

<img width="1000" alt="Screenshot 2024-01-14 at 2 56 33 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/a23b6a2b-f010-4e3a-bded-e230403c3585">

##### Now Lets see if its refected into `http://127.0.0.1:59447/tasks`
<img width="1000" alt="Screenshot 2024-01-14 at 2 58 01 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/35676274-a6a4-4e08-b2c6-8be9fffce2de">

##### Now Lets check our MongoDb Database within the kubernets pod
<img width="1000" alt="Screenshot 2024-01-14 at 3 00 41 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/755fc921-5b4f-4f40-98b0-07789ac6a06c">

 You can see that yes the data which we had inputed got stored in our mongoDB

<img width="1000" alt="Screenshot 2024-01-14 at 3 00 59 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/5bd81303-6951-4208-a3a6-e6b9adaa155f">


### Persistent Volume (PV)
To ensure that a Persistent Volume (PV) is being used to store MongoDB data and that the data persists even if the pod is deleted, we should verify the following:

#### 1. Check Persistent Volume Claim (PVC)
List PVCs:
Run the following command to list all PVCs in your Kubernetes cluster:
```bash
kubectl get pvc
```
<img width="1000" alt="Screenshot 2024-01-14 at 3 17 26 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/3c957c0f-9053-42cf-ae6e-8357a7767421">

Describe PVC:
Get more details about the MongoDB PVC:
```bash
 kubectl describe pvc mongo-pvc
```
<img width="1000" alt="Screenshot 2024-01-14 at 3 18 30 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/bd7aa3ed-9e73-4d4b-8eb3-6571137dc5ab">

#### 2. Test Data Persistence
From the above we have already inserted an entry task in mongoDB withn the cluster, now lets try to delete the above mongodb pod and creat new one and see will the data be there or not

`kubectl get pods`

<img width="1000" alt="Screenshot 2024-01-14 at 3 23 57 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/54b934e9-42ab-4dfa-97b6-67407a9ebe1f">

`kubectl delete pod mongodb-deployment-df7b8b9c-v5zrp`

<img width="1000" alt="Screenshot 2024-01-14 at 3 24 34 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/c39e8dd6-4f64-4eda-92b0-af29b3834e99">

`New Pod Got Created`

<img width="1000" alt="Screenshot 2024-01-14 at 3 25 31 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/288d3d78-865a-4264-a85b-979ef92f0d9e">

##### The data is still present the the mongDb and its persistent
<img width="1280" alt="Screenshot 2024-01-14 at 3 27 18 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/e6977def-5efe-4927-9321-e995594f43a9">



# Conclusion
The successful completion of Task-2 demonstrated a practical application of containerization and Kubernetes in deploying and managing a Java REST API application with MongoDB. The use of Kubernetes facilitated scalable and manageable deployment while ensuring data persistence through PVs and PVCs. This task not only highlighted the importance of proper Kubernetes resource configuration but also reinforced best practices in containerized application deployment and management in a Kubernetes environment.

