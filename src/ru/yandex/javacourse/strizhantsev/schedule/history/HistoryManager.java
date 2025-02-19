package ru.yandex.javacourse.strizhantsev.schedule.history;

import ru.yandex.javacourse.strizhantsev.schedule.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    void remove(int id);
}
