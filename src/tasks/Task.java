package tasks;

public class Task {
    protected int id;
    protected String tittle;
    protected String description;
    protected Status status;
    private static int count = 0;

    public Task(String tittle, String description) {
        this.id = countId();
        this.tittle = tittle;
        this.description = description;
        status = Status.NEW;
    }
    private int countId() {
        return ++count;
    }

    public int getId() {
        return id;
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
}
