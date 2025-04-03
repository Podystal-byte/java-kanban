package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.net.*;
import java.net.http.*;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class PrioritizedHandlerTest extends HttpTaskServerTestBase {

    @Test
    void getPrioritized_shouldReturn200AndTasks() throws Exception {
        Task task = new Task("Task", "Desc", Status.NEW, LocalDateTime.now()
                , Duration.ofMinutes(30));
        manager.addTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/prioritized"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Task"));
    }
}