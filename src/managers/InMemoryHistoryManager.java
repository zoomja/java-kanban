package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private LinkedList history;


    public InMemoryHistoryManager() {
        history = new LinkedList();
    }


    @Override
    public void addTask(Task task) {
        if (task != null) {
            remove(task.getId());
            history.linkLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }
}