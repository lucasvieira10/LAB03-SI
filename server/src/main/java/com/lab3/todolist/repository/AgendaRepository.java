package com.lab3.todolist.repository;

import com.lab3.todolist.models.Agenda;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This is the representation for the database.
 *
 * @author Lucas L. Vieira.
 */
public interface AgendaRepository extends MongoRepository<Agenda, String> {

    Agenda findById(String id);
}
