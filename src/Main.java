public class Main {

    public static void main(String[] args) {
        Task task = new Task("Готовка", "Выбор ингредиентов и тд");

        Task task1 = new Task("Прогулка", "выйти на улицу");

        SubTask subTask = new SubTask("F", "t");
        SubTask subTask2 = new SubTask("b", "h");
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);
        taskManager.addTask(task1);

        taskManager.removeAllTasks();
        taskManager.printAllTasks();
        Epic epic = new Epic("Переезд", "Этапы подготовки к переезду");
        epic.addSubTask(subTask);
        epic.addSubTask(subTask2);
        taskManager.addTask(task);
        taskManager.addTask(epic);
        taskManager.addTask(subTask);
        taskManager.printAllTasks();

        Task task2 = new Task("aaa", "bbbb");

    }
}
