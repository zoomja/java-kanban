package tasks;

import managers.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskId;
    private LocalDateTime endTime;

    public Epic(String tittle, String description, TaskType taskType) {
        super(tittle, description, taskType, Duration.ZERO.toMinutes(), LocalDateTime.now());
        subtaskId = new ArrayList<>();
    }

    public Epic(int id, String tittle, String description, Status status, TaskType taskType, LocalDateTime startTime) {
        super(id, tittle, description, status, taskType, Duration.ZERO.toMinutes(), startTime);
        subtaskId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }

    //    private LocalDateTime getStartDateForEpic()



    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}