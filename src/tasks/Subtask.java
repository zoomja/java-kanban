package tasks;

import managers.TaskType;

import java.time.LocalDateTime;

public class Subtask extends Task {
    public int epicId;

    public Subtask(String tittle, String description, int epicId, TaskType taskType, long durationMinutes, LocalDateTime startTime) {
        super(tittle, description, taskType,durationMinutes, startTime);
        this.epicId = epicId;
    }

    public Subtask(int id, String tittle, String description, Status status, TaskType taskType, int epicId, long durationMinutes, LocalDateTime startTime) {
        super(id, tittle, description, status, taskType, durationMinutes, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }



    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        String separator = ",";
        return getId() + separator + getTaskType() + separator + getTittle() + separator + getStatus() + separator + getDescription() + separator + getStartTime() + separator + getEndTime() + separator + getDuration().toMinutes() + separator + getEpicId();
    }
}
