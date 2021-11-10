
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double conf = 1.96;
    private final double[] openSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }
        this.openSites = new double[trials];
        Percolation proc = new Percolation(n);
        while (trials > 1) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            proc.open(row, col);
            if (proc.percolates()) {
                openSites[trials - 1] = 1.0 * proc.numberOfOpenSites() / (n * n);
                proc = new Percolation(n);
                trials--;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (conf * stddev()) / (Math.sqrt(openSites.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (conf * stddev()) / (Math.sqrt(openSites.length));
    }


    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percs = new PercolationStats(n, t);
        System.out.println("mean = " + percs.mean());
        System.out.println("stddev + " + percs.stddev());
        System.out.println("95% confidence interval = [" + percs.confidenceLo() + ", " + percs.confidenceHi() + "]");


    }

}