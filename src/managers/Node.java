package managers;

import tasks.Task;

public class Node {
    private Task task;
    private Node prev;
    private Node next;

    public Node(Task task, Node next, Node first) {
        this.task = task;
        this.prev = first;
        this.next = next;
    }

    public Task getTask() {
        return task;
    }

    public void setData(Task task) {
        this.task = task;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

