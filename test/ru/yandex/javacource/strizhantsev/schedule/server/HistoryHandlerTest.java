package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.net.*;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;

class HistoryHandlerTest extends HttpTaskServerTestBase {

    @Test
    void getHistory_shouldReturn200AndHistory() throws Exception {
        Task task = new Task("Task", "Desc", Status.NEW);
        manager.addTask(task);
        manager.findTaskById(task.getId());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/history"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Task"));
    }
}