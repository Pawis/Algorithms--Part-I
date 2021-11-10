
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private int n;
    private Node first;
    private Node last;

    public Deque() {
        this.n = 0;
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (!isEmpty()) {
            oldFirst.previous = first;
        } else
            last = first;
        n++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        n++;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first.item = null;
        first = first.next;

        n--;
        if (isEmpty())
            first = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        if (n > 1) {
            // last.previous = null;
            // last.item = null;
            last = last.previous;
            last.next.item = null;
            last.next = null;
        } else {
            last = null;
            first = null;
        }
        /*
        if (last.previous == null) {
            last.item = null;
            last.next = null;
            first = null;
        } else if (n == 1) {
            first = last;
        } else {
            last = last.previous;
            last.item = null;
        }

         */

        n--;
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> que = new Deque<>();
        /*que.addFirst(5);
        que.addLast(7);
        System.out.println(que.removeFirst());
        System.out.println(que.removeLast());
        que.addLast(2);
        System.out.println(que.size());
        System.out.println(" ");
        System.out.println(que.isEmpty());
        for (Integer a : que) {
            System.out.print(a + " ");
        }

         */
        que.addLast(5);

        que.addLast(5);
        que.removeLast();
        System.out.println(" ");
        System.out.println(que.size());
        System.out.println(" ");
        for (Integer a : que) {
            System.out.print(a + " ");
        }
    }

}
