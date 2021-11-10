import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {

        this.set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.set.size() == 0;
    }

    // number of points in the set
    public int size() {
        return this.set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        this.set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return this.set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D a : this.set)
            a.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Stack<Point2D> points = new Stack<>();
        for (Point2D a : this.set)
            if (rect.contains(a))
                points.push(a);

        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (this.set.isEmpty())
            return null;
        Point2D nearest = this.set.last();
        for (Point2D a : this.set) {
            if (a.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
                nearest = a;
        }
        return nearest;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET set = new PointSET();
        set.insert(new Point2D(0.2, 0.5));
        set.insert(new Point2D(0.3, 0.4));
        set.insert(new Point2D(0.4, 0.6));
        set.insert(new Point2D(0.5, 0.7));
        set.draw();
    }
}