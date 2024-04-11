import managers.Csv;
import managers.FileBackedTasksManager;
import managers.TaskType;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

//        Test test = new Test();
//
//        test.test_Utils();
//        test.test_after_close();



        File file = new File(Csv.DEFAULT_FILE_NAME);
        FileBackedTasksManager manager1 = new FileBackedTasksManager(file);
        // Создание продолжительности и времени начала
        LocalDateTime startTime = LocalDateTime.now(); // Текущее время


        Task taskOne = new Task("Таска 1 ", "Описание 1", TaskType.TASK, 43800, startTime);
        Task taskDva = new Task("Таска 2 ", "Описание 2", TaskType.TASK, 43800, startTime.plusMonths(1));
        Task taskThree = new Task("Таска 3 ", "Описание 3", TaskType.TASK, 43800, startTime.minusMonths(1));

        Epic epicOne = new Epic("epic1", "Эпик 1 ", TaskType.EPIC);
        manager1.addNewEpic(epicOne);

        Subtask subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 1440, LocalDateTime.now());
        Subtask subtaskDva = new Subtask("SUB2", "OPIS2", epicOne.getId(), TaskType.SUBTASK, 1440, LocalDateTime.now().plusMonths(1));
        Subtask subtaskThree = new Subtask("SUB3", "OPIS3", epicOne.getId(), TaskType.SUBTASK, 1440, LocalDateTime.now().plusMonths(5));

        manager1.addNewTask(taskOne);
        manager1.addNewTask(taskDva);
        manager1.addNewTask(taskThree);


        manager1.addNewSubtask(subtaskOne);
        manager1.addNewSubtask(subtaskDva);
        manager1.addNewSubtask(subtaskThree);

        System.out.println(manager1.getSubtaskById(subtaskOne.getId()).toString());
        System.out.println(manager1.getSubtaskById(subtaskDva.getId()).toString());
        System.out.println(manager1.getSubtaskById(subtaskThree.getId()).toString());
        System.out.println(manager1.getEpicById(epicOne.getId()).toString());
        System.out.println(manager1.getTaskById(taskOne.getId()));
        System.out.println(manager1.getTaskById(taskDva.getId()));
        System.out.println(manager1.getTaskById(taskThree.getId()));

        manager1.getPrioritizedTasks().forEach(t -> System.out.println(t.getTittle()));
        System.out.print(manager1.getPrioritizedTasks().stream().count());


        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(file);

        System.out.println("Задачи после восстановления:");
        manager2.getAllTasks().forEach(System.out::println);
        manager2.getALlEpics().forEach(System.out::println);
        manager2.getAllSubTasks().forEach(System.out::println);

        System.out.println("\nИстория после восстановления:");
        manager2.getHistory().forEach(System.out::println);
    }
}