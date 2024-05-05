package interfaces;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    void addNewTask(Task task);

    Task getTaskById(int taskId);

    Subtask getSubtaskById(int subId);

    Epic getEpicById(int epicId);

    void printTaskById(int id);

    void printAllTasks();

    Task updateTask(Task newTask);

    void deleteTask(int taskId);

    boolean deleteSubtaskById(int subId);

    Epic addNewEpic(Epic epic);

    void printIdEpic(int id);

    void printAllEpics();

    Subtask addNewSubtask(Subtask subtask);

    void printAllEpicsWithSubtasks();

    Subtask updateSubtask(Subtask newSubtask);

    void deleteAllEpics();

    void deleteEpic(int epicId);

    List<Task> getHistory();

    void remove(int id);

    List<Task> getAllTasks();

    List<Epic> getALlEpics();

    List<Subtask> getAllSubTasks();

    public TreeSet<Task> getPrioritizedTasks();

}