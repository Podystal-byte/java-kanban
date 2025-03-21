package ru.yandex.javacource.strizhantsev.schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.javacource.strizhantsev.schedule.manager.FileBackedTaskManager;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {

    @TempDir
    Path tempDir;

    @Test
    public void testFileBackedTaskManagerException() {
        File test = tempDir.resolve("test.txt").toFile();

        assertThrows(RuntimeException.class, () -> {
            FileBackedTaskManager.loadFromFile(test);
        }, "Загрузка из несуществующего файла должна вызывать исключение");
    }
}