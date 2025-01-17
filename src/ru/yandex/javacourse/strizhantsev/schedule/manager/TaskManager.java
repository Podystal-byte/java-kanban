package ru.yandex.javacourse.strizhantsev.schedule.manager;

import ru.yandex.javacourse.strizhantsev.schedule.task.Task;
import ru.yandex.javacourse.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacourse.strizhantsev.schedule.task.Epic;
import ru.yandex.javacourse.strizhantsev.schedule.task.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subtasks;
    private int generateId;

    public TaskManager() {
        this.generateId = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    public int addTask(Task task) {
        int id = ++generateId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public int addEpic(Epic epic) {
        int epicId = ++generateId;
        epic.setId(epicId);
        epics.put(epicId, epic);
        return epicId;
    }

    public Integer addNewSubtask(SubTask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }

        int id = ++generateId;
        subtask.setId(id);
        subtasks.put(id, subtask);
        epic.addSubtaskId(id);
        updateEpicStatus(epic);
        return id;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public Task findTaskById(int id) {
        return tasks.get(id);
    }

    public Epic findEpicById(int id) {
        return epics.get(id);
    }

    public SubTask findSubTaskById(int id) {
        return subtasks.get(id);
    }

    public void updateTask(Task task) {
        int id = task.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        }
    }

    public void updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;

        }


        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());


    }

    public void updateSubtask(SubTask subtask) {
        int id = subtask.getId();
        int epicId = subtask.getEpicId();
        SubTask savedSubtask = subtasks.get(id);

        if (savedSubtask == null) {
            return;
        }

        final Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        subtasks.put(id, subtask);
        updateEpicStatus(epic);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public void deleteSubtask(int id) {
        SubTask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(epic);
            }
        }
    }

    public ArrayList<SubTask> allSubtasksForEpic(Epic epic) {
        int epicId = epic.getId();
        ArrayList<SubTask> result = new ArrayList<>();

        for (SubTask subTask : subtasks.values()) {
            if (subTask.getEpicId() == epicId) {
                result.add(subTask);
            }
        }

        return result;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic);
        }

        subtasks.clear(); // Очистка всех подзадач
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); // Очистка всех подзадач при удалении эпиков
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> subtasksForEpic = allSubtasksForEpic(epic);

        if (subtasksForEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean done = true;
        boolean inProgress = false;

        for (SubTask subTask : subtasksForEpic) {
            if (subTask.getStatus() != Status.DONE) done = false;
            if (subTask.getStatus() == Status.IN_PROGRESS) inProgress = true;
        }

        if (done) {
            epic.setStatus(Status.DONE);
        } else if (inProgress) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.NEW);
        }
    }
}