package com.lab3.todolist.controller;

import com.lab3.todolist.model.Agenda;
import com.lab3.todolist.model.Task;
import com.lab3.todolist.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lucas on 05/01/17.
 *
 */

@RestController
public class AgendaController {

    private static final String AGENDA_ROUTE = "/agendas";
    private static final String TASK_ROUTE = "/tasks";
    private static final String AGENDA_ROUTE_ID = "/{route_id}";
    private static final String AGENDA_ID = "route_id";
    private static final String TASK_ROUTE_ID = "/{task_id}";
    private static final String TASK_ID = "task_id";
    private static final String SUCCESSFULLY_DELETED = "Agenda successfully deleted!";
    private static final String NOT_DELETED = "Nonexistent Agenda!";
    private static final String ADDED_TASK = "Added task!";
    private static final String UPDATE_AGENDA = "Agenda Updated!";

    @Autowired
    private AgendaRepository agendaRepository;

    /**
     * Agenda Methods.
     */

    @CrossOrigin
    @GetMapping(value = AGENDA_ROUTE)
    public List<Agenda> getAgendas() {
        return agendaRepository.findAll();
    }

    @CrossOrigin
    @PostMapping(value = AGENDA_ROUTE,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> addAgenda(@RequestBody Agenda agenda) {
        agendaRepository.save(agenda);

        return new ResponseEntity<>(agenda, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> updateAgenda(@PathVariable(AGENDA_ID) String id, @RequestBody Agenda agenda) {
        Agenda searchAgenda = agendaRepository.findById(id);

        searchAgenda.setNome(agenda.getNome());
        searchAgenda.setTarefasJaConcluidas(agenda.getTarefasJaConcluidas());
        searchAgenda.setTarefasParaCumprir(agenda.getTarefasParaCumprir());

        agendaRepository.save(searchAgenda);

        return new ResponseEntity<>(agenda, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID)
    public ResponseEntity<String> deleteAgenda(@PathVariable(AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);

        if (agenda != null) {
            agendaRepository.delete(agenda);
            return new ResponseEntity<>(SUCCESSFULLY_DELETED, HttpStatus.OK);
        }

        return new ResponseEntity<>(NOT_DELETED, HttpStatus.BAD_REQUEST);
    }

    /**
     * Task Methods.
     */

    @CrossOrigin
    @GetMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE)
    public List<Task> getTasks(@PathVariable(AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);

        return agenda.obterTarefas();
    }

    @CrossOrigin
    @PostMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Task> addTask(@PathVariable(AGENDA_ID) String id, @RequestBody Task task) {
        Agenda agenda = agendaRepository.findById(id);
        agenda.addTarefa(task);

        agendaRepository.save(agenda);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Task> updateTask(@PathVariable(AGENDA_ID) String agendaID,
                                             @PathVariable(TASK_ID) String taskId,
                                             @RequestBody Task task) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Task> tasks = agenda.obterTarefas();

        for (Task t : tasks) {
            if (t.getId().equals(taskId)) {
                t.setNome(task.getNome());
                t.setPrioridade(task.getPrioridade());
                t.setSelecionada(task.isSelecionada());
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID)
    public ResponseEntity<String> deleteTask(@PathVariable(AGENDA_ID) String agendaID,
                                             @PathVariable(TASK_ID) String taskId) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Task> tasks = agenda.obterTarefas();

        for (Task t : tasks) {
            if (t.getId().equals(taskId)) {
                tasks.remove(t);
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(SUCCESSFULLY_DELETED, HttpStatus.OK);
    }
}
