package ru.yandex.javacource.strizhantsev.schedule.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Objects;
import java.util.Optional;

public class Task {

    private int id;
    private String name;
    private String description;
    private Status status;
    private TypeTask typeTask;
    private Duration duration = Duration.ofMinutes(15);
    private LocalDateTime startTime;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = TypeTask.TASK;
    }

    public Task(String name, String description, Status status, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = TypeTask.TASK;
        this.startTime = startTime;
    }

    public TypeTask getTypeTask() {
        return typeTask;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() throws NullPointerException {
        Optional<LocalDateTime> optionalStartTime = Optional.of(startTime);
        if (optionalStartTime.isPresent()){
            LocalDateTime endTime = LocalDateTime.of(startTime.toLocalDate(), startTime.toLocalTime()).plus(duration);
            return endTime;
        }

       return null;

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
        return id + "," + typeTask + "," + name + "," + description + "," + status;
    }
}



