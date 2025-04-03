package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.strizhantsev.schedule.manager.IntersectionException;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;

import java.io.IOException;
import java.util.List;

public class EpicsHandler extends BaseHttpHandler {
    private final TaskManager taskManager;

    public EpicsHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            switch (method) {
                case GET_STATUS -> handleGet(exchange, pathParts);
                case POST_STATUS -> handlePost(exchange);
                case DELETE_STATUS -> handleDelete(exchange, pathParts);
                default -> sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendError(exchange);
        }
    }

    private void handleGet(HttpExchange exchange, String[] pathParts) throws IOException {
        if (pathParts.length == 2) {
            List<Epic> epics = taskManager.getAllEpics();
            sendText(exchange, gson.toJson(epics));
        } else if (pathParts.length == 3) {
            try {
                int id = Integer.parseInt(pathParts[2]);
                Epic epic = taskManager.findEpicById(id);
                if (epic != null) {
                    sendText(exchange, gson.toJson(epic));
                } else {
                    sendNotFound(exchange);
                }
            } catch (NumberFormatException e) {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Epic epic = readRequest(exchange.getRequestBody(), Epic.class);
        try {
            if (epic.getId() == 0) {
                int epicId = taskManager.addEpic(epic);
                sendCreated(exchange);
            } else {
                Epic existingEpic = taskManager.findEpicById(epic.getId());
                if (existingEpic != null) {
                    taskManager.updateEpic(epic);
                    sendCreated(exchange);
                } else {
                    sendNotFound(exchange);
                }
            }
        } catch (IntersectionException e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange, String[] pathParts) throws IOException {
        if (pathParts.length == 2) {
            taskManager.deleteAllEpics();
            sendCreated(exchange);
        } else if (pathParts.length == 3) {
            try {
                int id = Integer.parseInt(pathParts[2]);
                taskManager.deleteEpic(id);
                sendCreated(exchange);
            } catch (NumberFormatException e) {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }
}