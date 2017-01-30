package com.lab3.todolist.controller;

import com.lab3.todolist.models.Agenda;
import com.lab3.todolist.models.Task;
import com.lab3.todolist.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * AgendaController that contains the methods for HTTP requests.
 *
 * @author Lucas L. Vieira.
 */
@RestController
public class AgendaController {

    private static final String AGENDA_ROUTE = "/agendas";
    private static final String TASK_ROUTE = "/tasks";
    private static final String AGENDA_ROUTE_ID = "/{route_id}";
    private static final String AGENDA_ID = "route_id";
    private static final String TASK_ROUTE_ID = "/{task_id}";
    private static final String TASK_ID = "task_id";

    @Autowired
    private AgendaRepository agendaRepository;

    /**
     * This method return all of saved agendas.
     *
     * @return agendas.
     */
    @GetMapping(value = AGENDA_ROUTE)
    public List<Agenda> getAgendas() {
        return agendaRepository.findAll();
    }

    /**
     * This method save an agenda.
     *
     * @param agenda for save.
     * @return agenda saved.
     */
    @PostMapping(value = AGENDA_ROUTE,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> addAgenda(@RequestBody Agenda agenda) {
        agendaRepository.save(agenda);

        return new ResponseEntity<>(agenda, HttpStatus.CREATED);
    }

    /**
     * This method edit a specific agenda using your ID.
     *
     * @param id of a agenda.
     * @param agenda for edit.
     * @return agenda edited.
     */
    @PutMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Agenda> updateAgenda(@PathVariable(AGENDA_ID) String id,
                                               @RequestBody Agenda agenda) {

        Agenda searchAgenda = agendaRepository.findById(id);

        searchAgenda.setNome(agenda.getNome());
        searchAgenda.setTarefasJaConcluidas(agenda.getTarefasJaConcluidas());
        searchAgenda.setTarefasParaCumprir(agenda.getTarefasParaCumprir());

        agendaRepository.save(searchAgenda);

        return new ResponseEntity<>(agenda, HttpStatus.OK);
    }

    /**
     * This method remove a specific agenda.
     *
     * @param id of a agenda.
     * @return void.
     */
    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID)
    public ResponseEntity<Void> deleteAgenda(@PathVariable(AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);

        if (agenda != null) {
            agendaRepository.delete(agenda);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method remove all of saved agendas.
     *
     * @return void.
     */
    @DeleteMapping(value =  AGENDA_ROUTE)
    public ResponseEntity<Void> deleteAgendas() {
        agendaRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method return all of saved tasks.
     *
     * @return tasks.
     */
    @GetMapping(value = AGENDA_ROUTE + TASK_ROUTE)
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        for (Agenda agenda : agendaRepository.findAll()) {
            tasks.addAll(agenda.getTarefas());
        }

        return tasks;
    }

    /**
     * This method return all of saved tasks in a specific agenda.
     *
     * @param id of a agenda.
     * @return tasks.
     */
    @GetMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE)
    public List<Task> getTasks(@PathVariable(AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);

        return agenda.getTarefas();
    }

    /**
     * This method save a task in a agenda.
     *
     * @param id of a task.
     * @param task for save.
     * @return task saved.
     */
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

    /**
     * This method edit a task in a agenda.
     *
     * @param agendaID of an agenda that contains the task.
     * @param taskId of a task.
     * @param task for edit.
     * @return task edited.
     */
    @PutMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Task> updateTask(@PathVariable(AGENDA_ID) String agendaID,
                                           @PathVariable(TASK_ID) String taskId,
                                           @RequestBody Task task) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Task> tasks = agenda.getTarefas();

        for (Task t : tasks) {
            if (t.getId().equals(taskId)) {
                t.setNome(task.getNome());
                t.setPrioridade(task.getPrioridade());
                t.setSelecionada(task.isSelecionada());
                t.setComentario(task.getComentario());
                t.setCategoria(task.getCategoria());
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * This method remove a task in a agenda.
     *
     * @param agendaID of an agenda that contains the task.
     * @param taskId of a task.
     * @return void.
     */
    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID)
    public ResponseEntity<Void> deleteTask(@PathVariable(AGENDA_ID) String agendaID,
                                           @PathVariable(TASK_ID) String taskId) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Task> tasks = agenda.getTarefas();

        for (Task t : tasks) {
            if (t.getId().equals(taskId)) {
                tasks.remove(t);
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method remove all of tasks in an agenda.
     *
     * @param agendaID of an agenda that contains the task.
     * @return void.
     */
    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE)
    public ResponseEntity<Void> deleteTasks(@PathVariable(AGENDA_ID) String agendaID) {
        Agenda agenda = agendaRepository.findById(agendaID);

        agenda.getTarefas().clear();

        agendaRepository.save(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
