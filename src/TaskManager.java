import java.util.HashMap;


public class TaskManager {
    public HashMap<Integer, Task> tasks;

    TaskManager() {
        this.tasks = new HashMap<Integer, Task>();
    }

    public void addTask(Task task) {
        tasks.put(task.id, task);
    }

    public void printAllTasks() {
        if (!tasks.isEmpty()) {
            for (Integer id : tasks.keySet()) {
                Task task = tasks.get(id);
                System.out.println(task.id);
                System.out.println(task.name);
            }
        } else {
            System.out.println("Задачник пуст");

        }
    }

    public void removeAllTasks() {
        int i = 0;
        Task task = new Task("", "");
        tasks.clear();
        if (tasks.isEmpty()) {
            task.removeTask();
        }

    }

    public void removeForId(int id) {
        tasks.remove(id);
    }
    public void updateTask(Task task){
        if (tasks.containsKey(task.id)){
            tasks.put(task.id, task);
        } else {
            System.out.println("Задача для обновления не найдена");
        }
    }

}
