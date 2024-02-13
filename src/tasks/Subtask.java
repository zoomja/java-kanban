package tasks;

import managers.TaskType;

public class Subtask extends Task {
    public int epicId;

    public Subtask(String tittle, String description, int epicId, TaskType taskType) {
        super(tittle, description, taskType);
        this.epicId = epicId;
    }

    public Subtask(int id, String tittle, String description, Status status, TaskType taskType, int epicId) {
        super(id, tittle, description, status, taskType);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }



    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
