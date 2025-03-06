
package ru.yandex.javacource.strizhantsev.schedule.manager;

import ru.yandex.javacource.strizhantsev.schedule.task.Epic;
import ru.yandex.javacource.strizhantsev.schedule.task.Status;
import ru.yandex.javacource.strizhantsev.schedule.task.SubTask;
import ru.yandex.javacource.strizhantsev.schedule.task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileBackedTaskManager extends InMemoryTaskManager {
    public static final String FILE_NAME = "tasks.txt";
    public static String FILE_PATH = "./resources/" + FILE_NAME;

    @Override
    public int addTask(Task task) throws IOException {
        super.addTask(task);
        save();
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) throws IOException {
        super.addEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public Integer addNewSubtask(SubTask subtask) throws IOException {
        super.addNewSubtask(subtask);
        save();
        return subtask.getId();
    }

    public void save() throws IOException {
        Path filePath = Paths.get(FILE_PATH);
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Файл создан.");
            }

            StringBuilder content = new StringBuilder("id,type,name,description,status,epicId\n"); // Заголовок
            for (Task task : getAllTasks()) {
                content.append(task.toString()).append("\n");
            }
            for (Epic epic : getAllEpics()) {
                content.append(epic.toString()).append("\n");
            }
            for (SubTask subtask : getAllSubTasks()) {
                content.append(subtask.toString()).append("\n");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write(content.toString());
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Проблема с сохранением данных");
        }
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("Некорректная строка: " + line);
                    continue;
                }

                int id = Integer.parseInt(parts[0]);

                String type = parts[1];

                if (type.equals("TASK") && parts.length == 5) {
                    Task task = new Task(parts[2], parts[3], Status.valueOf(parts[4]));
                    task.setId(id);
                    manager.addTask(task);
                } else if (type.equals("EPIC") && parts.length == 5) {
                    Epic epic = new Epic(parts[2], parts[3], Status.valueOf(parts[4]));
                    epic.setId(id);
                    manager.addEpic(epic);
                } else if (type.equals("SUBTASK") && parts.length == 6) {
                    SubTask subtask = new SubTask(parts[2], parts[3], Status.valueOf(parts[4]));
                    subtask.setId(id);
                    try {
                        int epicId = Integer.parseInt(parts[5]);
                        subtask.setEpicId(epicId);
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректный epicId: " + line);
                        continue;
                    }
                    manager.addNewSubtask(subtask);
                } else {
                    System.out.println("Неизвестный тип: " + type);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла");
        }
        return manager;
    }
}

