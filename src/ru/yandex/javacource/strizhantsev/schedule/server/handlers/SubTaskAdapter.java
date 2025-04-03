package ru.yandex.javacource.strizhantsev.schedule.server.handlers;

import com.google.gson.*;
import com.google.gson.stream.*;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;

import java.io.IOException;
import java.time.*;

public class SubTaskAdapter extends TypeAdapter<SubTask> {

    @Override
    public void write(JsonWriter out, SubTask subTask) throws IOException {
        out.beginObject();
        out.name("id").value(subTask.getId());
        out.name("name").value(subTask.getName());
        out.name("description").value(subTask.getDescription());
        out.name("status").value(subTask.getStatus().toString());
        out.name("typeTask").value(subTask.getTypeTask().toString());
        out.name("epicId").value(subTask.getEpicId());

        if (subTask.getStartTime() != null) {
            out.name("startTime").value(subTask.getStartTime().toString());
        }

        if (subTask.getDuration() != null) {
            out.name("duration").value(subTask.getDuration().toMinutes());
        }

        out.endObject();
    }

    @Override
    public SubTask read(JsonReader in) throws IOException {
        JsonObject json = JsonParser.parseReader(in).getAsJsonObject();

        String name = json.get("name").getAsString();
        String description = json.get("description").getAsString();
        Status status = Status.valueOf(json.get("status").getAsString());
        int epicId = json.get("epicId").getAsInt();

        SubTask subTask = new SubTask(name, description, status);
        subTask.setEpicId(epicId);

        if (json.has("startTime") && json.has("duration")) {
            LocalDateTime startTime = LocalDateTime.parse(json.get("startTime").getAsString());
            Duration duration = Duration.ofMinutes(json.get("duration").getAsLong());
            subTask.setStartTime(startTime);
            subTask.setDuration(duration);
        }

        if (json.has("id")) {
            subTask.setId(json.get("id").getAsInt());
        }

        return subTask;
    }
}