import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import java.util.List;



public class Main {

    public static void main(String[] args) {

        TaskManager m = Managers.getDefault();


        System.out.println("Создали 2 таски: ");
        Task taskOne = new Task("Таска 1 ", "Описание 1");
        Task taskTwo = new Task("Таска 2 ", "Описание 2");
        m.addNewTask(taskOne);
        m.addNewTask(taskTwo);
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали 1 эпик с тремя подзадачами: ");
        Epic epicOne = new Epic("Эпик 1 ", "описание Эпика 1");
        m.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask("Сабтаск 1 ", "для эпика 1", 4);
        m.addNewSubtask(subtaskOne);
        Subtask subtaskTwo = new Subtask("Сабтаск 2 ", "для эпика 1", 4);
        m.addNewSubtask(subtaskTwo);
        Subtask subtaskThree = new Subtask("Сабтаск 3 ", "для эпика 1", 4);
        m.addNewSubtask(subtaskThree);
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали 1 эпик без подзадач: ");
        Epic epicTwo = new Epic("Эпик 2 ", "описание Эпика 2");
        m.addNewEpic(epicTwo);

        m.getEpicById(epicTwo.getId());
        m.getTaskById(taskOne.getId());
        m.getTaskById(taskTwo.getId());
        m.getTaskById(taskOne.getId());
        m.getTaskById(taskTwo.getId());
        m.getSubtaskById(subtaskTwo.getId());
        m.getSubtaskById(subtaskOne.getId());
        m.getSubtaskById(subtaskTwo.getId());
        m.getEpicById(epicOne.getId());

        System.out.println(".............................................");
        System.out.println(" ");

        System.out.println("История просмотренных задач:");
        System.out.println(m.getHistory().size());
        for (Task task : m.getHistory()) {
            System.out.println("ID: " + task.getId() + " " + task.getTittle() + " " + task.getDescription() + " " + task.getStatus());
            System.out.println("--------------");
        }

        m.remove(taskOne.getId());

        System.out.println("История просмотренных задач после удаления:");
        System.out.println(m.getHistory().size());
        for (Task task : m.getHistory()) {
            System.out.println("ID: " + task.getId() + " " + task.getTittle() + " " + task.getDescription() + " " + task.getStatus());
            System.out.println("--------------");
        }

        m.remove(epicOne.getId());
        m.remove(subtaskOne.getId());
        m.remove(subtaskTwo.getId());

        System.out.println("История просмотренных задач после удаления:");
        System.out.println(m.getHistory().size());
        for (Task task : m.getHistory()) {
            System.out.println("ID: " + task.getId() + " " + task.getTittle() + " " + task.getDescription() + " " + task.getStatus());
            System.out.println("--------------");
        }




//        System.out.println("Создали 3 таски: ");
//        Task taskOne = new Task("Утро ", "умыться");
//        Task taskTwo = new Task("Завтрак ", "сделать кофе");
//        Task taskThree = new Task("После еды ", "прибраться на кухне");
//        m.addNewTask(taskOne);
//        m.addNewTask(taskTwo);
//        m.addNewTask(taskThree);
//        System.out.println(".............................................");
//        System.out.println(" ");
//        System.out.println("Создали 'эпик' ");
//        Epic epicOne = new Epic("сегодня ", "большие планы");
//        Epic epicTwo = new Epic("завтра ", "планов ещё больше");
//        m.addNewEpic(epicOne);
//        m.addNewEpic(epicTwo);
//        System.out.println(".............................................");
//        System.out.println(" ");
//        System.out.println("Создали сабтаск ");
//        Subtask subtaskOne = new Subtask("Пойти на работу ", "Рабочий день 8,00 - 17,00", 5);
//        Subtask subtaskTwo = new Subtask("Покупки ", "купить в магазине продукты для ужина", 5);
//        Subtask subtaskThree = new Subtask("Сон ", "выспаться в выходной", 6);
//        m.addNewSubtask(subtaskOne);
//        m.addNewSubtask(subtaskTwo);
//        m.addNewSubtask(subtaskThree);
//        System.out.println(".............................................");
//        System.out.println(" ");
//        m.printAllTasks();
//        System.out.println(".............................................");
//        System.out.println(" ");
//        taskOne.setTittle("Завтрак");
//        taskOne.setDescription("умылся");
//        taskOne.setStatus(Status.DONE);
//        m.updateTask(taskOne);
//        System.out.println(".............................................");
//        System.out.println(" ");
//        m.printAllEpicsWithSubtasks();
//        System.out.println(".............................................");
//        System.out.println(" ");
//        subtaskOne.setStatus(Status.DONE);
//        subtaskOne.setTittle("Пришёл с работы ");
//        subtaskOne.setDescription("рабочий день закончился");
//        m.updateSubtask(subtaskOne);
//        subtaskThree.setStatus(Status.DONE);
//        subtaskThree.setTittle("Пришёл с работы ");
//        subtaskThree.setDescription("рабочий день закончился");
//        m.updateSubtask(subtaskThree);
//        System.out.println(".............................................");
//        System.out.println(" ");
//        m.deleteEpic(5);
//        System.out.println(".............................................");
//        System.out.println(" ");
//        m.printAllEpics();
//        System.out.println(".............................................");
//        m.getEpicById(6);
//        m.getTaskById(2);
//        m.getTaskById(3);
//        m.getSubtaskById(9);
//
//        for (int i = 0; i <= 8; i++) {
//            Task task = new Task("Утро " + i, "умыться " + i);
//            m.addNewTask(task);
//            m.getTaskById(task.getId());
//        }
//
//        System.out.println("История просмотренных задач:");
//        System.out.println(m.getHistory().size());
//        for (Task task : m.getHistory()) {
//            System.out.println("ID: " + task.getId() + " " + task.getTittle() + " " + task.getDescription() + " " + task.getStatus());
//            System.out.println("--------------");
//        }


    }
}