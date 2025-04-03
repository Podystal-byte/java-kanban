package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.google.gson.*;
import com.google.gson.stream.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;

import java.io.IOException;

public class EpicAdapter extends TypeAdapter<Epic> {

    @Override
    public void write(JsonWriter out, Epic epic) throws IOException {
        out.beginObject();
        out.name("id").value(epic.getId());
        out.name("name").value(epic.getName());
        out.name("description").value(epic.getDescription());
        out.name("status").value(epic.getStatus().toString());
        out.name("typeTask").value(epic.getTypeTask().toString());
        out.name("subtaskIds").jsonValue(new Gson().toJson(epic.getSubtaskIds()));
        out.endObject();
    }

    @Override
    public Epic read(JsonReader in) throws IOException {
        JsonObject json = JsonParser.parseReader(in).getAsJsonObject();

        Epic epic = new Epic(
                json.get("name").getAsString(),
                json.get("description").getAsString(),
                Status.valueOf(json.get("status").getAsString())
        );

        if (json.has("id")) {
            epic.setId(json.get("id").getAsInt());
        }

        if (json.has("subtaskIds")) {
            JsonArray subtasks = json.getAsJsonArray("subtaskIds");
            subtasks.forEach(id -> epic.addSubtaskId(id.getAsInt()));
        }

        return epic;
    }
}