import java.util.*;

/**
 * Created by jackkates on 2/17/16.
 */
public class Main {

    public static void main(String[] args) {
       new Main().run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        int numberOfCases = sc.nextInt();

        for (int i = 1; i <= numberOfCases; i++) {
            int rows = sc.nextInt();
            int cols = sc.nextInt();

            Board board = new Board(rows, cols);
            System.out.println("Scenario #" + i + ":");
            board.printTour();
            System.out.println();
        }
    }

    class Board {
        int rows;
        int cols;

        Square[][] board;

        public Board(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;

            board = new Square[rows][cols];

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    board[row][col] = new Square(row, col);
                }
            }
        }

        public List<Square> getValidMoves(Square square) {
            int[] rowOffsets = new int[] {1, 1, -1, -1, 2, 2, -2, -2};
            int[] colOffsets = new int[] {2, -2, 2, -2, 1, -1, 1, -1};

            // For each of the 8 possible squares, add the legal ones to a list
            List<Square> legalMoves= new ArrayList<Square>();
            for (int i = 0; i < 8; i++) {
                int newRow = square.row + rowOffsets[i];
                int newCol = square.col + colOffsets[i];

                if (isValid(newRow, newCol)) {
                    legalMoves.add(board[newRow][newCol]);
                }
            }

            // Sort the legal moves list in lexicographic order
            Collections.sort(legalMoves);
            return legalMoves;
        }

        public void printTour() {
            // Start the solution from the top left square.
            List<Square> solution = new ArrayList<Square>();
            solution.add(board[0][0]);

            if (tourExists(solution)) {
                for (Square square: solution) {
                    System.out.print(square);
                }
            } else {
                System.out.print("impossible");
            }
            System.out.println();
        }

        public boolean tourExists(List<Square> squares) {
            if (squares.size() == rows * cols) {
                return true;
            } else {
                Square lastSquare = squares.get(squares.size() - 1);
                List<Square> validMoves = getValidMoves(lastSquare);

                for (Square nextSquare : validMoves) {
                    if (!squares.contains(nextSquare)) {
                        squares.add(nextSquare);
                        if (tourExists(squares)) return true;
                        squares.remove(squares.size() - 1);
                    }
                }

                return false;
            }
        }

        private boolean isValid(int row, int col) {
            return (row >= 0 && row < rows) && (col >= 0 && col < cols);
        }
    }

    class Square implements Comparable<Square> {
        int row;
        int col;

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int compareTo(Square sq) {
            // Chessboard Squares are ordered first by column, and then by row.
            // This imposes a lexicographic ordering on the squares when the column
            // is written as a letter.
            if (col == sq.col && row == sq.row) return 0;

            if (col < sq.col) return -1;

            if (col == sq.col && row < sq.row) return -1;

            return 1;
        }

        @Override
        public String toString() {
            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUV".toCharArray();

            // Print column as a letter + row as a number
            return alphabet[col] + String.valueOf(row + 1);
        }
    }
}
