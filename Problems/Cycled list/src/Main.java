import java.io.*;
import java.util.*;

class Main {

    static class Node<E> {

        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(E element, Node<E> next, Node<E> prev) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }

        Node<E> getNext() {
            return next;
        }

        Node<E> getPrev() {
            return prev;
        }
    }

    public static class DoublyLinkedList<E> {

        public Node head;
        public Node tail;
        public Node pointer;
        public int size;

        DoublyLinkedList() {
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

            StringBuilder result = new StringBuilder();
            Node iterator = head;

            for (int i = 0; i < size; i++) {
                result.append(iterator.value).append(" ");
                iterator = iterator.next;
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

        void addFirst(Node node) {

            head = node;
            tail = node;
            head.next = tail;
            tail.prev = head;
            pointer = head;
        }

        void removeLeft(int distance) {
            for (int i = 0; i < distance; i++) {
                pointer = pointer.prev;
            }
            remove();

            if (!isEmpty()) {
                pointer = pointer.prev;
            }
        }

        void removeRight(int distance) {
            for (int i = 0; i < distance; i++) {
                pointer = pointer.next;
            }
            remove();

            if (!isEmpty()) {
                pointer = pointer.next;
            }
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

        void add(E elem) {
            Node<E> tmp = new Node<>(elem, head, tail);

            if (isEmpty()) {
                addFirst(tmp);
            } else {
                head.prev = tmp;
                tail.next = tmp;
                tail = tmp;
            }
            size++;
        }

        public void removeFirst() {
            head = head.next;
            head.prev = tail;
            tail.next = head;
            size--;
        }

        public void removeLast() {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
            size--;
        }

        public void removeHead() {
            head = null;
            tail = null;
            pointer = null;
            size--;
        }

        public void remove() {
            if (isEmpty()) {
                return;
            }

            if (size == 1) {
                removeHead();
                return;
            }

            if (pointer == head) {
                removeFirst();
                return;
            }
            if (pointer == tail) {
                removeLast();
                return;
            }

            pointer.prev.next = pointer.next;
            pointer.next.prev = pointer.prev;
            size--;
        }

    }

    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
         DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
         boolean isFirstLineExist = false;
        while (sc.hasNextLine()) {
            String[] bugs = sc.nextLine().split(" ");
            //System.out.println(bugs);
            if (bugs.length == 2 && !bugs[0].equals("l") && !bugs[0].equals("r")) {
                int size = Integer.parseInt(bugs[0]);
                int rounds = Integer.parseInt(bugs[1]);
                bugs = sc.nextLine().split(" ");
                for (int i = 0; i < bugs.length; i++) {
                    list.add(Integer.parseInt(bugs[i]));
                }
                isFirstLineExist = true;
            } else if (!isFirstLineExist && !bugs[0].equals("l") && !bugs[0].equals("r")) {
                for (int i = 0; i < bugs.length; i++) {
                    list.add(Integer.parseInt(bugs[i]));
                }
            } else {
                String ops = bugs[0];
                int distance = Integer.parseInt(bugs[1]);

                switch (ops) {
                    case "r":
                        try {
                            list.removeRight(distance);
                        } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        break;
                    case "l":
                        try {
                            list.removeLeft(distance);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        break;
                    default:
                        break;
                }
            }

        }
        sc.close();
        System.out.println(list.toString());

        /*int size = sc.nextInt();
        int rounds = sc.nextInt();

        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(sc.nextInt());
        }

        for (int i = 0; i < rounds; i++) {
            String ops = sc.next();
            int distance = sc.nextInt();

            switch (ops) {
                case "r":
                    list.removeRight(distance);
                    System.out.println(list.toString());
                    break;
                case "l":
                    list.removeLeft(distance);
                    System.out.println(list.toString());
                    break;
                default:
                    break;
            }
        }

        System.out.println(list.toString());*/
    }
}
