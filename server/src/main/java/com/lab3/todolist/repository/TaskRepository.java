package com.lab3.todolist.repository;

import com.lab3.todolist.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lucas on 05/01/17.
 *
 */


public interface TaskRepository extends MongoRepository<Task, String> {

    Task findById(String id);
}
