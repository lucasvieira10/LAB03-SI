package com.lab3.todolist.controllers;

import com.lab3.todolist.models.Agenda;
import com.lab3.todolist.repository.AgendaRepository;
import com.lab3.todolist.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AgendaController that contains the methods for HTTP requests.
 *
 * @author Lucas L. Vieira.
 */
@RestController
public class AgendaController extends Routes {

    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping(value = AGENDA_ROUTE)
    public List<Agenda> getAgendas() {
        return agendaRepository.findAll();
    }

    @PostMapping(value = AGENDA_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> addAgenda(@RequestBody Agenda agenda) {
        agendaRepository.save(agenda);

        return new ResponseEntity<>(agenda, HttpStatus.CREATED);
    }

    @PutMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> updateAgenda(@PathVariable(AGENDA_ID) String id, @RequestBody Agenda agenda) {

        Agenda searchAgenda = agendaRepository.findById(id);
        updateAgenda(searchAgenda, agenda);
        agendaRepository.save(searchAgenda);

        return new ResponseEntity<>(agenda, HttpStatus.OK);
    }

    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID)
    public ResponseEntity<Void> deleteAgenda(@PathVariable(AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);
        agendaRepository.delete(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value =  AGENDA_ROUTE)
    public ResponseEntity<Void> deleteAgendas() {
        agendaRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void updateAgenda(Agenda newAgenda, Agenda oldAgenda) {
        newAgenda.setNome(oldAgenda.getNome());
        newAgenda.setTarefasJaConcluidas(oldAgenda.getTarefasJaConcluidas());
        newAgenda.setTarefasParaCumprir(oldAgenda.getTarefasParaCumprir());
    }
}
