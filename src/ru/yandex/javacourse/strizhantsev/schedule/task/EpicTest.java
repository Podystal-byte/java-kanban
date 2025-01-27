package ru.yandex.javacourse.strizhantsev.schedule.task;

import org.junit.jupiter.api.BeforeEach; // Изменено на BeforeEach
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    private Epic epic;

    @BeforeEach
    public void setUp() {
        this.epic = new Epic("Epic Title", "Epic Description", Status.NEW);
        epic.setId(1); // Устанавливаем ID для тестирования
    }

    @Test
    public void testEpic() {
        assertEquals("Epic Title", epic.getName());
        assertEquals("Epic Description", epic.getDescription());
        assertEquals(Status.NEW, epic.getStatus());
    }
}