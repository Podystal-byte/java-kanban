package ru.yandex.javacource.strizhantsev.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicId;
    private TypeTask typeTask;


    public SubTask(String name, String description, Status status) {
        super(name, description, status);
        this.typeTask = TypeTask.SUBTASK;

    }

    public SubTask(String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(name, description, status, startTime, duration);
        this.typeTask = TypeTask.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getId() + "," + typeTask + "," + getName() + "," + getDescription() + "," + getStatus() + "," + getEpicId() + this.startTime + "," + this.duration + "," + this.endTime;
    }
}


