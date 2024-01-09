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
