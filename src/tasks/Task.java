package tasks;

import managers.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    protected String tittle;
    protected String description;
    protected Status status;
    private TaskType taskType;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Task(String tittle, String description, TaskType taskType, long durationMinutes, LocalDateTime startTime) {
        this.tittle = tittle;
        this.description = description;
        this.taskType = taskType;
        this.duration = Duration.ofMinutes(durationMinutes);
        this.startTime = startTime;
        this.endTime = calculateEndTime();
        status = Status.NEW;
    }


    public Task(int id, String tittle, String description, Status status, TaskType taskType, long durationMinutes, LocalDateTime startTime) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(durationMinutes);
        this.startTime = startTime;
        this.taskType = taskType;
        this.endTime = calculateEndTime();
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(long durationMinutes) {
        this.duration = Duration.ofMinutes(durationMinutes);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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

    private LocalDateTime calculateEndTime() {
        return startTime.plus(duration);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String separator = ",";
        return getId() + separator + getTaskType() + separator + getTittle() + separator + getStatus() + separator + getDescription() + separator + getStartTime() + separator + getEndTime() + separator + getDuration().toMinutes();
    }
}
