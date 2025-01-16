import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subtasks;
    private int generateId;

    public TaskManager() {
        generateId = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }


    public int getGenerateId() {
        return generateId;
    }

    public void setGenerateId(int generateId) {
        this.generateId = generateId;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    public HashMap<Integer, SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(HashMap<Integer, SubTask> subtasks) {
        this.subtasks = subtasks;
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

    public int addSubtask(SubTask subTask, Epic epic) {
        if (epic == null || subTask == null) {
            return 0;
        } else {
            int subTaskId = ++generateId;
            subTask.setEpicId(epic.getId());
            subTask.setId(subTaskId);
            subtasks.put(subTaskId, subTask);
            return subTask.getId();
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public Task foundId(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }

    public Epic foundEpicID(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    public SubTask foundSubTaskId(int id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        return null;
    }

    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    public void updateEpic(Epic epic) {
        int id = epic.getId();
        Epic savedTask = epics.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, epic);
    }

    public void updateSubTask(SubTask subTask) {
        int id = subTask.getId();
        SubTask savedTask = subtasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, subTask);
    }

    public void removeTaskForID(int id) {
        tasks.remove(id);
    }

    public void removeEpicForID(int id) {
        epics.remove(id);
    }

    public void removeSubTaskforID(int id) {
        subtasks.remove(id);
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

    public void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> subtasksForEpic = allSubtasksForEpic(epic);

        if (subtasksForEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean done = true;
        boolean inProgress = false;

        for (SubTask subTask : subtasksForEpic) {
            if (subTask.getStatus() != Status.DONE) {
                done = false;
            }
            if (subTask.getStatus() == Status.IN_PROGRESS) {
                inProgress = true;
            }
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
