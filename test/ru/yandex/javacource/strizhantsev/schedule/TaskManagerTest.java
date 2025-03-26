package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.strizhantsev.schedule.manager.IntersectionException;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;
import ru.yandex.javacource.strizhantsev.schedule.task.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    protected abstract T createTaskManager();

    @BeforeEach
    public void setUp() {
        taskManager = createTaskManager();
    }

    @Test
    public void testAddTask() throws IOException, IntersectionException {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        taskManager.addTask(task);
        assertNotNull(taskManager.findTaskById(task.getId()), "Задача не была добавлена.");
    }

    @Test
    public void testAddEpic() throws IOException, IntersectionException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        int epicId = taskManager.addEpic(epic);
        assertNotNull(taskManager.findEpicById(epicId), "Эпик не был добавлен.");
    }

    @Test
    public void testAddSubTask() throws IOException, IntersectionException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask("SubTask 1", "Description 1", Status.NEW);
        subTask.setEpicId(epic.getId());
        taskManager.addNewSubtask(subTask);
        assertNotNull(taskManager.findSubTaskById(subTask.getId()), "Подзадача не была добавлена.");
    }

    @Test
    public void testUpdateTaskStatus() throws IOException, IntersectionException {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);
        task.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task);
        assertEquals(Status.IN_PROGRESS, taskManager.findTaskById(taskId).getStatus(), "Статус задачи не обновлен.");
    }

    @Test
    public void testDeleteTask() throws IOException, IntersectionException {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);
        taskManager.removeTaskById(taskId);
        assertNull(taskManager.findTaskById(taskId), "Задача не была удалена.");
    }

    @Test
    public void testDeleteEpic() throws IOException, IntersectionException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        int epicId = taskManager.addEpic(epic);
        taskManager.deleteEpic(epicId);
        assertNull(taskManager.findEpicById(epicId), "Эпик не был удален.");
    }

    @Test
    public void testDeleteSubTask() throws IOException, IntersectionException {
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW);
        SubTask subTask = new SubTask("SubTask 1", "Description 1", Status.NEW);
        subTask.setEpicId(epic.getId());
        taskManager.addEpic(epic);
        taskManager.addNewSubtask(subTask);
        taskManager.deleteSubtask(subTask.getId());
        assertNull(taskManager.findSubTaskById(subTask.getId()), "Подзадача не была удалена.");
    }

    @Test
    public void testGetHistory() throws IOException, IntersectionException {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);
        taskManager.findTaskById(taskId);
        List<Task> history = taskManager.getHistory();
        assertEquals(1, history.size(), "История задач неверна.");
    }

    @Test
    public void testGetPrioritizedTasks() throws IOException, IntersectionException {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        assertEquals(2, prioritizedTasks.size(), "Приоритетные задачи неверны.");
    }

    @Test
    public void testValidationTask() throws IOException, IntersectionException {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, LocalDateTime.now().plusMinutes(15), Duration.ofMinutes(30));
        taskManager.addTask(task1);
        assertTrue(taskManager.addTask(task2) > 0, "Задачи пересекаются по времени.");
    }
}