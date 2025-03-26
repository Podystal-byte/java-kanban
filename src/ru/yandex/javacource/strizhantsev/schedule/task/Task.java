package ru.yandex.javacource.strizhantsev.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private int id;
    private String name;
    private String description;
    private Status status;
    private TypeTask typeTask;
    private Duration duration;
    private LocalDateTime startTime;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = TypeTask.TASK;
    }

    public Task(String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = TypeTask.TASK;
        this.startTime = startTime;
        this.duration = duration;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(long minDur) {
        this.duration = Duration.ofMinutes(minDur);
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public long getDurationInMinute() {
        long minute = getDuration().toMinutes();
        return minute;
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
        return id + "," + typeTask + "," + name + "," + description + "," + status + "," + startTime + "," + duration + "," + getEndTime();
    }
}



