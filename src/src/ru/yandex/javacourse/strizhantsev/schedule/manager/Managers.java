package src.ru.yandex.javacourse.strizhantsev.schedule.manager;

import src.ru.yandex.javacourse.strizhantsev.schedule.history.HistoryManager;
import src.ru.yandex.javacourse.strizhantsev.schedule.history.InMemoryHistoryManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(); // Возвращаем экземпляр InMemoryTaskManager
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}