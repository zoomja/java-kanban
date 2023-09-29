package tasks;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String tittle, String description, int epicId) {
        super(tittle, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
