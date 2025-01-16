public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = new Task("Отправиться на шоппинг", "Нужно купить платья, сумку и туфли", Status.NEW);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.NEW);

        Epic epic = new Epic("Купить продукты", "Пойти в магазин", Status.NEW);
        Epic epic1 = new Epic("Построить дом", "Описание эпик 2", Status.NEW);

        SubTask subTask = new SubTask("Гречка", "купить гречку", Status.IN_PROGRESS);
        SubTask subTask2 = new SubTask("Лук", "купить лук", Status.NEW);
        SubTask subTask3 = new SubTask("Заложить фундамент", "Описание подзадачи 2", Status.NEW);

        taskManager.addTask(task);
        taskManager.addTask(task2);

        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);

        taskManager.addSubtask(subTask, epic);
        taskManager.addSubtask(subTask2, epic);
        taskManager.addSubtask(subTask3, epic1);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());

        System.out.println();
        System.out.println();

        taskManager.updateEpicStatus(epic);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());

        System.out.println();
        System.out.println();

        taskManager.removeTaskForID(2);
        taskManager.removeEpicForID(4);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());

    }
}