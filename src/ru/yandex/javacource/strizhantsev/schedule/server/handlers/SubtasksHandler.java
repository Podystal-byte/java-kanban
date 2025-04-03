package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.strizhantsev.schedule.manager.IntersectionException;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;

import java.io.IOException;
import java.util.List;

public class SubtasksHandler extends BaseHttpHandler {
    private final TaskManager taskManager;

    public SubtasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            switch (method) {
                case GET_STATUS:
                    handleGet(exchange, pathParts);
                    break;
                case POST_STATUS:
                    handlePost(exchange);
                    break;
                case DELETE_STATUS:
                    handleDelete(exchange, pathParts);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendError(exchange);
        }
    }

    private void handleGet(HttpExchange exchange, String[] pathParts) throws IOException {
        if (pathParts.length == 2) {
            List<SubTask> subTasks = taskManager.getAllSubTasks();
            sendText(exchange, gson.toJson(subTasks));
        } else if (pathParts.length == 3) {
            int id = Integer.parseInt(pathParts[2]);
            SubTask subTask = taskManager.findSubTaskById(id);
            if (subTask != null) {
                sendText(exchange, gson.toJson(subTask));
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        SubTask subTask = readRequest(exchange.getRequestBody(), SubTask.class);
        if (taskManager.findEpicById(subTask.getEpicId()) == null) {
            sendNotFound(exchange);
            return;
        }
        try {
            if (subTask.getId() == 0) {
                int subTaskId = taskManager.addNewSubtask(subTask);
                sendCreated(exchange);
            } else {
                SubTask existingSubTask = taskManager.findSubTaskById(subTask.getId());
                if (existingSubTask != null) {
                    taskManager.updateSubtask(subTask);
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
            taskManager.deleteAllSubtasks();
            sendCreated(exchange);
        } else if (pathParts.length == 3) {
            int id = Integer.parseInt(pathParts[2]);
            taskManager.deleteSubtask(id);
            sendCreated(exchange);
        } else {
            sendNotFound(exchange);
        }
    }
}