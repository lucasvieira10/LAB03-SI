package com.lab3.todolist.controllers;

import com.lab3.todolist.models.Agenda;
import com.lab3.todolist.models.Subtarefa;
import com.lab3.todolist.models.Tarefa;
import com.lab3.todolist.repository.AgendaRepository;
import com.lab3.todolist.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SubtaskController that contains the methods for HTTP requests.
 *
 * @author Lucas L. Vieira.
 */
@RestController
public class SubtaskController extends Routes {

    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID + SUBTASK_ROUTE)
    public List<Subtarefa> getSubTasks(@PathVariable(AGENDA_ID) String agendaID,
                                       @PathVariable(TASK_ID) String taskId) {

        Agenda agenda = agendaRepository.findById(agendaID);

        List<Tarefa> tarefas = agenda.getTarefas();

        for (Tarefa t : tarefas) {
            if (t.getId().equals(taskId)) {
                return t.getSubtarefas();
            }
        }

        return null;
    }

    @PostMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID + SUBTASK_ROUTE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Subtarefa> addSubtask(@PathVariable(AGENDA_ID) String agendaID,
                                                @PathVariable(TASK_ID) String taskId,
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

    @DeleteMapping(value = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE + TASK_ROUTE_ID
            + SUBTASK_ROUTE + SUBTASK_ROUTE_ID)
    public ResponseEntity<Void> deleteSubtask(@PathVariable(AGENDA_ID) String agendaID,
                                              @PathVariable(TASK_ID) String taskId,
                                              @PathVariable(SUBTASK_ID) String subTaskID) {

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
