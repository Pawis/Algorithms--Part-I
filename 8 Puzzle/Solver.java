import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    // private final MinPQ<SearchNode> priorQ;
    // private final MinPQ<SearchNode> priorQTwin;

    // find a solution to the initial board (using the A* algorithm)
    private SearchNode finalNoda;

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        /*
        this.priorQ = new MinPQ<>();
        this.priorQTwin = new MinPQ<>();
        Board decha = initial;
        SearchNode sortA = new SearchNode(decha, null, 0);
        SearchNode sortTwin = new SearchNode(decha.twin(), null, 0);
        priorQ.insert(sortA);
        priorQTwin.insert(sortTwin);

         */
        findGoal(initial);

    }

    private void findGoal(Board dec) {

        MinPQ<SearchNode> priorQ = new MinPQ<>();
        MinPQ<SearchNode> priorQTwin = new MinPQ<>();
        Board decha = dec;
        SearchNode sortA = new SearchNode(decha, null, 0);
        SearchNode sortTwin = new SearchNode(decha.twin(), null, 0);
        priorQ.insert(sortA);
        priorQTwin.insert(sortTwin);

        while (!priorQ.min().deska.isGoal()) {
            if (priorQTwin.min().deska.isGoal()) {
                finalNoda = null;
                break;
            }
            SearchNode mother = priorQ.min();
            SearchNode motherTwin = priorQTwin.min();
            priorQ.delMin();
            priorQTwin.delMin();
            for (Board a : mother.deska.neighbors()) {
                if (mother.moves > 1) {
                    if (a.equals(mother.previousNoda.deska)) {
                        continue;
                    }
                }
                SearchNode newNoda = new SearchNode(a, mother, mother.moves + 1);
                priorQ.insert(newNoda);
            }
            for (Board a : motherTwin.deska.neighbors()) {
                if (motherTwin.moves > 1) {
                    if (a.equals(mother.previousNoda.deska)) {
                        continue;
                    }
                }
                SearchNode newNodaTwin = new SearchNode(a, motherTwin, motherTwin.moves + 1);
                priorQTwin.insert(newNodaTwin);
            }

        }
        if (priorQ.min().deska.isGoal()) {
            finalNoda = priorQ.min();
        }


    }

    /*
    private boolean findGoal() {
        while (priorQ.min().deska.isGoal() != true) {
            if (priorQTwin.min().deska.isGoal() == true || priorQ.min().deska.isGoal() == true)
                break;
            SearchNode mother = priorQ.min();
            SearchNode motherTwin = priorQTwin.min();
            //  System.out.println("Matka " + priorQ.min().deska.toString() + " Min noda " + "Moves : " + priorQ.min().moves);
            priorQ.delMin();
            priorQTwin.delMin();
            // System.out.println("Prior : " + mother.priority);
            // System.out.println("Moves : " + mother.moves);
            // System.out.println("Man : " + mother.deska.manhattan());
            // System.out.println("SASIEDZI ");
            for (Board a : mother.deska.neighbors()) {
                if (mother.moves > 1 && a.equals(mother.deska)) {
                    // System.out.println(mother.previousNoda.deska.toString() + " ZLA " + a.toString());
                    continue;
                }
                SearchNode newNoda = new SearchNode(a, mother, mother.moves + 1);
                // System.out.println(newNoda.deska.toString() + " nowa noda " + newNoda.priority + " + " + newNoda.moves + " + " + newNoda.deska.manhattan() + " + Ham :" + newNoda.deska.hamming());
                priorQ.insert(newNoda);
            }

            for (Board a : motherTwin.deska.neighbors()) {
                if (motherTwin.moves > 1 && a.equals(motherTwin.deska)) {
                    continue;
                }
                SearchNode newNodaTwin = new SearchNode(a, motherTwin, motherTwin.moves + 1);
                priorQTwin.insert(newNodaTwin);
            }

        }
        if (priorQTwin.min().deska.isGoal() == true)
            return false;
        // System.out.println(priorQ.min().deska.toString() + " Org ");
        // System.out.println(priorQTwin.min().deska.toString() + " Twin ");
        return true;
    }

     */

    private class SearchNode implements Comparable<SearchNode> {
        private final Board deska;
        private final SearchNode previousNoda;
        private final int priority;
        private final int moves;

        public SearchNode(Board initial, SearchNode noda, int moves) {
            this.deska = initial;
            this.previousNoda = noda;
            this.moves = moves;
            this.priority = deska.manhattan() + this.moves;

        }

        public String toString() {
            return deska.toString() + priority;
        }

        public int compareTo(SearchNode a) {
            if (this.priority > a.priority)
                return 1;
            if (this.priority < a.priority)
                return -1;
            if (this.priority == a.priority && this.moves > a.moves)
                return -1;
            if (this.priority == a.priority && this.moves < a.moves)
                return 1;

            else return 0;
        }

    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (finalNoda == null)
            return false;
        return finalNoda.deska.isGoal();

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        else {
            return finalNoda.moves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> solution = new Stack<>();
        SearchNode save = finalNoda;
        solution.push(save.deska);
        while (save.previousNoda != null) {
            if (save.previousNoda.moves < save.moves) {
                save = save.previousNoda;
                solution.push(save.deska);
            }
        }
        return solution;

    }

    // test client (see below)
    public static void main(String[] args) {
        /*
        int[][] a = new int[2][2];
        a[0][0] = 0;
        a[0][1] = 1;
        a[1][0] = 2;
        a[1][1] = 3;
        Board deska = new Board(a);
        Solver solv = new Solver(deska);
        System.out.println(solv.isSolvable());
        System.out.println(solv.moves());
        System.out.println(solv.solution());

         */

    }


}