package com.lab3.todolist.controllers;

import com.lab3.todolist.models.Agenda;
import com.lab3.todolist.repository.AgendaRepository;
import com.lab3.todolist.routes.Routes;
import com.lab3.todolist.routes.RoutesAgenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping(value = RoutesAgenda.GET_ROUTE)
    public List<Agenda> getAgendas() {
        return agendaRepository.findAll();
    }

    @PostMapping(value = RoutesAgenda.POST_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> addAgenda(@RequestBody Agenda agenda) {
        agendaRepository.save(agenda);

        return new ResponseEntity<>(agenda, HttpStatus.CREATED);
    }

    @PutMapping(value = RoutesAgenda.PUT_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> updateAgenda(@PathVariable(Routes.AGENDA_ID) String id,
                                               @RequestBody Agenda agenda) {

        Agenda searchAgenda = agendaRepository.findById(id);
        updateAgenda(searchAgenda, agenda);
        agendaRepository.save(searchAgenda);

        return new ResponseEntity<>(agenda, HttpStatus.OK);
    }

    @DeleteMapping(value = RoutesAgenda.DELETE_ROUTE)
    public ResponseEntity<Void> deleteAgenda(@PathVariable(Routes.AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);
        agendaRepository.delete(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = RoutesAgenda.DELETE_ALL_ROUTE)
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
