import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        TaskManager m = new InMemoryTaskManager();
        List<Task> history = m.getHistory();

        System.out.println("Создали 3 таски: ");
        Task taskOne = new Task("Утро ", "умыться");
        Task taskTwo = new Task("Завтрак ", "сделать кофе");
        Task taskThree = new Task("После еды ", "прибраться на кухне");
        m.addNewTask(taskOne);
        m.addNewTask(taskTwo);
        m.addNewTask(taskThree);
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали 'эпик' ");
        Epic epicOne = new Epic("сегодня ", "большие планы");
        Epic epicTwo = new Epic("завтра ", "планов ещё больше");
        m.addNewEpic(epicOne);
        m.addNewEpic(epicTwo);
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали сабтаск ");
        Subtask subtaskOne = new Subtask("Пойти на работу ", "Рабочий день 8,00 - 17,00", 5);
        Subtask subtaskTwo = new Subtask("Покупки ", "купить в магазине продукты для ужина", 5);
        Subtask subtaskThree = new Subtask("Сон ", "выспаться в выходной", 6);
        m.addNewSubtask(subtaskOne);
        m.addNewSubtask(subtaskTwo);
        m.addNewSubtask(subtaskThree);
        System.out.println(".............................................");
        System.out.println(" ");
        m.printAllTasks();
        System.out.println(".............................................");
        System.out.println(" ");
        taskOne.setTittle("Завтрак");
        taskOne.setDescription("умылся");
        taskOne.setStatus(Status.DONE);
        m.updateTask(taskOne);
        System.out.println(".............................................");
        System.out.println(" ");
        m.printAllEpicsWithSubtasks();
        System.out.println(".............................................");
        System.out.println(" ");
        subtaskOne.setStatus(Status.DONE);
        subtaskOne.setTittle("Пришёл с работы ");
        subtaskOne.setDescription("рабочий день закончился");
        m.updateSubtask(subtaskOne);
        subtaskThree.setStatus(Status.DONE);
        subtaskThree.setTittle("Пришёл с работы ");
        subtaskThree.setDescription("рабочий день закончился");
        m.updateSubtask(subtaskThree);
        System.out.println(".............................................");
        System.out.println(" ");
        m.deleteEpic(5);
        System.out.println(".............................................");
        System.out.println(" ");
        m.printAllEpics();
        System.out.println(".............................................");
//        m.deleteTask(3);
//        m.printAllTasks();
//        System.out.println(".............................................");
//        m.deleteSubtaskById(9);
//        System.out.println(".............................................");
//        m.printAllEpicsWithSubtasks();
//        System.out.println(".............................................");
//        m.deleteAllEpics();
//        System.out.println(".............................................");
        m.getEpicById(6);
        m.getTaskById(2);
        m.getTaskById(3);
        m.getSubtaskById(9);
        System.out.println(".............................................");
        m.getHistory();
        System.out.println(".............................................");


        System.out.println("История просмотренных задач:");
        for (Task task : history) {
            System.out.println("ID: " + task.getId() + " " + task.getTittle() + " " + task.getDescription() + " " + task.getStatus());
            System.out.println("--------------");
        }


    }
}
