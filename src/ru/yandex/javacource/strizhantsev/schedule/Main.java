package ru.yandex.javacource.strizhantsev.schedule;

import ru.yandex.javacource.strizhantsev.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacource.strizhantsev.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.strizhantsev.schedule.manager.Managers;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.manager.TaskManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) throws Exception {
        //TaskManager taskManager = Managers.getFileBacked();

        TaskManager taskManager = Managers.getFileBacked();




        Task task1 = new Task("Отправиться на шоппинг", "Нужно купить платья, сумку и туфли", Status.NEW, LocalDateTime.of(2025,11, 11, 12, 20));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.NEW, LocalDateTime.of(2023,11, 11, 12, 20));
        Task task217 = new Task("name", "descr", Status.NEW);
        Task task216 = new Task("n", "d", Status.NEW, LocalDateTime.of(2025,7, 11, 12, 20));


        Epic epic1 = new Epic("Купить продукты", "Пойти в магазин", Status.NEW);
        Epic epic2 = new Epic("Построить дом", "Описание эпик 2", Status.NEW);




        SubTask subTask3 = new SubTask("Заложить фундамент", "Описание подзадачи 2", Status.IN_PROGRESS, LocalDateTime.now());
//        SubTask subTask4 = new SubTask("r  ", "1    ", Status.NEW);

        taskManager.addTask(task216);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        taskManager.addTask(task217);

//
        subTask3.setEpicId(epic1.getId());
//        subTask3.setEpicId(epic2.getId());
//        subTask4.setEpicId(epic2.getId());


//        taskManager.addNewSubtask(subTask2);
//        taskManager.addNewSubtask(subTask3);
//        taskManager.addNewSubtask(subTask4);

        System.out.println(subTask3.getEndTime());

        System.out.println("-------------------------------------------------------");

        taskManager.findTaskById(1);
        taskManager.findTaskById(2);
//        taskManager.findSubTaskById(7);
//        taskManager.findSubTaskById(8);




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