package ru.yandex.javacource.strizhantsev.schedule.task;

public class SubTask extends Task {
    private int epicId;
    private TypeTask typeTask;

    public SubTask(String name, String description, Status status) {
        super(name, description, status);
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
        return getId() + "," + typeTask + "," + getName() + "," + getDescription() + "," + getStatus() + "," + getEpicId();
    }
}


