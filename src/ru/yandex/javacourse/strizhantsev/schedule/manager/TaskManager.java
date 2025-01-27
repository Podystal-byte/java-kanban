package ru.yandex.javacourse.strizhantsev.schedule.manager;

import ru.yandex.javacourse.strizhantsev.schedule.task.Task;
import ru.yandex.javacourse.strizhantsev.schedule.task.Epic;
import ru.yandex.javacourse.strizhantsev.schedule.task.SubTask;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int addTask(Task task);

    int addEpic(Epic epic);

    Integer addNewSubtask(SubTask subtask);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTasks();

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

    ArrayList<SubTask> allSubtasksForEpic(Epic epic);

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();
}
