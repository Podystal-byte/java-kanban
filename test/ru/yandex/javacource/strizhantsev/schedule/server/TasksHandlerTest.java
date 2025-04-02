package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.net.*;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;

class TasksHandlerTest extends HttpTaskServerTestBase {

    @Test
    void addTask_shouldReturn201AndCreateTask() throws Exception {
        String taskJson = """
                {
                          "name": "Спринт 9",
                          "description": "Реализовать HTTP API",
                          "status": "IN_PROGRESS",
                          "startTime": "2023-10-20T10:00:00",
                          "duration": 120
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, manager.getAllTasks().size());
        // я не понимаю, делаю то же самое в Инсомния, все работает корректно
        // плюс когда запускаю тесты по отдельности тоже все ок, думаю какая-то проблема с запуском и остановкой сервера
        // а что именно не знаю
        // уже и так и так делал не знаю как решить, пытался при помощи нейросетки сделать не получилось
        // если можно подскажи

    }

    @Test
    void getTask_shouldReturn200AndTask() throws Exception {
        Task task = new Task("Test", "Desc", Status.NEW);
        manager.addTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/" + task.getId()))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Test"));

    }

    @Test
    void deleteTask_shouldReturn201AndRemoveTask() throws Exception {
        Task task =new Task("Test", "Desc", Status.NEW);
        manager.addTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/" + task.getId()))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(0, manager.getAllTasks().size());
    }
}