package com.lab3.todolist.controllers;

import com.lab3.todolist.models.Agenda;
import com.lab3.todolist.models.Tarefa;
import com.lab3.todolist.repository.AgendaRepository;
import com.lab3.todolist.routes.Routes;
import com.lab3.todolist.routes.RoutesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping(value = RoutesTask.GET_TASKS_ALL_AGENDAS_ROUTE)
    public List<Tarefa> getAllTasks() {
        List<Tarefa> tarefas = new ArrayList<>();

        for (Agenda agenda : agendaRepository.findAll()) {
            tarefas.addAll(agenda.getTarefas());
        }

        return tarefas;
    }

    @GetMapping(value = RoutesTask.GET_ALL_ROUTE)
    public List<Tarefa> getTasks(@PathVariable(Routes.AGENDA_ID) String id) {
        Agenda agenda = agendaRepository.findById(id);

        return agenda.getTarefas();
    }

    @PostMapping(value = RoutesTask.POST_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Tarefa> addTask(@PathVariable(Routes.AGENDA_ID) String id, @RequestBody Tarefa tarefa) {
        Agenda agenda = agendaRepository.findById(id);
        agenda.addTarefa(tarefa);
        agendaRepository.save(agenda);

        return new ResponseEntity<>(tarefa, HttpStatus.CREATED);
    }

    @PutMapping(value = RoutesTask.PUT_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Tarefa> updateTask(@PathVariable(Routes.AGENDA_ID) String agendaID,
                                             @PathVariable(Routes.TASK_ID) String taskId,
                                             @RequestBody Tarefa tarefa) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                updateTask(t, tarefa);
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(tarefa, HttpStatus.OK);
    }

    @DeleteMapping(value = RoutesTask.DELETE_ROUTE)
    public ResponseEntity<Void> deleteTask(@PathVariable(Routes.AGENDA_ID) String agendaID,
                                           @PathVariable(Routes.TASK_ID) String taskId) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                tarefas.remove(t);
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = RoutesTask.DELETE_ALL_ROUTE)
    public ResponseEntity<Void> deleteTasks(@PathVariable(Routes.AGENDA_ID) String agendaID) {
        Agenda agenda = agendaRepository.findById(agendaID);
        agenda.getTarefas().clear();
        agendaRepository.save(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void updateTask(Tarefa newTask, Tarefa oldTask) {
        newTask.setNome(oldTask.getNome());
        newTask.setPrioridade(oldTask.getPrioridade());
        newTask.setSelecionada(oldTask.isSelecionada());
        newTask.setComentario(oldTask.getComentario());
        newTask.setCategoria(oldTask.getCategoria());
    }
}
