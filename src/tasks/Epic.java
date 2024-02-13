package tasks;

import managers.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskId;

    public Epic(String tittle, String description, TaskType taskType) {
        super(tittle, description, taskType);
        subtaskId = new ArrayList<>();
    }

    public Epic(int id, String tittle, String description, Status status, TaskType taskType) {
        super(id, tittle, description, status, taskType);
        subtaskId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }
}