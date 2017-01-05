package com.lab3.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

/**
 * Created by lucas on 05/01/17.
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    @Id
    private String id;

    private String text;
    private String description;
    private boolean checked;
    private int priority;

    public Task() {}

    public Task(String text, String description, boolean checked, int priority) {
        this.text = text;
        this.description = description;
        this.checked = checked;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return text != null ? text.equals(task.text) : task.text == null;

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", checked=" + checked +
                ", priority=" + priority +
                '}';
    }
}
