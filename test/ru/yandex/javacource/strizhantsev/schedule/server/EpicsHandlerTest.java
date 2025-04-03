package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;

import java.net.*;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;

class EpicsHandlerTest extends HttpTaskServerTestBase {

    @Test
    void addEpic_shouldReturn201AndCreateEpic() throws Exception {
        String epicJson = """
            {
                "name": "Test Epic",
                "description": "Test Description",
                "status": "NEW"
            }
            """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, manager.getAllEpics().size());
    }
}