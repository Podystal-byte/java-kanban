package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.strizhantsev.schedule.manager.IntersectionException;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.io.IOException;
import java.util.List;

public class TasksHandler extends BaseHttpHandler {
    private final TaskManager taskManager;

    public TasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            switch (method) {
                case "GET" -> handleGet(exchange, pathParts);
                case "POST" -> handlePost(exchange);
                case "DELETE" -> handleDelete(exchange, pathParts);
                default -> sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendError(exchange);
        }
    }

    private void handleGet(HttpExchange exchange, String[] pathParts) throws IOException {
        if (pathParts.length == 2) {
            List<Task> tasks = taskManager.getAllTasks();
            sendText(exchange, gson.toJson(tasks));
        } else if (pathParts.length == 3) {
            try {
                int id = Integer.parseInt(pathParts[2]);
                Task task = taskManager.findTaskById(id);
                if (task != null) {
                    sendText(exchange, gson.toJson(task));
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
        Task task = readRequest(exchange.getRequestBody(), Task.class);
        try {
            if (task.getId() == 0) {
                int taskId = taskManager.addTask(task);
                sendCreated(exchange);
            } else {
                Task existingTask = taskManager.findTaskById(task.getId());
                if (existingTask != null) {
                    taskManager.updateTask(task);
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
            taskManager.deleteAllTasks();
            sendCreated(exchange);
        } else if (pathParts.length == 3) {
            try {
                int id = Integer.parseInt(pathParts[2]);
                taskManager.removeTaskById(id);
                sendCreated(exchange);
            } catch (NumberFormatException e) {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }
}