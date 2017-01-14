package com.lab3.todolist.repository;

import com.lab3.todolist.model.Agenda;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lucas on 05/01/17.
 *
 */


public interface AgendaRepository extends MongoRepository<Agenda, String> {

    Agenda findById(String id);
}
