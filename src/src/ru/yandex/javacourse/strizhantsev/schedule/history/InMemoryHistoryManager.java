package src.ru.yandex.javacourse.strizhantsev.schedule.history;

import src.ru.yandex.javacourse.strizhantsev.schedule.task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
        }

        Node newNode = new Node(task);
        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }

        nodeMap.put(task.getId(), newNode);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();

        Node node = head;
        while (Objects.nonNull(node)) {
            result.add(node.getTask());
            node = node.next;
        }
        return result;
    }

    @Override
    public void remove(int id) {
        Node removeNode = nodeMap.remove(id);

        if (removeNode != null) {
            if (removeNode.prev != null) {
                removeNode.prev.next = removeNode.next;
            } else {
                head = removeNode.next;
            }

            if (removeNode.next != null) {
                removeNode.next.prev = removeNode.prev;
            } else {
                tail = removeNode.prev;
            }
        }
    }

    public static class Node {
        private Node next;
        private Node prev;
        private Task task;

        public Node(Task task) {
            this.task = task;
        }

        public Task getTask() {
            return task;
        }


    }
}
