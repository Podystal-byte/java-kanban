package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.Test;
import ru.yandex.javacource.strizhantsev.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }

    @Test
    public void testEpicStatusAllNew() throws IOException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        taskManager.addEpic(epic);
        assertEquals(Status.NEW, taskManager.findEpicById(epic.getId()).getStatus(), "Статус эпика должен быть NEW.");
    }

    @Test
    public void testEpicStatusAllDone() throws IOException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        int epicId = taskManager.addEpic(epic);
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", Status.DONE);
        subTask1.setEpicId(epicId);
        taskManager.addNewSubtask(subTask1);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", Status.DONE);
        subTask2.setEpicId(epicId);
        taskManager.addNewSubtask(subTask2);
        assertEquals(Status.DONE, taskManager.findEpicById(epicId).getStatus(), "Статус эпика должен быть DONE.");
    }

    @Test
    public void testEpicStatusNewAndDone() throws IOException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        int epicId = taskManager.addEpic(epic);
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", Status.NEW);
        subTask1.setEpicId(epicId);
        taskManager.addNewSubtask(subTask1);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", Status.DONE);
        subTask2.setEpicId(epicId);
        taskManager.addNewSubtask(subTask2);
        assertEquals(Status.IN_PROGRESS, taskManager.findEpicById(epicId).getStatus(), "Статус эпика должен быть IN_PROGRESS.");
    }

    @Test
    public void testEpicStatusInProgress() throws IOException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        taskManager.addEpic(epic);
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", Status.IN_PROGRESS);
        subTask1.setEpicId(epic.getId());
        taskManager.addNewSubtask(subTask1);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", Status.IN_PROGRESS);
        subTask2.setEpicId(epic.getId());
        taskManager.addNewSubtask(subTask2);
        assertEquals(Status.IN_PROGRESS, taskManager.findEpicById(epic.getId()).getStatus(), "Статус эпика должен быть IN_PROGRESS.");
    }

    @Test
    public void testNoOverlap() throws IOException {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW,
                LocalDateTime.of(2023, 10, 1, 10, 0), Duration.ofHours(1));

        Task task2 = new Task("Task 2", "Description 2", Status.NEW,
                LocalDateTime.of(2023, 10, 1, 12, 0), Duration.ofHours(1));

        taskManager.addTask(task1);

        assertDoesNotThrow(() -> taskManager.addTask(task2), "Задачи не должны пересекаться.");
    }
}
