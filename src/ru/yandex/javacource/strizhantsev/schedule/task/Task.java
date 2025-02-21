package ru.yandex.javacource.strizhantsev.schedule.task;

import java.util.Objects;

public class Task {

    private int id;
    private String name;
    private String description;
    private Status status;

    public Task(String name, String description, Status status) {

        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + id;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return (id == otherTask.id) && Objects.equals(name, otherTask.name) && Objects.equals(description, otherTask.description) && Objects.equals(status, otherTask.status);
    }

    @Override
    public String toString() {
        return "Task{" + "id = " + id + ", " + "name = " + name + ", " + "description = " + description + ", " + "status = " + status + ".";
    }
}



