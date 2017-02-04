package com.lab3.todolist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarefa {

    private String id;
    private String nome;
    private int prioridade;
    private boolean selecionada;
    private String comentario;
    private String categoria;
    private List<Subtarefa> subtarefas;

    public Tarefa() {
        this.subtarefas = new ArrayList<>();
    }

    public Tarefa(String id, String nome, int prioridade, boolean selecionada,
                  String comentario, String categoria, List<Subtarefa> subtarefas) {

        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.selecionada = selecionada;
        this.comentario = comentario;
        this.categoria = categoria;
        this.subtarefas = subtarefas;
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

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Subtarefa> getSubtarefas() {
        return subtarefas;
    }

    public void setSubtarefas(List<Subtarefa> subtarefas) {
        this.subtarefas = subtarefas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tarefa tarefa = (Tarefa) o;

        return nome != null ? nome.equals(tarefa.nome) : tarefa.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", prioridade=" + prioridade +
                ", selecionada=" + selecionada +
                ", comentario='" + comentario + '\'' +
                ", categoria='" + categoria + '\'' +
                ", subtarefas=" + subtarefas +
                '}';
    }
}
