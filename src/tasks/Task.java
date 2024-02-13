package tasks;

import managers.TaskType;

public class Task {
    protected int id;
    protected String tittle;
    protected String description;
    protected Status status;
    private TaskType taskType;

    public Task(String tittle, String description, TaskType taskType) {
        this.tittle = tittle;
        this.description = description;
        this.taskType = taskType;
        status = Status.NEW;
    }

    public Task(int id, String tittle, String description, Status status, TaskType taskType) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.status = status;
        this.taskType = taskType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        String separator = ",";
        return getId() + separator + getTaskType() + separator + getTittle() + separator + getStatus() + separator + getDescription();
    }
}
