package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    int countId();

    void addNewTask(Task task);

    Task getTaskById(int taskId);

    Subtask getSubtaskById(int subId);

    Epic getEpicById(int epicId);

    void printTaskById(int id);

    void printAllTasks();

    void updateTask(Task newTask);

    void deleteTask(int taskId);

    void deleteSubtaskById(int subId);

    void addNewEpic(Epic epic);

    void printIdEpic(int id);

    void printAllEpics();

    void addNewSubtask(Subtask subtask);

    void printAllEpicsWithSubtasks();

    void updateSubtask(Subtask newSubtask);

    void calculateEpicStatus(int epicId);

    void deleteAllEpics();

    void deleteEpic(int epicId);

    List<Task> getHistory();

}