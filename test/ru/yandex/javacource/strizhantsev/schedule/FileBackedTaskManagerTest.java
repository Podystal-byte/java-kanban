package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.javacource.strizhantsev.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacource.strizhantsev.schedule.manager.IntersectionException;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    @TempDir
    Path tempDir;

    @Override
    protected FileBackedTaskManager createTaskManager() {
        Path tempFile = tempDir.resolve("test.txt");
        return new FileBackedTaskManager() {
            @Override
            public void save() throws IOException {
                FILE_PATH = tempFile.toString();
                super.save();
            }
        };
    }

    @Test
    public void testSaveAndLoad() throws IOException, IntersectionException {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);

        taskManager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempDir.resolve("test.txt").toFile());

        assertNotNull(loadedManager.findTaskById(taskId), "Задача не была загружена из файла.");
    }
}