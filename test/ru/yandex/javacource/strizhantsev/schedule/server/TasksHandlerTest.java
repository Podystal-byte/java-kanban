package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.net.*;
import java.net.http.*;

import static org.junit.jupiter.api.Assertions.*;

class TasksHandlerTest extends HttpTaskServerTestBase {

    @Test
    void deleteTask_shouldReturn201AndRemoveTask() throws Exception {
        Task task = new Task("Test", "Desc", Status.NEW);
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