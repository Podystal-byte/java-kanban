public class Main {

    public static void main(String[] args) {
        Task task = new Task("Отправиться на шоппинг", "Нужно купить платья, сумку и туфли", Status.NEW);
        Task task1 = new Task("Заказать доставку продуктов", "Нужно купить шоколад, чипсы, кола", Status.IN_PROGRESS);
        Epic epic = new Epic("Отправиться в путешествие", "Отпуск, отдохнуть, набраться сил", Status.DONE);
        SubTask subTask = new SubTask("Поехать в аэропорт", "Купить билеты", Status.DONE);
        SubTask subTask1 = new SubTask("Собрать вещи заранее", "Чтобы не спешить при отъезде", Status.DONE);
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addTask(epic);
        taskManager.addTask(subTask);
        taskManager.addTask(subTask1);
        taskManager.printAllTasks();
        taskManager.removeForId(1);
        taskManager.removeForId(3);
        taskManager.printAllTasks();
    }
}
