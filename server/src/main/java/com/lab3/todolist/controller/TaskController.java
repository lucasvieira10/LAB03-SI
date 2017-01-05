package com.lab3.todolist.controller;

import com.lab3.todolist.model.Task;
import com.lab3.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lucas on 05/01/17.
 *
 */

@RestController
public class TaskController {

    private static final String ROUTE = "/tasks";
    private static final String ROUTE_ID = "/{id}";
    private static final String ID = "id";
    private static final String TASK_EXIST = "The task already exists!";
    private static final String SUCCESSFULLY_DELETED = "Task successfully deleted!";
    private static final String NOT_DELETED = "Nonexistent task!";

    @Autowired
    private TaskRepository repository;

    @GetMapping(value = ROUTE)
    public List<Task> getTasks() {
        return repository.findAll();
    }

    @PostMapping(value = ROUTE)
    public ResponseEntity<?> addTask(@ModelAttribute Task task) {
        if (! containsTask(task)) {
            repository.save(task);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(TASK_EXIST, HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = ROUTE + ROUTE_ID)
    public ResponseEntity<String> deleteTask(@PathVariable(ID) String id) {
        Task task = repository.findById(id);

        if (task != null) {
            repository.delete(task);
            return new ResponseEntity<>(SUCCESSFULLY_DELETED, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(NOT_DELETED, HttpStatus.BAD_REQUEST);
    }

    private boolean containsTask(Task task) {
        List<Task> tasks = repository.findAll();

        return tasks.contains(task);
    }
}
