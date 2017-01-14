package com.lab3.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 10/01/17.
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agenda {

    @Id
    private String id;

    private String nome;
    private int tarefasParaCumprir;
    private int tarefasJaConcluidas;

    private List<Task> tarefas;

    public Agenda() {
        this.tarefas = new ArrayList<>();
    }

    public Agenda(String nome, int tarefasParaCumprir, int tarefasJaConcluidas, List<Task> tarefas) {
        this.nome = nome;
        this.tarefasParaCumprir = tarefasParaCumprir;
        this.tarefasJaConcluidas = tarefasJaConcluidas;
        this.tarefas = tarefas;
    }

    public void addTarefa(Task task) {
        this.tarefas.add(task);
    }

    public List<Task> obterTarefas() {
        return tarefas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTarefasParaCumprir() {
        return tarefasParaCumprir;
    }

    public void setTarefasParaCumprir(int tarefasParaCumprir) {
        this.tarefasParaCumprir = tarefasParaCumprir;
    }

    public int getTarefasJaConcluidas() {
        return tarefasJaConcluidas;
    }

    public void setTarefasJaConcluidas(int tarefasJaConcluidas) {
        this.tarefasJaConcluidas = tarefasJaConcluidas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agenda that = (Agenda) o;

        return nome != null ? nome.equals(that.nome) : that.nome == null;

    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Agenda{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", tarefasParaCumprir=" + tarefasParaCumprir +
                ", tarefasJaConcluidas=" + tarefasJaConcluidas +
                ", tarefas" + tarefas +
                '}';
    }
}
