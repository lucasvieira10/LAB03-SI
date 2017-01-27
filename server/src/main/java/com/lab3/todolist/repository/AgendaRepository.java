package com.lab3.todolist.repository;

import com.lab3.todolist.model.Agenda;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Lucas L. Vieira.
 *
 * This is the representation for the database.
 */
public interface AgendaRepository extends MongoRepository<Agenda, String> {

    Agenda findById(String id);
}
