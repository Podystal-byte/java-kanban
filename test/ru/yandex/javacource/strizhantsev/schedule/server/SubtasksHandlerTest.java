package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;

import java.net.*;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;

class SubtasksHandlerTest extends HttpTaskServerTestBase {

    @Test
    void addSubtask_shouldReturn201AndCreateSubtask() throws Exception {
        Epic epic = new Epic("Parent", "Desc", Status.NEW);
        manager.addEpic(epic);
        String subtaskJson = String.format("""
            {
                "name": "Test Subtask",
                "description": "Test Description",
                "status": "NEW",
                "epicId": %d
            }
            """, epic.getId());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, manager.getAllSubTasks().size());
    }
}