import manager.Manager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;


public class Main {

    public static void main(String[] args) {

        Manager m = new Manager();

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
        System.out.println(" ");
        m.deleteAllEpics();
        System.out.println(".............................................");
        System.out.println(" ");




    }
}
