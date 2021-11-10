

import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.board = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                board[i][j] = tiles[i][j];
            }
        }

    }

    // string representation of this board

    public String toString() {

        StringBuilder bob = new StringBuilder();
        bob.append(board.length);
        for (int i = 0; i < board.length; i++) {
            bob.append("\n");
            for (int j = 0; j < board.length; j++) {
                bob.append(" " + board[i][j] + " ");
            }
        }
        return bob.toString();

    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (i + j == (board.length * 2) - 2) {
                    return hamming;
                }
                if (board[i][j] % board.length != 0) {
                    if (i != board[i][j] / board.length || j != board[i][j] % board.length - 1) {
                        hamming++;
                    }
                } else if (board[i][j] % board.length == 0) {
                    if (i != board[i][j] / board.length - 1 || j != board.length - 1) {
                        hamming++;
                    }
                }

            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0)
                    continue;
                if (board[i][j] % board.length != 0) {
                    manhattan = manhattan + Math.abs((board[i][j] / board.length) - i) + Math.abs((board[i][j] % board.length - 1) - j);
                } else if (board[i][j] % board.length == 0) {
                    manhattan = manhattan + Math.abs((board[i][j] / board.length - 1) - i) + Math.abs((board.length - 1) - j);
                }
            }
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (hamming() == 0)
            return true;
        return false;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board1 = (Board) y;
        return Arrays.deepEquals(board, board1.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row = 0;
        int col = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        Stack<Board> sasiedzi = new Stack<Board>();
        int saved = 0;
        if (row + 1 < board.length) {
            saved = board[row + 1][col];
            board[row][col] = saved;
            board[row + 1][col] = 0;
            sasiedzi.push(new Board(board));
            board[row][col] = 0;
            board[row + 1][col] = saved;
        }
        if (row - 1 >= 0) {
            saved = board[row - 1][col];
            board[row][col] = saved;
            board[row - 1][col] = 0;
            sasiedzi.push(new Board(board));
            board[row][col] = 0;
            board[row - 1][col] = saved;
        }
        if (col + 1 < board.length) {
            saved = board[row][col + 1];
            board[row][col] = saved;
            board[row][col + 1] = 0;
            sasiedzi.push(new Board(board));
            board[row][col] = 0;
            board[row][col + 1] = saved;
        }
        if (col - 1 >= 0) {
            saved = board[row][col - 1];
            board[row][col] = saved;
            board[row][col - 1] = 0;
            sasiedzi.push(new Board(board));
            board[row][col] = 0;
            board[row][col - 1] = saved;
        }
        return sasiedzi;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                twin[i][j] = board[i][j];
            }
        }
        if (twin[0][0] != 0 && twin[0][1] != 0) {
            int saved = twin[0][0];
            twin[0][0] = twin[0][1];
            twin[0][1] = saved;
        } else {
            int saved = twin[1][0];
            twin[1][0] = twin[1][1];
            twin[1][1] = saved;
        }
        Board decha = new Board(twin);

        return decha;


    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = new int[3][3];
        a[0][0] = 8;
        a[0][1] = 1;
        a[0][2] = 3;
        a[1][0] = 4;
        a[1][1] = 0;
        a[1][2] = 2;
        a[2][0] = 7;
        a[2][1] = 6;
        a[2][2] = 5;
        System.out.println((1 / 3) + " " + (1 % 3) + " = 1");
        System.out.println((2 / 3) + " " + (2 % 3) + " = 2");
        System.out.println((3 / 3) + " " + (3 % 3) + " = 3");
        System.out.println((4 / 3) + " " + (4 % 3) + " = 4");
        System.out.println((5 / 3) + " " + (5 % 3) + " = 5");
        System.out.println((6 / 3) + " " + (6 % 3) + " = 6");
        System.out.println((7 / 3) + " " + (7 % 3) + " = 7");
        System.out.println((8 / 3) + " " + (8 % 3) + " = 8");

        System.out.println(2 / 3 + " Wynik1");
        System.out.println(a.length + " L");
        Board deska = new Board(a);
        System.out.println(deska.toString());
        System.out.println(deska.dimension());
        System.out.println(deska.hamming() + " H");
        System.out.println(deska.manhattan() + " M");
        System.out.println(deska.isGoal());
        System.out.println(deska.neighbors());
        System.out.println(deska.twin());
    }

}