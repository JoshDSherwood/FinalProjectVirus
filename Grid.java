public class Grid {
    public int rows;
    public int cols;
    public Person[][] board;

    // Constructor matching your preferred style
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Person[rows][cols];

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = new Person("HEALTHY");
            }
        }
    }

    // Method to infect a specific person
    public void infectPerson(int r, int c) {
        if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
            this.board[r][c].setStatus("INFECTED");
        }
    }

 
    // Getter method for Main to print the board
    public Person[][] getBoard() {
        return this.board;
    }
}
