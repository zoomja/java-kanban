package managers;


import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkedList {
    private Node first;
    private Node last;
    private final Map<Integer, Node> tasksMap = new HashMap<>();

    private int size;

    public void linkLast(Task task) {
        Node lastNode = this.last;
        Node newNode = new Node(task, null, lastNode);

        this.last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.setNext(newNode);
        }

        tasksMap.put(newNode.getTask().getId(), newNode);
        size++;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node head = this.first;

        while (head != null) {
            tasks.add(head.getTask());
            head = head.getNext();
        }

        return tasks;
    }

    public void remove(int id) {
        if (tasksMap.containsKey(id)) {
            removeNode(tasksMap.get(id));
        }
    }

    private void removeNode(Node node) {
        if (node == first & node == last) {
            last = null;
            first = null;
        } else if (node.equals(first)) {
            first = node.getNext();
            node.getNext().setPrev(null);
        } else if (node.equals(last)) {
            last = node.getPrev();
            node.getPrev().setNext(null);
        } else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }

        tasksMap.remove(node.getTask().getId());
        size--;
    }

    static class Node {
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
}