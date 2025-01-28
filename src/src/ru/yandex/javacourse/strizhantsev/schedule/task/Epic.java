package src.ru.yandex.javacourse.strizhantsev.schedule.task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
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
}
