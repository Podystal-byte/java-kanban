package ru.yandex.javacource.strizhantsev.schedule.manager;

import ru.yandex.javacource.strizhantsev.schedule.history.HistoryManager;
import ru.yandex.javacource.strizhantsev.schedule.history.InMemoryHistoryManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(); // Возвращаем экземпляр InMemoryTaskManager
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBacked() {
        return new FileBackedTaskManager();
    }
}