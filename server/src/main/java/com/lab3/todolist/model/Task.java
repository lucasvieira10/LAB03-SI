package com.lab3.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Lucas L. Vieira.
 *
 * This class represent a task.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    private String id;
    private String nome;
    private int prioridade;
    private boolean selecionada;
    private String comentario;
    private String categoria;

    public Task() {}

    public Task(String id, String nome, int prioridade, boolean selecionada, String comentario, String categoria) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.selecionada = selecionada;
        this.comentario = comentario;
        this.categoria = categoria;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return nome != null ? nome.equals(task.nome) : task.nome == null;

    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", prioridade=" + prioridade +
                ", selecionada=" + selecionada +
                ", comentario='" + comentario + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
