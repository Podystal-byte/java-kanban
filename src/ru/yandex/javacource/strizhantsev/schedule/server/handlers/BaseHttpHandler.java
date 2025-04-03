package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.google.gson.*;
import com.google.gson.stream.*;
import com.sun.net.httpserver.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;

import java.io.*;
import java.nio.charset.*;
import java.time.*;

public abstract class BaseHttpHandler implements HttpHandler {
    protected final Gson gson;
    protected static final String GET_STATUS = "GET";
    protected static final String POST_STATUS = "POST";
    protected static final String DELETE_STATUS = "DELETE";

    public BaseHttpHandler() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Epic.class, new EpicAdapter())
                .registerTypeAdapter(SubTask.class, new SubTaskAdapter())
                .create();
    }


    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    protected void sendCreated(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, -1);
        exchange.close();
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(406, -1);
        exchange.close();
    }

    protected void sendError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(500, -1);
        exchange.close();
    }

    protected <T> T readRequest(InputStream body, Class<T> clazz) {
        InputStreamReader reader = new InputStreamReader(body, StandardCharsets.UTF_8);
        return gson.fromJson(reader, clazz);
    }
}