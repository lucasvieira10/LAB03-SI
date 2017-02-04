package com.lab3.todolist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subtarefa {

    private String id;
    private String nome;
    private boolean selecionada;

    public Subtarefa() {}

    public Subtarefa(String id, String nome, boolean selecionada) {
        this.id = id;
        this.nome = nome;
        this.selecionada = selecionada;
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

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subtarefa subtarefa = (Subtarefa) o;

        return nome != null ? nome.equals(subtarefa.nome) : subtarefa.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Subtarefa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", selecionada=" + selecionada +
                '}';
    }
}
