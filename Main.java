import java.util.ArrayList;
public class Main {

public static void main(String[] args) {

// 1. Initialize the grid (10 rows by 10 columns)

Grid outbreakGrid = new Grid(10, 10);

// 2. Set starting conditions (Patient Zero in the center)

outbreakGrid.infectPerson(5, 5);


// 3. Run a simulation loop for 3 steps

for (int step = 1; step <= 10; step++) {

System.out.println("--- Simulation Step: " + step + " ---");

// Render the current state of the board

printBoard(outbreakGrid);

System.out.println(); // Blank line for spacing

}

}


// Helper method to print the grid to the console

public static void printBoard(Grid grid) {

Person[][] board = grid.getBoard();

for (int r = 0; r < board.length; r++) {

for (int c = 0; c < board[r].length; c++) {

String status = board[r][c].getStatus();

if (status.equals("INFECTED")) {

System.out.print("I ");

} else if (status.equals("RECOVERED")) {

System.out.print("R ");

} else {

System.out.print(". "); // Healthy / Susceptible

}

}

System.out.println(); // New line at the end of each row

}

}

} 
