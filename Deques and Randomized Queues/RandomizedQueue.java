import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] que;
    private int n;

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
	public RandomizedQueue() {
        this.que = (Item[]) new Object[1];
        this.n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    /* private void print() {
        for (Item a : que) {
            System.out.print(a + " ");
        }
    }

     */

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (n == que.length)
            resizeArrayUp();
        que[n++] = item;
    }

    private void resizeArrayUp() {
        @SuppressWarnings("unchecked")
		Item[] copy = (Item[]) new Object[que.length * 2];
        for (int i = 0; i < n; i++) {
            copy[i] = que[i];
        }
        que = copy;
    }

    private void resizeArrayDown() {
        @SuppressWarnings("unchecked")
		Item[] copy = (Item[]) new Object[que.length / 2];
        for (int i = 0; i < n; i++) {
            copy[i] = que[i];
        }
        que = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int rn = StdRandom.uniform(0, n);
        Item item = que[rn];
        if (rn != n - 1) {
            que[rn] = que[n - 1];
            que[n - 1] = null;
            n--;
            if (n < que.length / 4) {
                resizeArrayDown();
            }
        } else {
            que[n - 1] = null;
            n--;
            if (n < que.length / 4) {
                resizeArrayDown();
            }
        }


        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return que[StdRandom.uniform(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        private final Item[] copy;
        private int i = 0;

        @SuppressWarnings("unchecked")
		public QueueIterator() {
            this.copy = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                copy[j] = que[j];
            }

            for (int j = 0; j < n; j++) {
                int r = StdRandom.uniform(j + 1);
                Item swap = copy[j];
                copy[j] = copy[r];
                copy[r] = swap;

            }



            /* while (i < n) {
                int q = StdRandom.uniform(n);
                if (copy[q] == null) {
                    copy[q] = que[i];
                    i++;
                }
            }

             */


            this.i = copy.length;
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return copy[--i];
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> que = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            que.enqueue(i);
        }
        for (Integer a : que) {
            System.out.print(a + " ");
        }
        System.out.println(" ");
        for (Integer a : que) {
            System.out.print(a + " ");

        }
        System.out.println(" ");
        for (Integer a : que) {
            System.out.print(a + " ");

        }
        System.out.println(" ");
        System.out.println(que.dequeue());
        System.out.println(que.sample());
        System.out.println(que.size());
        System.out.println(que.isEmpty());

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }


        /*
        System.out.println(" ");
        que.print();
        while (true) {
            int input = scan.nextInt();
            if (input == 0)
                System.out.println(que.dequeue());
            System.out.println(" ");
            que.print();
            System.out.println(" ");
            for (Integer a : que) {
                System.out.print(a + " ");

            }
        }

         */

        /* for (int i = 0; i < que.size(); i++) {
            System.out.print(que.sample() + " ");
        }


        que.print();
        System.out.println(" ");
        for (Integer a : que) {
            System.out.print(a + " ");
        }

         */
    }

}