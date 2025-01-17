package ru.yandex.javacourse.strizhantsev.schedule.task;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void removeSubtask(int id) {
        subtaskIds.remove(id);
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }
}
