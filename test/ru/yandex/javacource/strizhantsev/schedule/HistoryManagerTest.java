package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.strizhantsev.schedule.history.InMemoryHistoryManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {

    private InMemoryHistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();

        task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);

        task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);
        task2.setId(2);

        task3 = new Task("Task 3", "Description 3", Status.DONE);
        task3.setId(3);
    }

    @Test
    public void testEmptyHistory() {
        List<Task> history = historyManager.getHistory();

        assertTrue(history.isEmpty(), "История должна быть пустой.");
    }

    @Test
    public void testAddDuplicateTaskToHistory() {
        historyManager.add(task1);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size(), "История должна содержать 1 задачу.");
        assertEquals(task1, history.get(0), "Задача 1 должна быть в истории.");
    }

    @Test
    public void testRemoveFirstTaskFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 задачи после удаления.");
        assertFalse(history.contains(task1), "Задача 1 не должна быть в истории.");
        assertEquals(task2, history.get(0), "Первая задача в истории должна быть Task 2.");
        assertEquals(task3, history.get(1), "Вторая задача в истории должна быть Task 3.");
    }

    @Test
    public void testRemoveMiddleTaskFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 задачи после удаления.");
        assertFalse(history.contains(task2), "Задача 2 не должна быть в истории.");
        assertEquals(task1, history.get(0), "Первая задача в истории должна быть Task 1.");
        assertEquals(task3, history.get(1), "Вторая задача в истории должна быть Task 3.");
    }

    @Test
    public void testRemoveLastTaskFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 задачи после удаления.");
        assertFalse(history.contains(task3), "Задача 3 не должна быть в истории.");
        assertEquals(task1, history.get(0), "Первая задача в истории должна быть Task 1.");
        assertEquals(task2, history.get(1), "Вторая задача в истории должна быть Task 2.");
    }
}