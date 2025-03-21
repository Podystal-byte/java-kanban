package ru.yandex.javacource.strizhantsev.schedule;

import ru.yandex.javacource.strizhantsev.schedule.manager.Managers;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) throws Exception {
        TaskManager taskManager = Managers.getFileBacked();

        Task task1 = new Task("Отправиться на шоппинг", "Нужно купить платья, сумку и туфли", Status.NEW, LocalDateTime.of(2025, 11, 11, 12, 20), Duration.ofMinutes(10));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.NEW, LocalDateTime.of(2023, 11, 11, 12, 20), Duration.ofMinutes(10));
        Task task216 = new Task("n", "d", Status.NEW, LocalDateTime.of(2025, 7, 11, 12, 20), Duration.ofMinutes(10));
        Task task3 = new Task("rrr", "dsdsd", Status.NEW);

        Epic epic1 = new Epic("Купить продукты", "Пойти в магазин", Status.NEW);

        SubTask subTask3 = new SubTask("Заложить фундамент", "Описание подзадачи 2", Status.IN_PROGRESS, LocalDateTime.of(2026, 1, 13, 0, 5), Duration.ofMinutes(25));
        SubTask subTask4 = new SubTask("r  ", "1    ", Status.NEW, LocalDateTime.of(2025, 1, 2, 3, 4), Duration.ofMinutes(20));

        taskManager.addTask(task216);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.addEpic(epic1);

        subTask3.setEpicId(epic1.getId());
        subTask4.setEpicId(epic1.getId());

        taskManager.addNewSubtask(subTask3);
        taskManager.addNewSubtask(subTask4);


        System.out.println("-------------------------------------------------------");
        System.out.println(taskManager.getPrioritizedTasks());


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