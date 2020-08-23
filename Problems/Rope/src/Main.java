import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int queryNumber = scanner.nextInt();
        DoublyLinkedList<Character> rope = new DoublyLinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            rope.addLast(input.charAt(i));
        }
        scanner.nextLine();
        for (int i = 0; i < queryNumber; i++) {
            String[] ops = scanner.nextLine().split(" ");
            switch (ops[0]) {
                case "split":
                    DoublyLinkedList<Character> tail = new DoublyLinkedList<>();
                    for (int j = 0; j < Integer.parseInt(ops[1]); j++) {
                        tail.addLast(rope.getHead().getValue());
                        rope.removeFirst();
                    }
                    //System.out.println(tail.toString());
                    //System.out.println(rope.toString());
                    for (int k = 0; k < Integer.parseInt(ops[1]); k++) {
                        rope.addLast(tail.getHead().getValue());
                        tail.removeFirst();
                    }
                    //System.out.println(tail.toString());
                    //System.out.println(rope.toString());
                    break;
                case "reverse":
                    rope.reverse();
                    //System.out.println(rope.toString());
                    break;
            }
        }
        System.out.println(rope.toString());

    }
}
class Node<E> {

    public E value;
    public Node<E> next;
    public Node<E> prev;

    Node(E element, Node<E> next, Node<E> prev) {
        this.value = element;
        this.next = next;
        this.prev = prev;
    }

    public E getValue() {
        return value;
    }

    Node<E> getNext() {
        return next;
    }

    Node<E> getPrev() {
        return prev;
    }
}


class DoublyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        size = 0;
    }

    public Node<E> getHead() {
        return head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {

        Node<E> tmp = head;
        StringBuilder result = new StringBuilder();

        while (tmp != null) {
            result.append(tmp.value);
            tmp = tmp.next;
        }

        return result.toString();
    }

    void addFirst(E elem) {

        Node<E> tmp = new Node<>(elem, head, null);

        if (head != null) {
            head.prev = tmp;
        }

        head = tmp;

        if (tail == null) {
            tail = tmp;
        }
        size++;
    }

    void addLast(E elem) {

        Node<E> tmp = new Node<>(elem, null, tail);

        if (tail != null) {
            tail.next = tmp;
        }

        tail = tmp;

        if (head == null) {
            head = tmp;
        }
        size++;
    }

    void add(E elem, Node<E> curr) {

        if (curr == null) {
            throw new NoSuchElementException();
        }

        Node<E> tmp = new Node<>(elem, curr, null);

        if (curr.prev != null) {
            curr.prev.next = tmp;
            tmp.prev = curr.prev;
            curr.prev = tmp;
        } else {
            curr.prev = tmp;
            tmp.next = curr;
            head = tmp;
        }
        size++;
    }

    public void removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
    }

    public void removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
    }

    public void remove(Node<E> curr) {
        if (curr == null)
            throw new NoSuchElementException();
        if (curr.prev == null) {
            removeFirst();
            return;
        }
        if (curr.next == null) {
            removeLast();
            return;
        }
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;
    }

    public void reverse() {
        DoublyLinkedList<E> tmp = new DoublyLinkedList<>();

        Node<E> node = this.tail;
        while (node != null) {
            tmp.addLast(node.value);
            node = node.prev;
        }

        this.head = tmp.head;
        this.tail = tmp.tail;

    }



}