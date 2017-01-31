package com.lab3.todolist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma agenda.
 *
 * @author Lucas L. Vieira.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Agenda {

    @Id
    private String id;

    private String nome;
    private int tarefasParaCumprir;
    private int tarefasJaConcluidas;

    private List<Tarefa> tarefas;

    public Agenda() {
        this.tarefas = new ArrayList<>();
    }

    public Agenda(String nome, int tarefasParaCumprir, int tarefasJaConcluidas, List<Tarefa> tarefas) {
        this.nome = nome;
        this.tarefasParaCumprir = tarefasParaCumprir;
        this.tarefasJaConcluidas = tarefasJaConcluidas;
        this.tarefas = tarefas;
    }

    public void addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
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

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
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
