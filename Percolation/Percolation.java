import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private int count;
    private final WeightedQuickUnionUF uf;
    // private QuickFindUF uf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.count = 0;
        uf = new WeightedQuickUnionUF((n * n) + 2);
        // this.uf = new QuickFindUF((n * n) + 2);

        this.grid = new int[n + 2][n + 2];
        int k = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                k++;
                grid[i][j] = -k;
            }
        }
        grid[0][(grid.length - 1) / 2] = (n * n) + 1;
    }

    // opens the parent (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            grid[row][col] = Math.abs(grid[row][col]);
            count++;
        } else
            return;
        if (row - 1 == 0) {
            uf.union(grid[row][col], grid[0][(grid.length - 1) / 2]);
        }
        if (row + 2 == grid.length) {
            uf.union(grid[row][col], grid[grid.length - 1][(grid.length - 1) / 2]);
        }
        if (row + 1 <= grid.length - 2)
            if (isOpen(row + 1, col)) {
                uf.union(grid[row][col], grid[row + 1][col]);
            }
        if (row - 1 > 0)
            if (isOpen(row - 1, col)) {
                uf.union(grid[row][col], grid[row - 1][col]);
            }
        if (col + 1 <= grid.length - 2)
            if (isOpen(row, col + 1)) {
                uf.union(grid[row][col], grid[row][col + 1]);
            }
        if (col - 1 > 0)
            if (isOpen(row, col - 1)) {
                uf.union(grid[row][col], grid[row][col - 1]);
            }
    }

    // is the parent (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[row][col] > 0;
    }

    // is the parent (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        if (grid[row][col] < 0)
            return false;
        else
            return uf.find(grid[row][col]) == uf.find(grid[0][(grid.length - 1) / 2]);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(grid[0][(grid.length - 1) / 2]) == uf.find(grid[grid.length - 1][(grid.length - 1) / 2]);

    }

    private void validate(int row, int col) {
        if ((col < 1 || row < 1) && (row > grid.length - 2 || col > grid.length - 2))
            throw new IllegalArgumentException();
    }

    /*private boolean outOfBound(int row, int col) {
        if (row + 1 > grid.length - 2) {
            return false;
        }
        if (col + 1 > grid.length - 2) {
            return false;
        }
        if (row - 1 < 1) {
            return false;
        }
        if (col - 1 < 1) {
            return false;
        }
        return true;
    }

     */





   /* public void print() {

        //System.out.println(isFull(3, 4));
        //System.out.println(uf.find(grid[3][4]));
        System.out.println(grid[1].length);

        for (int i = 0; i < grid.length; i++) {
            System.out.println("");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
                System.out.print("   ");
            }
        }
    }

    */


    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        perc.open(-1, 1);
        perc.open(1, 5);
        System.out.println(perc.isFull(1, 5));
    }
}