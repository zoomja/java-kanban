package test;

import managers.Csv;
import managers.FileBackedTasksManager;
import managers.TaskManager;
import managers.TaskType;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;

public class Test {

    TaskManager fileManager = new FileBackedTasksManager();

    public void test_Utils() {
        Task taskOne = new Task("Таска 1 ", "Описание 1", TaskType.TASK);
        Task taskTwo = new Task("Таска 2 ", "Описание 2", TaskType.TASK);
        Task taskThree = new Task("Таска 3 ", "Описание 3", TaskType.TASK);
        fileManager.addNewTask(taskOne);
        fileManager.addNewTask(taskTwo);
        fileManager.addNewTask(taskThree);
        Epic epicOne = new Epic("Эпик 1", "описание для эпика 1", TaskType.EPIC);
        fileManager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("подзадача 1", "подзадача для эпика 1", epicOne.getId(), TaskType.SUBTASK);
        Subtask subtaskTwo = new Subtask("подзадача 2", "подзадача для эпика 1", epicOne.getId(), TaskType.SUBTASK);
        fileManager.addNewSubtask(subtaskOne);
        fileManager.addNewSubtask(subtaskTwo);
        fileManager.getTaskById(taskTwo.getId());
        fileManager.getTaskById(taskThree.getId());
        fileManager.getEpicById(epicOne.getId());
        fileManager.getSubtaskById(subtaskOne.getId());
        Task taskFour = new Task("задача 4", "описание задачи 4", TaskType.TASK);
        fileManager.addNewTask(taskFour);
        fileManager.getTaskById(taskFour.getId());
    }

    public void test_after_close() {

        File file = Csv.createIfFileNotExist();
        fileManager = FileBackedTasksManager.loadFromFile(file);

        System.out.println("История после восстановления из файла");
        fileManager.getHistory().forEach(h -> {
            System.out.println("ID: " + h.getId() + " | Название: " + h.getTittle() + " | Описание: " + h.getDescription());
        });

        Task task = new Task("задача 1", "добавление задачи после восстановления", TaskType.TASK);
        fileManager.addNewTask(task);

    }


}
