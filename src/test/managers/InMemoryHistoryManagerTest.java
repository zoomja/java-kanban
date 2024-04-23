package test.managers;

import interfaces.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    private HistoryManager manager;

    @BeforeEach
    public void setUp() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    public void addTasksToHistory() {
        Task task1 = new Task(1, "Task", "task_description", Status.NEW, TaskType.TASK, 60, LocalDateTime.now());
        Task task2 = new Task(2, "Task2", "task_description2", Status.NEW, TaskType.TASK, 60, LocalDateTime.now().plusDays(1));
        Task task3 = new Task(3, "Task3", "task_description3", Status.NEW, TaskType.TASK, 60, LocalDateTime.now().plusDays(2));
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        assertEquals(List.of(task1, task2, task3), manager.getHistory(), "Не все задачи добавились в историю");
    }

    @Test
    public void removeFromHistory() {
        Task task1 = new Task(1, "Task", "task_description", Status.NEW, TaskType.TASK, 60, LocalDateTime.now());
        Task task2 = new Task(2, "Task2", "task_description2", Status.NEW, TaskType.TASK, 60, LocalDateTime.now().plusDays(1));
        Task task3 = new Task(3, "Task3", "task_description3", Status.NEW, TaskType.TASK, 60, LocalDateTime.now().plusDays(2));
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.remove(task2.getId());
        assertEquals(List.of(task1, task3), manager.getHistory(), "Не все задачи добавились в историю");
    }

    @Test
    public void emptyTaskHistory() {
        Task task1 = new Task(1, "Task", "task_description", Status.NEW, TaskType.TASK, 60, LocalDateTime.now());
        manager.addTask(task1);
        manager.remove(task1.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void addDuplicateToHistory() {
        Task task1 = new Task(1, "Task", "task_description", Status.NEW, TaskType.TASK, 60, LocalDateTime.now());
        manager.addTask(task1);
        manager.addTask(task1);
        assertEquals(List.of(task1), manager.getHistory(), "задача задублировалась");
    }
}
