package interfaces;

import tasks.Task;

import java.util.List;

public interface HistoryManager {
    void remove(int id);

    void addTask(Task task);

    List<Task> getHistory();
}