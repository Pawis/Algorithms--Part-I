import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    // construct an empty set of points
    private Node root;
    private int size;

    public KdTree() {
        this.size = 0;
    }

    private static class Node {
        private final Point2D key;
        private Node left, right;
        private final RectHV rect;
        private final boolean dir;

        public Node(Point2D key, boolean dir, RectHV rect) {
            this.key = key;
            this.dir = dir;
            this.rect = rect;

        }

    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        else
            root = put(root, p, true, new RectHV(0, 0, 1, 1));

    }

    private Node put(Node x, Point2D key, boolean orient, RectHV recc) {
        if (x == null && orient == false) {
            // System.out.println("In Y " + key + recc.toString());
            size++;
            return new Node(key, true, recc);
        } else if (x == null && orient == true) {
            // System.out.println("In X " + key + recc.toString());
            size++;
            return new Node(key, false, recc);
        }
        if (x.dir) {
            if (x.key.compareTo(key) == 0)
                return x;
            double comp = Point2D.Y_ORDER.compare(key, x.key);
            // System.out.println(key + " - " + x.key + " = " + comp + " Y");
            if (comp < 0) {
                // System.out.println("LY" + x.key.toString() + x.dir);
                if (x.left == null) {
                    RectHV kwadrat = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.key.y());
                    recc = kwadrat;
                }
                // System.out.println(recc.toString());
                x.left = put(x.left, key, true, recc);
            } else if (comp >= 0) {
               //  System.out.println("RY" + x.key.toString() + x.dir);
                if (x.right == null) {
                    RectHV kwadrat = new RectHV(x.rect.xmin(), x.key.y(), x.rect.xmax(), x.rect.ymax());
                    recc = kwadrat;
                }
               //  System.out.println(recc.toString());
                x.right = put(x.right, key, true, recc);
            }
        } else {
            if (x.key.compareTo(key) == 0)
                return x;
            double comp = Point2D.X_ORDER.compare(key, x.key);
             // System.out.println(key + " - " + x.key + " = " + comp + " X");
            if (comp < 0) {
                // System.out.println("LX" + x.key.toString() + x.dir);
                if (x.left == null) {
                    RectHV kwadrat = new RectHV(x.rect.xmin(), x.rect.ymin(), x.key.x(), x.rect.ymax());
                    recc = kwadrat;
                }
                // System.out.println(recc.toString());
                x.left = put(x.left, key, false, recc);
            } else if (comp >= 0) {
                // System.out.println("RX" + x.key.toString() + x.dir);
                if (x.right == null) {
                    RectHV kwadrat = new RectHV(x.key.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
                    recc = kwadrat;
                }
                // System.out.println(recc.toString());
                x.right = put(x.right, key, false, recc);
            }
        }

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Node a = (get(root, p));
        if (a != null)
            return (get(root, p).key.compareTo(p) == 0);
        else return false;
    }

    private Node get(Node x, Point2D p) {
        //  StdDraw.setPenColor(StdDraw.GREEN);
        // StdDraw.setPenRadius(0.01);
        if (x == null)
            return null;
        // else x.key.draw();
        if (x.dir) {
            double comp = Point2D.Y_ORDER.compare(p, x.key);
            if (x.key.compareTo(p) == 0) {
             //   System.out.println(x.key + " R");
              //  System.out.println("dsa");
                return x;
            }
            if (comp < 0) {
              //  System.out.println(x.key + " 1");
               //  System.out.println("LF" + x.key.toString() + x.dir);
                if (x.left != null) {
                    //x.left.key.draw();
                    return get(x.left, p);
                }
            } else if (comp >= 0) {
                // System.out.println(x.key + " 2");
               //  System.out.println("RF" + x.key.toString()+ x.dir);
                if (x.right != null) {
                    // x.right.key.draw();
                    return get(x.right, p);
                }
            }  if (x.key.compareTo(p) == 0) {
               // System.out.println("asd");
               //  System.out.println(x.key + " R");
                return x;
            }
        } else {
            double comp = Point2D.X_ORDER.compare(p, x.key);
            if (x.key.compareTo(p) == 0) {
               // System.out.println(x.key + " R");
              //  System.out.println("dsa");
                return x;
            }
            if (comp < 0) {
               //  System.out.println(x.key + " 3");
               //  System.out.println("LT" + x.key.toString()+ x.dir);
                if (x.left != null) {
                    //  x.left.key.draw();
                    return get(x.left, p);
                }
            } else if (comp >= 0) {
                // System.out.println(x.key + " 4");
                // System.out.println("RT" + x.key.toString()+ x.dir);
                if (x.right != null) {
                    //  x.right.key.draw();
                    return get(x.right, p);
                }
            }
        }
         // System.out.println(x.key + " R");
        return x;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(root.key.x(), 1, root.key.x(), 0);

        square(root);
    }

    private void square(Node x) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.key.draw();
        if (x.dir) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            if (x.left != null)
                StdDraw.line(x.left.key.x(), 0, x.left.key.x(), x.key.y());
            if (x.right != null)
                StdDraw.line(x.right.key.x(), x.key.y(), x.right.key.x(), 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            if (x.right != null)
                if (x.key.x() < root.key.x())
                    StdDraw.line(x.key.x(), x.right.key.y(), root.key.x(), x.right.key.y());
                else
                    StdDraw.line(x.key.x(), x.right.key.y(), 1, x.right.key.y());
            if (x.left != null)
                if (x.key.x() > root.key.x())
                    StdDraw.line(x.key.x(), x.left.key.y(), root.key.x(), x.left.key.y());
                else
                    StdDraw.line(x.key.x(), x.left.key.y(), 0, x.left.key.y());
        }

        if (x.left != null) {
            square(x.left);
        } else if (x.right != null) {
            square(x.right);
        }
        if (x.right != null) {
            square(x.right);
        } else if (x.left != null) {
            square(x.left);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        else {
            Stack<Point2D> points = new Stack<>();
            return recPoints(root, rect, points);
        }
    }

    private Stack<Point2D> recPoints(Node x, RectHV rect, Stack<Point2D> p) {
        if (x == null)
            return p;
        // System.out.println(x.key + " Start");
        if (rect.contains(x.key)) {
            // System.out.println(x.key + " Push");
            p.push(x.key);
        }
        if (x.left != null) {
            if (rect.intersects(x.left.rect)) {
                // System.out.println(x.left.key.toString() + " L");
                recPoints(x.left, rect, p);
            }
        }
        if (x.right != null)
            if (rect.intersects(x.right.rect)) {
                // System.out.println(x.right.key.toString() + " R");
                recPoints(x.right, rect, p);
            }
        return p;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        else {
            if (isEmpty()) {
                return null;
            }
            return nearestP(root, p, root).key;
        }
    }

    private Node nearestP(Node x, Point2D p, Node c) {
         // System.out.println(x.key.distanceSquaredTo(p) + " x");
       //  System.out.println(x.rect.distanceSquaredTo(p) + " r");
         // System.out.println(c.key.distanceSquaredTo(p) + " c");
        if (x.key.distanceSquaredTo(p) < c.key.distanceSquaredTo(p)) {
            c = x;
        }
         // System.out.println(c.key.toString() + x.key.toString());
        if (x.dir) {
            if (p.y() < x.key.y()) {
                if ((x.left != null)) {
                   //  System.out.println(x.key.toString() + " / " + c.key.toString() + " 1 ");
                    c = nearestP(x.left, p, c);
                }
                if (x.right != null) {
                    if (x.right.rect.distanceSquaredTo(p) < c.key.distanceSquaredTo(p)) {
                        //  System.out.println(x.key.toString() + " / " + c.key.toString() + " 2 ");
                        c = nearestP(x.right, p, c);
                          // System.out.println(x.key.toString() + " / " + c.key.toString() + " 3 ");
                    } else return c;
                }
            } else if (p.y() >= x.key.y()) {
                if (x.right != null) {
                     // System.out.println(x.key.toString() + " / " + c.key.toString() + " 4 ");
                    c = nearestP(x.right, p, c);
                }
                if (x.left != null) {
                    if (x.left.rect.distanceSquaredTo(p) < c.key.distanceSquaredTo(p)) {
                        // System.out.println(x.key.toString() + " / " + c.key.toString() + " 5 ");
                        c = nearestP(x.left, p, c);
                        // System.out.println(x.key.toString() + " / " + c.key.toString() + " 6 ");
                    } else return c;
                }
            }
        } else {
            if (p.x() < x.key.x()) {
                if (x.left != null) {
                    // System.out.println(x.key.toString() + " / " + c.key.toString() + " 7 ");
                    c = nearestP(x.left, p, c);
                }
                if (x.right != null) {
                    if (x.right.rect.distanceSquaredTo(p) < c.key.distanceSquaredTo(p)) {
                        // System.out.println(x.key.toString() + " / " + c.key.toString() + " 8 ");
                        c = nearestP(x.right, p, c);
                        // System.out.println(x.key.toString() + " / " + c.key.toString() + " 9 ");
                    } else return c;
                }
            } else if (p.x() >= x.key.x()) {
                if (x.right != null) {
                    // System.out.println(x.key.toString() + " / " + c.key.toString() + " 10 ");
                    c = nearestP(x.right, p, c);
                    // System.out.println(x.key.toString() + "a" + c.key.toString());
                }
                if (x.left != null) {
                    if (x.left.rect.distanceSquaredTo(p) < c.key.distanceSquaredTo(p)) {
                       //  System.out.println(x.key.toString() + " / " + c.key.toString() + " 11 ");
                        c = nearestP(x.left, p, c);
                        // System.out.println(x.key.toString() + " / " + c.key.toString() + " 12 ");
                    } else return c;
                }
            }
        }
        // System.out.println(x.key.toString() + " / " + c.key.toString() + " 22 ");
        return c;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        // Point2D a = new Point2D(0.550000, 0.950000);
        // Point2D a = new Point2D(0.998046875, 0.931640625);
        // Point2D a = new Point2D(0.171875, 0.400390625);
        // Point2D a = new Point2D(0.427734375 , 0.419921875);
        // Point2D a = new Point2D(0.5390625, 0.033203125);
        // Point2D a = new Point2D(0.81, 0.30);
        // Point2D b = new Point2D(0.793893, 0.904508);
        //  Point2D c = new Point2D(0.024472, 0.654508);
        //  Point2D a = new Point2D(0.1640625 , 0.88671875); // Obok G
       // Point2D a = new Point2D(0.7, 0.426);
      //  Point2D a = new Point2D(0.5, 0.87);
       // System.out.println(kdtree.contains(a));
       // kdtree.draw();
        // System.out.println(kdtree.size());
        // System.out.println(kdtree.nearest(a));
        // System.out.println(kdtree.size());
         // System.out.println(kdtree.nearest(a));
        // RectHV rectang = new RectHV(0.681640625, 0.0546875, 1.001953125, 0.44921875);
        //  for (Point2D p : kdtree.range(rectang)) {
        //      // System.out.println(p.toString() + " S");
        //  }

        //  kdtree.aWay(new Point2D(0.793893, 0.095492));
/*
        KdTree tree = new KdTree();
        Point2D a = new Point2D(1.0, 1.0);
        Point2D b = new Point2D(0.5, 0.4);
        Point2D c = new Point2D(1.0, 1.0);
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        System.out.println(tree.size());

 */
        /*
        Point2D j = new Point2D(0.2, 0.4);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D f = new Point2D(0.9, 0.6);
        Point2D k = new Point2D(0.1, 0.1);
        tree.insert(f);
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.insert(k);
        tree.insert(j);
        System.out.println(tree.contains(f));
        System.out.println(tree.contains(a));
        System.out.println(tree.contains(b));
        System.out.println(tree.contains(c));
        System.out.println(tree.contains(d));

        tree.draw();
        tree.nearest(new Point2D(0.2, 0.4));

         */


    }
}