package ru.yandex.javacource.strizhantsev.schedule;

import ru.yandex.javacource.strizhantsev.schedule.manager.Managers;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();


        Task task1 = new Task("Отправиться на шоппинг", "Нужно купить платья, сумку и туфли", Status.NEW);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.NEW);


        Epic epic1 = new Epic("Купить продукты", "Пойти в магазин", Status.NEW);
        Epic epic2 = new Epic("Построить дом", "Описание эпик 2", Status.NEW);

        SubTask subTask1 = new SubTask("Гречка", "купить гречку", Status.NEW);
        SubTask subTask2 = new SubTask("Лук", "купить лук", Status.IN_PROGRESS);
        SubTask subTask3 = new SubTask("Заложить фундамент", "Описание подзадачи 2", Status.IN_PROGRESS);


        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);


        subTask1.setEpicId(epic1.getId());
        subTask2.setEpicId(epic1.getId());
        subTask3.setEpicId(epic2.getId());

        taskManager.addNewSubtask(subTask1);
        taskManager.addNewSubtask(subTask2);
        taskManager.addNewSubtask(subTask3);

        taskManager.findTaskById(1);
        taskManager.findTaskById(2);
        taskManager.findSubTaskById(6);
        taskManager.findSubTaskById(6);


        printAllTasks(taskManager);
        // Наконец-то разобрался как сделать правильную директорию

//        System.out.println(taskManager.getAllTasks());
//        System.out.println(taskManager.getAllEpics());
//        System.out.println(taskManager.getAllSubTasks());
//
//        System.out.println();
//        System.out.println();
//
//        taskManager.updateEpicStatus(epic1);
//        System.out.println(taskManager.getAllEpics());
//
//        System.out.println();
//        System.out.println();
//
//        taskManager.deleteEpic(epic1.getId());
//        taskManager.removeTaskById(task1.getId());
//
//
//        System.out.println(taskManager.getAllTasks());
//        System.out.println(taskManager.getAllEpics());
//        System.out.println(taskManager.getAllSubTasks());
//

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            // Получаем все подзадачи для данного эпика
            for (SubTask subtask : manager.allSubtasksForEpic(epic)) {
                System.out.println("--> " + subtask);
            }
        }

        System.out.println("Подзадачи:");
        for (SubTask subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}