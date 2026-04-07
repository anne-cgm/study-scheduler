package studyscheduler.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldCreateTaskWithTitleAndDueDate() {
        LocalDate date = LocalDate.of(2026, 4, 10);
        Task task = new Task("Estudar Java", date);

        assertEquals("Estudar Java", task.getTitle());
        assertEquals(date, task.getDueDate());
        assertFalse(task.isCompleted());
    }

    @Test
    void shouldMarkTaskAsCompleted() {
        Task task = new Task("Projeto", LocalDate.now());

        task.setCompleted(true);

        assertTrue(task.isCompleted());
    }

    @Test
    void shouldReturnTrueWhenTaskIsOverdue() {
        Task task = new Task("Trabalho atrasado", LocalDate.now().minusDays(1));

        assertTrue(task.isOverdue());
    }

    @Test
    void shouldReturnFalseWhenTaskIsCompletedEvenIfPastDue() {
        Task task = new Task("Entrega", LocalDate.now().minusDays(1));
        task.setCompleted(true);

        assertFalse(task.isOverdue());
    }

    @Test
    void shouldReturnFalseWhenDueDateIsNull() {
        Task task = new Task("Sem data", null);

        assertFalse(task.isOverdue());
    }
}