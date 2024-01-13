
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
<img width="1280" alt="Screenshot 2024-01-14 at 1 39 19 AM" src="https://github.com/reddyjai30/KAIBURR-Task2_Kubernetes_JaiPrakash/assets/47852931/87e6b0a0-5915-4b3b-8ed3-75646193ecf7">


### Step 2: Build the Docker Image
In your terminal, ensure you are still in the root directory of your project where the Dockerfile is located. Run the following command to build the Docker image
```bash
docker build -t task-api .
```


### Eclipse IDE Setup
Import the generated project into your Eclipse IDE
- Open Eclipse.
- Select `File` > `Import`.
- Choose `Existing Maven Projects` .
- Select and import the project folder.
<img width="1000" alt="Screenshot 2024-01-09 at 3 45 09 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/d3bad256-52b0-439d-a71f-a990f5d3f6a0">

### Configuring MongoDB
  #### 1. Install MongoDB Compass
  You can install MongoDB Compass locally as we done in our case by following the below steps:
  - Download MongoDB from the [MongoDB Compass](https://www.mongodb.com/try/download/compass).
  - Choose the correct version for your OS and follow the installation instructions.
  - Start the MongoDB service on your machine.
<img width="1000" alt="Screenshot 2024-01-09 at 3 57 28 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/1fbdb137-903d-4f5b-b5af-ea64a013140d">

  #### 2. Configuring MongoDB in our Application
  After installation, configure your Spring Boot application to connect to MongoDB.
  - Go to our eclipse project and Navigate under the section of `src/main/resources` and you will find `application.properties`.
  - In `application.properties` copy past the below line of code for establishing connectivity of our Spring Boot to MongoDB locally.
    
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/yourDatabase
```
In my case I have created a database in mongoDb called as `JavaApiJai` so mine will be like :
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/JavaApiJai
```

<img width="1000" alt="Screenshot 2024-01-12 at 12 13 11 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/b4ff2095-72d1-4903-bf07-ca38c38f82f1"> <img width="1000" alt="Screenshot 2024-01-12 at 12 34 32 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/a52b62ff-c432-4fad-9fd7-3777c2111ca5">


### Developing the Application
  #### 1. Create Model Class
  - Create `Task.java` in the model package.
  - Define properties: `name`, `id`, `assignee`, `project`, `startTime`, and a special property `jaiprakashProperty`.
  - Annotate the class with `@Document`, `@Id`, and other relevant JPA annotations.
  #### Task.java
  ```task.java
  package com.exampleJaiPrakash.taskapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Random;

@Document
public class Task {
    @Id
    private String id;
    private String name;
    private String assignee;
    private String project;
    private Date startTime;
    private String jaiprakashProperty; // The dynamic property

    // Constructors

    public Task() {
        // Generate the jaiprakashProperty for a new Task
        generateJaiprakashProperty();
    }

    public Task(String name, String assignee, String project, Date startTime) {
        this.name = name;
        this.assignee = assignee;
        this.project = project;
        this.startTime = startTime;
        generateJaiprakashProperty();
    }

    // Method to generate jaiprakashProperty
    public void generateJaiprakashProperty() {
        String candidateName = "JaiPrakash";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(candidateName.charAt(random.nextInt(candidateName.length())));
        }
        this.jaiprakashProperty = sb.toString();
    }

    // Getters and setters for all fields except for jaiprakashProperty (no setter for it)

    // Other methods, getters and setters

 // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getJaiprakashProperty() {
        return jaiprakashProperty;
    }
    



}
```
<img width="1000" alt="Screenshot 2024-01-12 at 12 44 34 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/ae60dfb7-e250-4c86-af07-50a8a87326c1">

  #### 2. Repository Layer
  - Create `TaskRepository` interface extending `MongoRepository<Task, String>`.
  - This provides basic CRUD operations.

    #### TaskRepository.java
      ```task.java
          package com.exampleJaiPrakash.taskapi.repository;

          import com.exampleJaiPrakash.taskapi.model.Task;
          import org.springframework.data.mongodb.repository.MongoRepository;
          import java.util.List;

          public interface TaskRepository extends MongoRepository<Task, String> {
            List<Task> findByNameContaining(String name);
            List<Task> findFirst10ByAssigneeOrderByStartTime(String assignee);
          }
      ```
<img width="1000" alt="Screenshot 2024-01-12 at 12 51 31 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/17b81bdb-c41c-4f98-99a6-2219e4d421ab">

  #### 3. Service Layer
  - Create a `TaskService` class.
  - Implement business logic for task operations.
  - Include logic to generate a random string for the special property.

#### TaskService.java
```task.java
package com.exampleJaiPrakash.taskapi.service;

import com.exampleJaiPrakash.taskapi.model.Task;
import com.exampleJaiPrakash.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get a single task by ID
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // Add or update a task
    public Task saveTask(Task task) {
        // If the task is new (i.e., has no id), or you want to regenerate the custom property every time
        if (task.getId() == null || task.getId().isEmpty()) {
            task.generateJaiprakashProperty(); // This will generate a new custom property
        }
        return taskRepository.save(task);
    }

    // Delete a task
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    // Find tasks by name containing a string
    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    // Find the first 10 tasks by assignee
    public List<Task> findFirst10TasksByAssignee(String assignee) {
        return taskRepository.findFirst10ByAssigneeOrderByStartTime(assignee);
    }
}

```
<img width="1000" alt="Screenshot 2024-01-12 at 1 00 10 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/f2c40319-b0a1-4ff4-9f85-ae77935ffd07">


  #### 4. Controller Layer
  - Create `TaskController` class.
  - Use `@RestController` and `@RequestMapping`.
  - Define methods for GET, POST, PUT, DELETE requests, corresponding to API endpoints.

    #### TaskController.java
```taskcontroller.java
package com.exampleJaiPrakash.taskapi.controller;

import com.exampleJaiPrakash.taskapi.model.Task;
import com.exampleJaiPrakash.taskapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

 // GET /tasks - Retrieve all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // GET /tasks/{id} - Retrieve a specific task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // PUT /tasks - Create or update a task
    @PutMapping
    public ResponseEntity<Task> createOrUpdateTask(@RequestBody Task task) {
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.ok(savedTask);
    }

    // DELETE /tasks/{id} - Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

 // GET /tasks/search/byName - Find tasks by name
    @GetMapping("/search/byName")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {
        List<Task> tasks = taskService.findTasksByName(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(tasks);
        }
    }

    // GET /tasks/search/byAssignee - Find first 10 tasks by assignee
    @GetMapping("/search/byAssignee")
    public ResponseEntity<List<Task>> findFirst10TasksByAssignee(@RequestParam String assignee) {
        List<Task> tasks = taskService.findFirst10TasksByAssignee(assignee);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(tasks);
        }
    }

}

```

<img width="1000" alt="Screenshot 2024-01-12 at 1 03 26 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/f0af99ce-9c73-447d-a1df-088d339a2943">



## Running the Project
Once you've completed the development and build process, the next step is to run your Spring Boot application.
- Locate the TaskapiApplication.java class (under `src/main/java/com/exampleJaiPrakash/taskapi/TaskapiApplication.java`).
- Right-click on the file and choose "Run".
  <img width="1280" alt="Screenshot 2024-01-12 at 1 18 12 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/ada4acec-9261-4545-af31-dc992b57c58f">

  Once the application is running, it should be accessible on the default port, which is typically 8080, unless specified otherwise in the    application.properties file.
  - You can verify that the application is running correctly by opening a web browser and navigating to http://localhost:8080.
    
    <img width="1000" alt="Screenshot 2024-01-12 at 1 24 01 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/baa95953-a3f3-408b-b082-dfcdd5d59fde">

  - Alternatively, you can use Postman to send a request to the application and check the response.
    
    <img width="1000" alt="Screenshot 2024-01-12 at 1 59 15 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/29107f2b-375c-4d30-9474-af0dbc670e8b">
    <img width="1000" alt="Screenshot 2024-01-12 at 1 59 39 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/f4f76e08-5492-43f8-ba0d-57ea6596839b">


  - After sending the request (`PUT`) then the request gets stored in the mongoDb database too.
    
    <img width="1000" alt="Screenshot 2024-01-12 at 1 59 59 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/50a7b293-dc22-415a-ad13-2c67f33772df">


## Testing the Java Api EndPoints
 I have given 12 random inputs and lets them all, this provides instructions for testing the Task API endpoints using Postman. The API is designed in Spring   Boot and handles various operations  related to `Task` objects.

 ### 1. Retrieve All Tasks

- **Endpoint**: `GET /tasks`
- **Description**: Retrieves all tasks.
  
<img width="1000" alt="Screenshot 2024-01-12 at 2 16 35 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/acad7bb2-a766-4f2b-bbd0-1e6e6c7f1bf8">

 ### 2. Retrieve a Specific Task by ID

- **Endpoint**: `GET /tasks/{id}`
- **Description**: Retrieves a task by its ID.

<img width="1000" alt="Screenshot 2024-01-12 at 2 26 28 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/aa8b775e-d8c0-459e-b4be-f6777e2de3f5">


### 3. Create or Update a Task

- **Endpoint**: `PUT /tasks`
- **Description**: Creates a new task or updates an existing one.

<img width="1000" alt="Screenshot 2024-01-12 at 2 25 04 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/cd9c6aae-6e59-4c2c-9c1b-d0bb6c8950f9">


### 4. Delete a Task by ID

- **Endpoint**: `DELETE /tasks/{id}`
- **Description**: Deletes a task by its ID.
  
<img width="1000" alt="Screenshot 2024-01-12 at 2 23 25 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/c281d15a-aac1-4c2d-ac22-543ec29d3d0a"> <img width="1000" alt="Screenshot 2024-01-12 at 2 25 37 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/7f1ccfa3-f491-4191-a1d9-5d959dfdf2df">

### 5. Find Tasks by Name

- **Endpoint**: `GET /tasks/search/byName`
- **Description**: Finds tasks by their name.
  
<img width="1000" alt="Screenshot 2024-01-12 at 2 33 13 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/82be6297-e725-4b9a-9046-379bf3875685"> <img width="1000" alt="Screenshot 2024-01-12 at 2 35 39 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/8eacffba-b204-4303-87aa-98b4efe23968">


### 6. Find Tasks by Name

- **Endpoint**: `GET /tasks/search/byAssignee`
- **Description**: Finds the first 10 tasks assigned to a specific assignee.
  
  <img width="1000" alt="Screenshot 2024-01-12 at 2 37 58 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/a63d566e-bede-4a35-a00a-2038ec36ea70"> <img width="1000" alt="Screenshot 2024-01-12 at 2 38 43 PM" src="https://github.com/reddyjai30/KAIBURR-Task1_JavaRESTAPI_JaiPrakash/assets/47852931/221c42ae-cb51-4f88-89a0-f3c6863e7e78">



# Conclusion
 After following the comprehensive steps outlined for building, configuring, and testing your Java REST API application using Spring Boot and MongoDB, you     should now have a fully functional application capable of managing "task" objects. This application demonstrates key aspects of RESTful service development,  including creating a robust model class, setting up a database connection, developing service and controller layers for business logic and request handling,  and, importantly, testing the application to ensure it meets the specified requirements.

 From initializing our Spring Boot project with the necessary dependencies to configuring MongoDB for data storage, developing your application by     implementing the model, repository, service, and controller layers, and finally running and thoroughly testing your application with tools like Postman, 
 each step has contributed to building a well-rounded backend service.
