package ru.yandex.javacource.strizhantsev.schedule.server;

import org.junit.jupiter.api.*;
import ru.yandex.javacource.strizhantsev.schedule.manager.*;

import java.io.IOException;
import java.net.http.HttpClient;

public class HttpTaskServerTestBase {
    protected static TaskManager manager;
    protected static HttpTaskServer taskServer;
    protected static HttpClient client;

    @BeforeAll
    static void setUpAll() throws IOException {
        manager = Managers.getDefault();
        taskServer = new HttpTaskServer(manager);
        client = HttpClient.newHttpClient();
    }

    @BeforeEach
    void setUp() {
        manager.deleteAllTasks();
        manager.deleteAllEpics();
        manager.deleteAllSubtasks();
        taskServer.start();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }
}