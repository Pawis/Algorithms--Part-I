

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private Point[] seg;
    private final Point[] aux;
    private int size;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        this.aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (i != 0) {
                for (int j = 0; j < i; j++) {
                    if (points[i].compareTo(aux[j]) == 0)
                        throw new IllegalArgumentException();
                }
                aux[i] = points[i];
            } else {
                aux[i] = points[i];
            }
        }
        this.seg = new Point[10];
        this.size = 0;
        findLineSegments();
        /*
        for (int i = 0; i < seg.length; i++) {
            System.out.print(seg[i] + " ");
        }

         */
    }

    private void findLineSegments() {
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux.length; j++) {
                double ij = aux[i].slopeTo(aux[j]);
                for (int p = 0; p < aux.length; p++) {
                    double ip = aux[i].slopeTo(aux[p]);
                    for (int k = 0; k < aux.length; k++) {
                        double ik = aux[i].slopeTo(aux[k]);
                        if ((aux[j] == aux[p] || aux[j] == aux[k]) || aux[p] == aux[k])
                            continue;
                        if (ij == ip && ij == ik && ip == ik) {
                            Point[] pkt = new Point[4];
                            pkt[0] = aux[i];
                            pkt[1] = aux[j];
                            pkt[2] = aux[p];
                            pkt[3] = aux[k];
                            Arrays.sort(pkt);
                            if (size != 0) {
                                if (!compareSlope(pkt[0], pkt[3])) {
                                    resizeArray();
                                    seg[size++] = pkt[0];
                                    resizeArray();
                                    seg[size++] = pkt[3];
                                  /*  System.out.print("W srodku seg : ");
                                    for (int l = 0; l < size; l++) {
                                        System.out.print(seg[l] + " ");
                                    }
                                    System.out.println();

                                   */
                                }
                            } else {
                               // System.out.println("Pierwsze punkty w arrayu : " + pkt[0] + " + " + pkt[3]);
                                seg[size++] = pkt[0];
                                seg[size++] = pkt[3];

                            }
                        }
                    }
                }
            }
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


        private boolean compareSlope(Point a, Point b) {
            boolean change = false;
            for (int i = 0; i < size - 1; i = i + 2) {
                if (a.slopeTo(b) == seg[i].slopeTo(seg[i + 1])) {
                    //    if (a.slopeOrder().compare(seg[i], seg[i + 1]) != 0) {
                    if (a.compareTo(seg[i]) != 0 && b.compareTo(seg[i + 1]) != 0) {
                        continue;
                    }
                    Point[] pkt = new Point[4];
                    pkt[0] = a;
                    pkt[1] = b;
                    pkt[2] = seg[i];
                    pkt[3] = seg[i + 1];
                    Arrays.sort(pkt);
                    seg[i] = pkt[0];
                    seg[i + 1] = pkt[3];
                    // System.out.println("Co zostaje podmienione : " + pkt[0] + " + " + pkt[3]);
                    change = true;
                }
            }
            return change;
        }


    /*
    private boolean compareSlope(Point a, Point b) {
        boolean change = false;
        Point[] pkt = new Point[4];
        for (int i = 0; i < size - 1; i = i + 2) {
            if (a.slopeTo(b) == seg[i].slopeTo(seg[i + 1])) {
                // if (a.compareTo(seg[i]) != 0 && b.compareTo(seg[i + 1]) != 0) {
                //    if (a.slopeOrder().compare(seg[i], seg[i + 1]) != 0) {
                if (a.slopeOrder().compare(seg[i], seg[i + 1]) != 0) {
                    continue;
                }
                pkt[0] = a;
                pkt[1] = b;
                pkt[2] = seg[i];
                pkt[3] = seg[i + 1];
                Arrays.sort(pkt);
                seg[i] = pkt[0];
                seg[i + 1] = pkt[3];
                for (int j = 0; j < seg.length; j++) {
                    System.out.println(seg[i] + " ");
                }
                // System.out.pr}
                change = true;
            }
        }
        return change;
    }

     */

    private void resizeArray() {
        if (size == seg.length) {
            Point[] copy = new Point[(seg.length * 2)];
            for (int i = 0; i < size; i++) {
                copy[i] = seg[i];
            }
            seg = copy;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

}