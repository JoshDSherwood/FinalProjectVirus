import java.util.Random;

public class Grid {
    public int rows;
    public int cols;
    public Person[][] array; 

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.array = new Person[rows][cols];
        
        // Automatically populate the grid with all 5 statuses when created
        setupRandomPopulation();
    }

    /**
     * Resets the grid and randomly populates people with a mix of all 5 statuses.
     */
    public void setupRandomPopulation() {
        Random rand = new Random();

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                // Randomly pick one of the 5 statuses for each cell
                int choice = rand.nextInt(5); // Generates 0, 1, 2, 3, or 4
                
                if (choice == 0) {
                    this.array[r][c] = new Person("INFECTED");     // Red
                } else if (choice == 1) {
                    this.array[r][c] = new Person("RECOVERED");    // Blue
                } else if (choice == 2) {
                    this.array[r][c] = new Person("VACCINATED");   // Green
                } else if (choice == 3) {
                    this.array[r][c] = new Person("QUARANTINED");  // Orange
                } else {
                    this.array[r][c] = new Person("HEALTHY");      // Light Gray
                }
            }
        }
    }

    /**
     * Updates the simulation by spreading infection to adjacent healthy neighbors.
     */
    public void updateSimulation() {
        String[][] nextStatuses = new String[rows][cols];
        
        // Copy current statuses first
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                nextStatuses[r][c] = array[r][c].getStatus();
            }
        }

        // Check every cell for infection spread
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (array[r][c].getStatus().equals("INFECTED")) {
                    
                    // Define neighbor directions (Up, Down, Left, Right)
                    int[] rowOffsets = {-1, 1, 0, 0};
                    int[] colOffsets = {0, 0, -1, 1};

                    for (int i = 0; i < 4; i++) {
                        int nr = r + rowOffsets[i];
                        int nc = c + colOffsets[i];

                        // Check if neighbor is inside grid bounds
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            // If neighbor is healthy, turn them infected!
                            if (array[nr][nc].getStatus().equals("HEALTHY")) {
                                nextStatuses[nr][nc] = "INFECTED";
                            }
                        }
                    }
                }
            }
        }

        // Apply the updated statuses back to your Person objects
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                array[r][c].setStatus(nextStatuses[r][c]);
            }
        }
    }

    public void infectPerson(int r, int c) {
        if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
            this.array[r][c].setStatus("INFECTED");
        }
    }

    public Person[][] getArray() {
        return this.array;
    }
}
