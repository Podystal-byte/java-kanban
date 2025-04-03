package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler {
    private final TaskManager taskManager;

    public HistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            List<Task> history = taskManager.getHistory();
            sendText(exchange, gson.toJson(history));
        } else {
            sendNotFound(exchange);
        }
    }
}