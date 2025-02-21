package ru.yandex.javacource.strizhantsev.schedule.manager;

import ru.yandex.javacource.strizhantsev.schedule.task.Task;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;

import java.util.List;

public interface TaskManager {
    int addTask(Task task);

    int addEpic(Epic epic);

    Integer addNewSubtask(SubTask subtask);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    void removeAllTasks();

    Task findTaskById(int id);

    Epic findEpicById(int id);

    SubTask findSubTaskById(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(SubTask subtask);

    void removeTaskById(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    List<SubTask> allSubtasksForEpic(Epic epic);

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    List<Task> getHistory();
}
