package test.ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.ru.yandex.javacourse.strizhantsev.schedule.manager.Managers;
import src.ru.yandex.javacourse.strizhantsev.schedule.manager.TaskManager;
import src.ru.yandex.javacourse.strizhantsev.schedule.task.Epic;
import src.ru.yandex.javacourse.strizhantsev.schedule.task.Status;
import src.ru.yandex.javacourse.strizhantsev.schedule.task.SubTask;
import src.ru.yandex.javacourse.strizhantsev.schedule.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setTaskManager() {
        taskManager = Managers.getDefault();
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

        assertNotEquals(epic.getId(), subTask.getEpicId(), "Эпик не может быть своей подзадачей");
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
    void inMemoryTaskManagerAddsAndFindsTasks() {
        Task task = new Task("Test Task", "Test Description", Status.NEW);
        int id = taskManager.addTask(task);

        assertNotNull(taskManager.findTaskById(id), "Задача должна быть найдена по Id");
    }

    @Test
    void noConflictBetweenTasksWithSameIds() {
        Task task1 = new Task("First Task", "Description A", Status.NEW);
        int id = taskManager.addTask(task1);

        Task task2 = new Task("Second Task", "Description B", Status.NEW);
        task2.setId(id);

        assertNotEquals(task2, taskManager.findTaskById(id), "Задачи с одинаковыми ID не должны конфликтовать");
    }

    @Test
    void taskVariabilityTest() {
        Task originalTask = new Task("Original Task", "Original Description", Status.NEW);
        int id = taskManager.addTask(originalTask);


        originalTask.setName("Changed Name");

        assertEquals(originalTask.getName(), taskManager.findTaskById(id).getName(),
                "Имя задачи в менеджере поменяется на новое");
    }

    @Test
    void historyPreservesPreviousVersionsOfTasks() {
        Task originalTask = new Task("History Test Task", "History Description", Status.NEW);
        int id = taskManager.addTask(originalTask);

        taskManager.findTaskById(id);

        Task updateTask = new Task("Update Task", "Update Description", Status.IN_PROGRESS);
        updateTask.setId(id);
        taskManager.updateTask(updateTask);
        taskManager.findTaskById(id);


        List<Task> history = taskManager.getHistory();

        assertEquals(originalTask.getName(), history.getFirst().getName(),
                "История должна содержать оригинальную версию задачи.");
        assertEquals(updateTask.getName(), taskManager.findTaskById(id).getName(),
                "Имя задачи в менеджере должно быть обновлено на новое.");

    }
} // Спасибо большое за ревью
// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью
// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью
// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью
// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью
// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью// Спасибо большое за ревью