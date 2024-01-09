package com.exampleJaiPrakash.taskapi.repository;

import com.exampleJaiPrakash.taskapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByNameContaining(String name);
    List<Task> findFirst10ByAssigneeOrderByStartTime(String assignee);
}
