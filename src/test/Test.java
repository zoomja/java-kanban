package test;
import manager.Manager;
import tasks.Status;

import java.util.Scanner;

public class Test {

    Manager m = new Manager();


    public void testManager() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Создали 3 таски: ");
        m.newTask("Утро ", "умыться");
        m.newTask("Завтрак ", "сделать кофе");
        m.newTask("После еды ", "прибраться на кухне");
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали 'эпик' ");
        m.newEpic("сегодня ", "большие планы");
        m.newEpic("завтра ", "планов ещё больше");
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали сабтаск ");
        m.newSubtask("Пойти на работу ", "Рабочий день 8,00 - 17,00", 4);
        m.newSubtask("Покупки ", "купить в магазине продукты для ужина", 4 );
        m.newSubtask("Готовка ", "приготовить ужин", 4 );
        System.out.println(".............................................");
        System.out.println(" ");
        System.out.println("Создали сабтаск ");
        m.newSubtask("Сон ", "выспаться в выходной", 5);
        m.newSubtask("Завтрак ", "не забыть поесть", 5);
        m.newSubtask("уборка ", "навести дома порядок", 5);
        System.out.println(".............................................");
        System.out.println(" ");

        while (true) {

            printMenu();

            int i = scanner.nextInt();
            scanner.nextLine();
            if (i == 1) {
                System.out.println("Введите ID (не больше 3-ёх): ");
                int id = scanner.nextInt();
                m.getIdTask(id);
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 2) {
                m.getAllTasks();
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 3) {
                m.updateTask(1, "Утро ", "умываюсь", Status.IN_Progress);
                m.updateTask(2, "Завтрак ", "пью кофе", Status.IN_Progress);
                m.updateTask(3, "После еды ", "умылся и поел. Пора навести порядок", Status.IN_Progress);
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 4) {
                m.updateTask(1, "Утро ", "умылся", Status.DONE);
                m.updateTask(2, "Завтрак ", "кофе выпил", Status.DONE);
                m.updateTask(3, "После еды ", "На кухне порядок", Status.DONE);
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 5) {
                System.out.println("Введите ID (не больше 3-ёх): ");
                int id = scanner.nextInt();
                m.deleteTask(id);
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 6) {
                System.out.println("Введите ID (не больше 3-ёх): ");
                int id = scanner.nextInt();
                m.getIdEpic(id);
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 7) {
                m.getAllEpics();
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 8) {
                m.getAllEpicsWithSubtasks();
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 9) {
                m.updateSubtask(6, "Пришёл с работы ", "рабочий день закончился", Status.DONE);
                m.updateSubtask(7, "Купил все что нужно ", "денег больше нет", Status.DONE);
                m.updateSubtask(8, "Ужин ", "поел", Status.DONE);
                System.out.println(".............................................");
                System.out.println(" ");
            }else if (i == 10) {
                System.out.println("Введите ID (1 или 2): ");
                int id = scanner.nextInt();
                m.deleteEpic(id);
                System.out.println(".............................................");
                System.out.println(" ");
            }else if (i == 11) {
                m.deleteAllEpics();
                System.out.println(".............................................");
                System.out.println(" ");
            } else if (i == 0) {
                System.out.println("Пока!");
                scanner.close();
                return;
            } else {
                System.out.println("Такой команды нет");
            }
        }
    }

    static void printMenu() {
        System.out.println("Поиск таски по id - 1");
        System.out.println("Вывод всех тасок - 2");
        System.out.println("Дать таскам статус IN_Progress - 3");
        System.out.println("Дать таскам статус DONE - 4");
        System.out.println("Удаление таски по id - 5 ");
        System.out.println("Поиск эпика по id - 6 ");
        System.out.println("Вывод всех эпиков - 7 ");
        System.out.println("Вывод всех эпиков с сабтаск - 8 ");
        System.out.println("обновление Subtask - 9 ");
        System.out.println("удаление эпика по id - 10 ");
        System.out.println("Удалить все эпики - 11");
        System.out.println("выход - 0");
    }

}

//                        System.out.println("Заголовок ");
//                        String tittle = scanner.nextLine();
//                        System.out.println("Введите заголовок: " + tittle);
//                        String description = scanner.nextLine();
//                        System.out.println("Описание ");
//                        System.out.println("Введите описание: " + description);
//                        m.newTask(tittle, description);
//                        System.out.println("//////////////////////////////////////////////");

