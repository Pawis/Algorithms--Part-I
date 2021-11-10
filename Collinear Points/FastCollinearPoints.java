

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private Point[] seg;
    private final Point[] aux;
    private int size;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        this.seg = new Point[10];
        this.aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            aux[i] = points[i];
            if (i > 0 && aux[0].compareTo(aux[i]) == 0)
                throw new IllegalArgumentException();
        }
        this.size = 0;
        findLineSegments();
    }

    private void recurssion() {
        int indexE = 0;
        int start = 1;
        Point[] pkt = new Point[3];
        // for (int i = 0; i < aux.length; i++) {
        //    System.out.print(aux[i] + " ");
        // }
/*
        System.out.println();
        for (int i = 0; i < dub.length; i++) {
            System.out.print(dub[i] + " ");
        }

 */


        // System.out.println();
        for (int i = 1; i < aux.length; i++) {
            if ((i + 1 < aux.length) && aux[0].slopeTo(aux[start]) == aux[0].slopeTo(aux[i + 1])) {
                //  System.out.println(aux[0] + " + " + aux[start] + " + " + aux[i + 1]);
                indexE++;
                // System.out.println(indexE);
                // System.out.println(i);
            } else if (indexE >= 2) {
                // System.out.println(aux[0].slopeTo(aux[start]) + " / " + aux[0].slopeTo(aux[i]));
                //  System.out.println(i);
                //   System.out.println(start);
                //   System.out.println(indexE);
                //  Arrays.sort(aux, start, i + 1);
                //  System.out.println();
                pkt[0] = aux[i];
                pkt[1] = aux[0];
                pkt[2] = aux[start];
                start = i + 1;
                indexE = 0;
                //  System.out.println(pkt[0] + " + " + pkt[1] + " + " + pkt[2]);
                Arrays.sort(pkt);
                if (size != 0) {
                    if (!compareSlope(pkt[0], pkt[2])) {
                        //  System.out.println(pkt[0] + " + " + pkt[2]);
                        resizeArray();
                        seg[size++] = pkt[0];
                        resizeArray();
                        seg[size++] = pkt[2];

                    }
                } else {
                    //  System.out.println("first");
                    seg[size++] = pkt[0];
                    seg[size++] = pkt[2];
                }
            } else {
                start = i + 1;
                indexE = 0;
            }
        }
    }

    private void findLineSegments() {
        for (int j = 0; j < aux.length; j++) {
            Arrays.sort(aux);
            Arrays.sort(aux, aux[j].slopeOrder());
            recurssion();
        }
        this.segments = new LineSegment[(size / 2)];
        for (int i = 0; i < size - 1; i = i + 2) {
            LineSegment lineSeg = new LineSegment(seg[i], seg[i + 1]);
            if (i == 0)
                segments[i] = lineSeg;
            else
                segments[i / 2] = lineSeg;
        }

    }


    private void resizeArray() {
        if (size == seg.length) {
            Point[] copy = new Point[(seg.length * 2)];
            for (int i = 0; i < size; i++) {
                copy[i] = seg[i];
            }
            seg = copy;
        }

    }

    private boolean compareSlope(Point a, Point b) {
        boolean change = false;
        for (int i = 0; i < size - 1; i = i + 2) {
            /*
            System.out.print("SEG :");
            for (int j = 0; j < size; j++) {
                System.out.print(seg[j] + " ");
            }
            System.out.println();

             */
            if (a.compareTo(seg[i]) != 0 || b.compareTo(seg[i + 1]) != 0) {
                continue;
            }
            // System.out.println("A B " + a + " + " + b);
            Point[] pkt = new Point[4];
            pkt[0] = a;
            pkt[1] = b;
            pkt[2] = seg[i];
            pkt[3] = seg[i + 1];
            Arrays.sort(pkt);
            seg[i] = pkt[0];
            seg[i + 1] = pkt[3];
            change = true;
        }

        return change;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
}