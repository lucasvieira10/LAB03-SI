package com.lab3.todolist.controllers;

import com.lab3.todolist.models.Agenda;
import com.lab3.todolist.models.Subtarefa;
import com.lab3.todolist.models.Tarefa;
import com.lab3.todolist.repository.AgendaRepository;
import com.lab3.todolist.routes.Routes;
import com.lab3.todolist.routes.RoutesSubtask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubtaskController {

    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping(value = RoutesSubtask.GET_ROUTE)
    public List<Subtarefa> getSubTasks(@PathVariable(Routes.AGENDA_ID) String agendaID,
                                       @PathVariable(Routes.TASK_ID) String taskId) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                return t.getSubtarefas();
            }
        }

        return null;
    }

    @PostMapping(value = RoutesSubtask.POST_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Subtarefa> addSubtask(@PathVariable(Routes.AGENDA_ID) String agendaID,
                                                @PathVariable(Routes.TASK_ID) String taskId,
                                                @RequestBody Subtarefa subtarefa) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                t.getSubtarefas().add(subtarefa);
                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(subtarefa, HttpStatus.CREATED);
    }

    @PutMapping(value = RoutesSubtask.PUT_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Subtarefa> updateTask(@PathVariable(Routes.AGENDA_ID) String agendaID,
                                                @PathVariable(Routes.TASK_ID) String taskId,
                                                @PathVariable(Routes.SUBTASK_ID) String subTaskID,
                                                @RequestBody Subtarefa subtarefa) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                for (Subtarefa s : t.getSubtarefas()) {
                    if (s.getId().equals(subTaskID)) {
                        s.setSelecionada(subtarefa.isSelecionada());
                        break;
                    }
                }

                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(subtarefa, HttpStatus.OK);
    }

    @DeleteMapping(value = RoutesSubtask.DELETE_ROUTE)
    public ResponseEntity<Void> deleteSubtask(@PathVariable(Routes.AGENDA_ID) String agendaID,
                                              @PathVariable(Routes.TASK_ID) String taskId,
                                              @PathVariable(Routes.SUBTASK_ID) String subTaskID) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                for (Subtarefa s : t.getSubtarefas()) {
                    if (s.getId().equals(subTaskID)) {
                        t.getSubtarefas().remove(s);
                        break;
                    }
                }

                break;
            }
        }

        agendaRepository.save(agenda);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
