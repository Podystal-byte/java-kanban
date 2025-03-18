package ru.yandex.javacource.strizhantsev.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();
    private TypeTask typeTask;
    private LocalDateTime startTime;
    private Duration duration;


    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.typeTask = TypeTask.EPIC;
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(List<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public void removeSubtask(int id) {
        subtaskIds.remove(id);
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    @Override
    public String toString() {
        return getId() + "," + typeTask + "," + getName() + "," + getDescription() + "," + getStatus();
    }
}
