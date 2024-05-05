package managers;

import exceptions.CheckOverTimeException;
import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import managers.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest<T extends TaskManager> {

    T manager;

    @BeforeEach
    public void setUp() {
        manager = (T) new InMemoryTaskManager();
    }

    @Test
    public void checkOverTimeInTask() {
        Task taskOne = new Task("Таск1", "Описание Task1", TaskType.TASK, 30, LocalDateTime.now());
        Task taskTwo = new Task("Таск2", "Описание Task2", TaskType.TASK, 30, LocalDateTime.now().plusMinutes(10));
        manager.addNewTask(taskOne);
        assertThrows(CheckOverTimeException.class, () -> {
            manager.addNewTask(taskTwo);
        }, "Не произошло пересечение интервалов при добавлении задачи");
        assertEquals(1, manager.getAllTasks().size(), "Тасок больше чем 1");
    }

    @Test
    public void getAllTask() {
        Task taskOne = new Task("Таск1", "Описание Task1", TaskType.TASK, 30, LocalDateTime.now());
        Task taskTwo = new Task("Таск2", "Описание Task2", TaskType.TASK, 30, LocalDateTime.now().plusMinutes(40));
        manager.addNewTask(taskOne);
        manager.addNewTask(taskTwo);
        assertEquals(2, manager.getAllTasks().size(), "Размер не равен 2");
    }

    @Test
    public void getALlEpics() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        Epic epicTwo = new Epic("epic2", "Эпик 2 ", TaskType.EPIC);
        Epic epicThree = new Epic("epic3", "Эпик 3 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        manager.addNewEpic(epicTwo);
        manager.addNewEpic(epicThree);
        assertEquals(3, manager.getALlEpics().size(), "Размер не равен 3");
    }

    @Test
    public void getAllSubTasks() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now());
        Subtask subtaskTwo = new Subtask("SUB2", "OPIS2", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now().plusMinutes(20));
        Subtask subtaskThree = new Subtask("SUB3", "OPIS3", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now().plusMinutes(40));
        manager.addNewSubtask(subtaskOne);
        manager.addNewSubtask(subtaskTwo);
        manager.addNewSubtask(subtaskThree);
        assertEquals(3, manager.getAllSubTasks().size(), "Размер не равен 3");
    }

    @Test
    public void deleteEpic() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now());
        manager.addNewSubtask(subtaskOne);
        manager.deleteEpic(1);
        assertNull(manager.getEpicById(1), "Эпик не был удален");
        assertNull(manager.getSubtaskById(2), "Сабтаск не был удален");
        assertEquals(0, manager.getALlEpics().size(), "Размер не равен 0");
        assertEquals(0, manager.getAllSubTasks().size(), "Размер не равен 0");
    }

    @Test
    public void deleteAllEpics() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        Epic epicTwo = new Epic("epic2", "Эпик 2 ", TaskType.EPIC);
        Epic epicThree = new Epic("epic3", "Эпик 3 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        manager.addNewEpic(epicTwo);
        manager.addNewEpic(epicThree);
        manager.deleteAllEpics();
        assertEquals(0, manager.getALlEpics().size(), "Размер не равен 0");
        assertEquals(0, manager.getAllSubTasks().size(), "Размер не равен 0");
        assertNull(manager.getEpicById(1), "Эпик не был удален");
        assertNull(manager.getEpicById(2), "Эпик не был удален");
        assertNull(manager.getEpicById(3), "Эпик не был удален");
    }

    @Test
    public void updateTask() {
        Task task = new Task("Task", "task_description", TaskType.TASK, 20, LocalDateTime.now());
        manager.addNewTask(task);
        Task newTask = new Task(task.getId(), "Task_upd", "task_description_upd", Status.IN_Progress, TaskType.TASK, 60, LocalDateTime.now().plusMinutes(30));
        manager.updateTask(newTask);
        assertEquals(task.getTittle(), newTask.getTittle(), "Титл не обновился");
        assertEquals(task.getDescription(), newTask.getDescription(), "Описание не обновилось");
        assertEquals(task.getStatus(), newTask.getStatus(), "Статус не обновился");
    }

    @Test
    public void printAllEpicsWithSubtasksTest() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", 1, TaskType.SUBTASK, 10, LocalDateTime.now());
        manager.addNewSubtask(subtaskOne);
        int subtaskId = subtaskOne.getId();
        assertNotNull(manager.getEpicById(1), "Эпик не был найден.");
        assertNotNull(manager.getSubtaskById(subtaskId), "Подзадача не была найдена.");
    }

    @Test
    public void updateSubtask() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtask = new Subtask("SUB1", "OPIS1", 1, TaskType.SUBTASK, 10, LocalDateTime.now());
        manager.addNewSubtask(subtask);
        Subtask newSubtask = new Subtask(subtask.getId(), "SUB_upd", "OPIS_upd", Status.IN_Progress, TaskType.SUBTASK, 1, 60, LocalDateTime.now().plusMinutes(60));
        manager.updateSubtask(newSubtask);
        assertEquals(subtask.getTittle(), newSubtask.getTittle(), "Титл не обновился");
        assertEquals(subtask.getDescription(), newSubtask.getDescription(), "Описание не обновилось");
        assertEquals(subtask.getStatus(), newSubtask.getStatus(), "Статус не обновился");
    }

    @Test
    public void addNewSubtask() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now());
        manager.addNewSubtask(subtaskOne);
        assertEquals(subtaskOne, manager.getSubtaskById(2), "Сабтаска не добавлена");
    }

    @Test
    public void addNewEpic() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        assertNotNull(manager.getEpicById(1), "Эпик не был найден.");
    }

    @Test
    public void deleteSubtaskById() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now());
        manager.addNewSubtask(subtaskOne);
        manager.deleteSubtaskById(2);
        assertNull(manager.getSubtaskById(2), "Сабтаск не был удален");
        assertEquals(0, manager.getAllSubTasks().size(), "Размер не равен 0");
    }

    @Test
    public void deleteTask() {
        Task taskOne = new Task("Таск1", "Описание Task1", TaskType.TASK, 30, LocalDateTime.now());
        manager.addNewTask(taskOne);
        manager.deleteTask(1);
        assertEquals(0, manager.getAllTasks().size(), "Такс не был удален");
    }

    @Test
    public void getEpicById() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        assertEquals(1, epicOne.getId(), "ID не верное");
    }

    @Test
    public void getSubtaskById() {
        Epic epicOne = new Epic("epic", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 10, LocalDateTime.now());
        manager.addNewSubtask(subtaskOne);
        assertEquals(2, subtaskOne.getId(), "ID не верное");
    }

    @Test
    public void getTaskById() {
        Task task = new Task("Task", "task_description", TaskType.TASK, 60, LocalDateTime.now());
        manager.addNewTask(task);
        assertEquals(1, task.getId(), "ID не верное");
    }

    @Test
    public void addNewTask() {
        Task taskOne = new Task("Таск1", "Описание Task1", TaskType.TASK, 30, LocalDateTime.now());
        manager.addNewTask(taskOne);
        assertEquals(1, taskOne.getId(), "Task не добавлен");
    }
}
