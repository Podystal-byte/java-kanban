package ru.yandex.javacource.strizhantsev.schedule.history;

import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    void remove(int id);
}
