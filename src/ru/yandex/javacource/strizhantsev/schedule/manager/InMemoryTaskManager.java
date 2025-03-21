package ru.yandex.javacource.strizhantsev.schedule.manager;

import ru.yandex.javacource.strizhantsev.schedule.history.HistoryManager;
import ru.yandex.javacource.strizhantsev.schedule.history.InMemoryHistoryManager;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, SubTask> subtasks;
    private int generateId;
    private final HistoryManager taskHistoryList;
    private TreeSet<Task> sortedSet;
    Comparator<Task> comparator = (t1, t2) -> {
        int sTime = t1.getStartTime().compareTo(t2.getStartTime());
        if (sTime == 0) {
            return Integer.compare(t1.getId(), t2.getId());
        }
        return sTime;
    };

    public InMemoryTaskManager() {
        this.sortedSet = new TreeSet<>(comparator);
        this.generateId = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.taskHistoryList = new InMemoryHistoryManager();
    }

    @Override
    public int addTask(Task task) throws IOException {
        int id = ++generateId;
        task.setId(id);
        if (validationTask(task)) {
            sortedSet.add(task);
        }
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addEpic(Epic epic) throws IOException {
        int epicId = ++generateId;
        epic.setId(epicId);
        epics.put(epicId, epic);
        if (validationTask(epic)) {
            sortedSet.add(epic);
        }
        return epicId;
    }

    @Override
    public Integer addNewSubtask(SubTask subtask) throws IOException {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }

        int id = ++generateId;
        subtask.setId(id);
        if (validationTask(subtask)) {
            setEpicStartTime(epic);
            setEpicDuration(epic);
            sortedSet.add(subtask);
        }
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
        if (task != null) {
            taskHistoryList.add(task);
        }

        return task;
    }

    @Override
    public Epic findEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            taskHistoryList.add(epic);
        }
        return epic;
    }

    @Override
    public SubTask findSubTaskById(int id) {
        SubTask subtask = subtasks.get(id);

        if (subtask != null) {
            taskHistoryList.add(subtask);
        }

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
        final Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        epic.setSubtaskIds(savedEpic.getSubtaskIds());
        epic.setStatus(savedEpic.getStatus());
        setEpicDuration(savedEpic);
        setEpicStartTime(savedEpic);
        if (validationTask(epic)) {
            sortedSet.add(epic);
        }
        epics.put(epic.getId(), epic);
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
        setEpicStartTime(epic);
        setEpicDuration(epic);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        taskHistoryList.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        taskHistoryList.remove(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    @Override
    public void deleteSubtask(int id) {
        SubTask subtask = subtasks.remove(id);
        taskHistoryList.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(epic);
                setEpicDuration(epic);
                setEpicStartTime(epic);
            }
        }
    }

    @Override
    public ArrayList<SubTask> allSubtasksForEpic(Epic epic) {
        int epicId = epic.getId();
        return subtasks.values().stream()
                .filter(subTask -> subTask.getEpicId() == epicId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        epics.values().stream()
                .peek(epic -> {
                    epic.cleanSubtaskIds();
                    updateEpicStatus(epic);
                    setEpicStartTime(epic);
                    setEpicDuration(epic);
                })
                .collect(Collectors.toList());

        subtasks.clear();
    }


    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    private void updateEpicStatus(Epic epic) {
        List<SubTask> subtasksForEpic = allSubtasksForEpic(epic);

        if (subtasksForEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean done = subtasksForEpic.stream()
                .allMatch(subTask -> subTask.getStatus() == Status.DONE);
        boolean inProgress = subtasksForEpic.stream()
                .anyMatch(subTask -> subTask.getStatus() == Status.NEW);
        boolean inProgressSubtaskInProgress = subtasksForEpic.stream()
                .anyMatch(subTask -> subTask.getStatus() == Status.IN_PROGRESS);

        if (done) {
            epic.setStatus(Status.DONE);
        } else if (inProgress || inProgressSubtaskInProgress) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.NEW);
        }
    }

    private void updateEpicTime(Epic epic) {
        List<SubTask> subtasks = allSubtasksForEpic(epic);
        epic.updateTime(subtasks);
    }

    private void setEpicStartTime(Epic epic) {
        updateEpicTime(epic);
    }

    private void setEpicDuration(Epic epic) {
        updateEpicTime(epic);
    }

    public ArrayList<Task> getPrioritizedTasks() {
        List<Task> allTask = getAllTasks().stream()
                .filter(task -> !(task.getStartTime() == null))
                .toList();
        List<SubTask> allSubTask = getAllSubTasks().stream()
                .filter(subTask -> !(subTask.getStartTime() == null))
                .toList();
        List<Epic> allEpic = getAllEpics().stream()
                .filter(epic -> !(epic.getStartTime() == null))
                .toList();
        sortedSet.addAll(allTask);
        sortedSet.addAll(allSubTask);
        sortedSet.addAll(allEpic);
        return new ArrayList<>(sortedSet);
    }

    private boolean isOverlapping(Task task1, Task task2) {
        if (task1.getStartTime() == null || task2.getStartTime() == null) {
            return false;
        }

        LocalDateTime start1 = task1.getStartTime();
        LocalDateTime end1 = task1.getEndTime();
        LocalDateTime start2 = task2.getStartTime();
        LocalDateTime end2 = task2.getEndTime();

        if (end1 == null || end2 == null) {
            return false;
        }

        return !(end1.isBefore(start2) || end2.isBefore(start1));
    }

    private boolean validationTask(Task newTask) {
        if (newTask.getStartTime() == null || newTask.getDuration() == null) {
            return false;
        }
        return sortedSet.stream()
                .allMatch(task -> isOverlapping(task, newTask));
    }


    @Override
    public List<Task> getHistory() {
        return taskHistoryList.getHistory();
    }
}

