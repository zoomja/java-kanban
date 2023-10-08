package managers;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}

//Этот класс я,врое, сделал как написанно в тз, но так и не понял правильно ли сделал и зачем он нужен. Можете пояснить,
//        пожалуйста, этот момент