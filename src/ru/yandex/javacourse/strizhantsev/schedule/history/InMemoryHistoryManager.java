package ru.yandex.javacourse.strizhantsev.schedule.history;

import ru.yandex.javacourse.strizhantsev.schedule.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> history = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        linkLast(task);
        history.put(task.getId(), tail);
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task, tail, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();

        Node node = head;
        while (Objects.nonNull(node)) {
            result.add(node.task);
            node = node.next;
        }
        return result;
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = history.remove(id);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
        }
    }

    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
    }

    public static class Node {
        private final Task task;
        private Node next;
        private Node prev;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}