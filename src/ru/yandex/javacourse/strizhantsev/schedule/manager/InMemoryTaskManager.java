package ru.yandex.javacourse.strizhantsev.schedule.manager;

import ru.yandex.javacourse.strizhantsev.schedule.history.HistoryManager;
import ru.yandex.javacourse.strizhantsev.schedule.history.InMemoryHistoryManager;
import ru.yandex.javacourse.strizhantsev.schedule.task.Epic;
import ru.yandex.javacourse.strizhantsev.schedule.task.Status;
import ru.yandex.javacourse.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacourse.strizhantsev.schedule.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subtasks;
    private int generateId;
    private HistoryManager taskHistoryList;

    public InMemoryTaskManager() {
        this.generateId = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.taskHistoryList = new InMemoryHistoryManager();
    }

    @Override
    public int addTask(Task task) {
        int id = ++generateId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int epicId = ++generateId;
        epic.setId(epicId);
        epics.put(epicId, epic);
        return epicId;
    }

    @Override
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

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task findTaskById(int id) {
        Task task = tasks.get(id);
        taskHistoryList.add(task);

        return task;
    }

    @Override
    public Epic findEpicById(int id) {
        Epic epic = epics.get(id);

        taskHistoryList.add(epic);

        return epic;
    }

    @Override
    public SubTask findSubTaskById(int id) {
        SubTask subtask = subtasks.get(id);

        taskHistoryList.add(subtask);

        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;

        }


        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());


    }

    @Override
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

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    @Override
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

    @Override
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

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic);
        }

        subtasks.clear(); // Очистка всех подзадач
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); // Очистка всех подзадач при удалении эпиков
    }

    @Override
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

    @Override
    public List<Task> getHistory() {
        return taskHistoryList.getHistory();
    }



}

