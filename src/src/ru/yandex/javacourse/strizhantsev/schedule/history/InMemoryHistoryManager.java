package src.ru.yandex.javacourse.strizhantsev.schedule.history;

import src.ru.yandex.javacourse.strizhantsev.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private final int MAX_SIZE = 10;

    @Override
    public void add(Task task) {

        if (history.size() >= MAX_SIZE) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
