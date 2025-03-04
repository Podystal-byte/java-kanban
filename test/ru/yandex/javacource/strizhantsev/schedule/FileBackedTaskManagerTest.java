package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.Test;
import ru.yandex.javacource.strizhantsev.schedule.manager.FileBackedTaskManager;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManagerTest {

    private FileBackedTaskManager manager = new FileBackedTaskManager();
    ;
    Path filePath = Paths.get(FileBackedTaskManager.FILE_PATH);
    private File file = filePath.toFile();


    @Test
    void saveTasks() throws IOException {
        Task task1 = new Task("Task1", "Description1", Status.NEW);
        Task task2 = new Task("Task2", "Description2", Status.DONE);
        Epic epic = new Epic("Epic1", "Description3", Status.NEW);


        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic);

        manager.save();
        StringBuilder cont = new StringBuilder();
        String content = new String(Files.readAllBytes(file.toPath()));
        assertNotNull(content);
        assertTrue(content.contains("Task1"));
        assertTrue(content.contains("Task2"));
        assertTrue(content.contains("Epic1"));
    }

    @Test
    void loadFromFile() throws IOException {
        Task task1 = new Task("Task1", "Description1", Status.NEW);
        Task task2 = new Task("Task2", "Description2", Status.DONE);
        Epic epic = new Epic("Epic1", "Description3", Status.NEW);


        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic);

        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(2, loadedManager.getAllTasks().size());
        assertEquals(1, loadedManager.getAllEpics().size());
    }


}