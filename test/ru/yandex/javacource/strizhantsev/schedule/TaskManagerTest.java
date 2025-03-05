package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.strizhantsev.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacource.strizhantsev.schedule.manager.Managers;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private FileBackedTaskManager taskManager;
    private File file;

    @BeforeEach
    void setUp() throws IOException {
        file = File.createTempFile("test", ".txt"); // Используем временный файл
        taskManager = new FileBackedTaskManager();
        FileBackedTaskManager.FILE_PATH = file.getAbsolutePath(); // Устанавливаем путь к временному файлу
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

    @Test
    void equalityOfTasksById() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Экземпляры Task должны быть равны по ID");
    }

    @Test
    void equalityOfSubTasksById() {
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", Status.NEW);
        SubTask subTask2 = new SubTask("SubTask 1", "Description 1", Status.NEW);
        subTask1.setId(2);
        subTask2.setId(2);

        assertEquals(subTask1, subTask2, "Наследники класса должны быть равны по ID");
    }

    @Test
    void checkingAnEpicWhenAddingItToItself() {
        Epic epic = new Epic("Epic 1", "Description", Status.NEW);
        epic.setId(3);
        SubTask subTask = new SubTask("Subtask for Epic", "Description", Status.NEW);

        subTask.setEpicId(epic.getId());

        assertEquals(epic.getId(), subTask.getEpicId(), "Эпик не может быть своей подзадачей");
    }

    @Test
    void checkBehindForAddingYourselfAsAnEpic() {
        SubTask subtask = new SubTask("Subtask", "Description", Status.NEW);
        subtask.setId(4);

        assertNotEquals(subtask.getEpicId(), subtask.getId(), "Подзадача не может быть своим же эпиком");
    }

    @Test
    void returnsInitializedAndReadyToUseManagerInstances() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер должен быть инициализирован");
    }

    @Test
    void inMemoryTaskManagerAddsAndFindsTasks() throws IOException {
        Task task = new Task("Test Task", "Test Description", Status.NEW);
        int id = taskManager.addTask(task);

        assertNotNull(taskManager.findTaskById(id), "Задача должна быть найдена по Id");
    }

    @Test
    void noConflictBetweenTasksWithSameIds() throws IOException {
        Task task1 = new Task("First Task", "Description A", Status.NEW);
        int id = taskManager.addTask(task1);

        Task task2 = new Task("Second Task", "Description B", Status.NEW);
        task2.setId(id);

        assertNotEquals(task2, taskManager.findTaskById(id), "Задачи с одинаковыми ID не должны конфликтовать");
    }

    @Test
    void taskVariabilityTest() throws IOException {
        Task originalTask = new Task("Original Task", "Original Description", Status.NEW);
        int id = taskManager.addTask(originalTask);


        originalTask.setName("Changed Name");

        assertEquals(originalTask.getName(), taskManager.findTaskById(id).getName(),
                "Имя задачи в менеджере поменяется на новое");
    }

    @Test
    void historyPreservesSingleVersionOfTask() throws IOException {
        Task originalTask = new Task("History Test Task", "History Description", Status.NEW);
        int id = taskManager.addTask(originalTask);

        Task fetchedTask = taskManager.findTaskById(id);

        assertNotNull(fetchedTask, "Задача должна быть найдена перед обновлением.");

        fetchedTask.setName("Updated Name");
        taskManager.updateTask(fetchedTask);

        Task updatedTask = taskManager.findTaskById(id);

        assertNotNull(updatedTask, "Задача должна быть найдена после обновления.");

        List<Task> history = taskManager.getHistory();

        assertNotEquals(1, history.size(), "История должна содержать одну версию задачи.");

        assertEquals("Updated Name", history.get(0).getName(),
                "История должна содержать обновленную версию задачи.");
    }

    @Test
    void removeFromHistoryWorksCorrectly() throws IOException {
        Task task1 = new Task("First Task", "Description A", Status.NEW);
        int id1 = taskManager.addTask(task1);

        Task task2 = new Task("Second Task", "Description B", Status.NEW);
        int id2 = taskManager.addTask(task2);

        taskManager.findTaskById(id1);
        taskManager.findTaskById(id2);

        taskManager.removeTaskById(id1);

        List<Task> historyAfterRemoval = taskManager.getHistory();

        assertNotEquals(2, historyAfterRemoval.size(), "История должна содержать одну задачу после удаления.");

        assertEquals(task2.getName(), historyAfterRemoval.get(0).getName(),
                "История должна содержать только вторую задачу.");
    }

    @Test
    void saveTasks() throws IOException {
        Task task1 = new Task("Task1", "Description1", Status.NEW);
        Task task2 = new Task("Task2", "Description2", Status.DONE);
        Epic epic = new Epic("Epic1", "Description3", Status.NEW);


        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);

        taskManager.save();

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


        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);

        taskManager.save();


        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

        for (Task task : loadedManager.getAllTasks()) {
            assertTrue(taskManager.getAllTasks().contains(task));
        }
    }
}