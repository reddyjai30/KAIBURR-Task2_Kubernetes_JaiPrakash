package com.exampleJaiPrakash.taskapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Random;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

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
