import managers.Csv;
import managers.FileBackedTasksManager;
import managers.TaskType;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import test.Test;

import java.io.File;

public class Main {

    public static void main(String[] args) {

//        Test test = new Test();
//
//        test.test_Utils();
//        test.test_after_close();

//        надеюсь я приваьно сделал, но я не совсем понимаю какой смысл это этого

        File file = new File(Csv.DEFAULT_FILE_NAME);
        FileBackedTasksManager manager1 = new FileBackedTasksManager(file);


        manager1.addNewTask(new Task("Задача 1", "Описание задачи 1", TaskType.TASK));
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", TaskType.EPIC);
        manager1.addNewEpic(epic);
        manager1.addNewSubtask(new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId(), TaskType.SUBTASK));

        // Имитация просмотра задач для заполнения истории
        manager1.getTaskById(1);
        manager1.getEpicById(2);
        manager1.getSubtaskById(3);


        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(file);

        System.out.println("Задачи после восстановления:");
        manager2.getAllTasks().forEach(System.out::println);
        manager2.getALlEpics().forEach(System.out::println);
        manager2.getAllSubTasks().forEach(System.out::println);

        System.out.println("\nИстория после восстановления:");
        manager2.getHistory().forEach(System.out::println);
    }
}