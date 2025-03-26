package ru.yandex.javacource.strizhantsev.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();
    private TypeTask typeTask;
    private LocalDateTime endTime;


    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.typeTask = TypeTask.EPIC;
    }

    public Epic(String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(name, description, status, startTime, duration);

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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void updateTime(List<SubTask> subtasks) {
        if (subtasks.isEmpty()) {
            setDuration(Duration.ZERO);
            setStartTime(null);
            return;
        }

        this.setStartTime(subtasks.stream()
                .map(SubTask::getStartTime)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null));
        setDuration(subtasks.stream()
                .map(SubTask::getDuration)
                .reduce(Duration.ZERO, Duration::plus));
        this.endTime = subtasks.stream()
                .map(SubTask::getEndTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

    @Override
    public String toString() {
        return getId() + "," + typeTask + "," + getName() + "," + getDescription() + "," + getStatus() + "," + getStartTime() + "," + getStartTime() + "," + this.endTime;
    }
}
