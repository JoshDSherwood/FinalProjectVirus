import java.util.Random;

/**
 * Models the 2D grid world for the disease spread simulation.
 * Holds all people, tracks turns, and updates infection, recovery,
 * quarantine, and vaccination states each turn.
 */
public class Grid {

    // --- TUNABLE SIMULATION SETTINGS (Edit these to experiment) ---
    public static int startingInfectedPercent     = 8;     // % of grid infected at start (8 = 8%)
    public static double healthyInfectionProb     = 0.12;  // 12% chance per turn to infect a HEALTHY neighbor
    public static double recoveredInfectionProb   = 0.05;  // 5% chance per turn to reinfect a RECOVERED neighbor
    public static double recoveryProbability      = 0.06;  // 6% chance per turn for an INFECTED person to recover
    public static double quarantineTransitionProb = 0.01;  // 1% chance per turn for an INFECTED person to be quarantined
    public static double vaccinationProbability   = 0.02;  // 2% chance per turn for a HEALTHY person to get vaccinated
    public static int vaccinationStartTurn        = 20;    // Turn number when vaccines become available

    public int rows;
    public int cols;
    public Person[][] array;
    public int turnCount = 0; // Tracks total simulation turns

    /**
     * Creates a new grid with the given dimensions and populates it.
     */
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.array = new Person[rows][cols];

        setupRandomPopulation();
    }

    /**
     * Resets the grid and populates people at the beginning.
     * Only Healthy and Infected agents exist at the start.
     */
    public void setupRandomPopulation() {
        Random rand = new Random();
        turnCount = 0;

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                int roll = rand.nextInt(100);

                if (roll < startingInfectedPercent) {
                    this.array[r][c] = new Person("INFECTED");
                } else {
                    this.array[r][c] = new Person("HEALTHY");
                }
            }
        }
    }

    /**
     * Advances the simulation by one turn, updating vaccinations,
     * quarantines, recoveries, and neighbor-to-neighbor infections.
     */
    public void updateSimulation() {
        Random rand = new Random();
        turnCount++;

        String[][] nextStatuses = new String[rows][cols];

        // Copy current statuses first so updates happen simultaneously
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                nextStatuses[r][c] = array[r][c].getStatus();
            }
        }

        // Loop through every cell in the grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Person currentPerson = array[r][c];
                String currentStatus = currentPerson.getStatus();

                // 1. Check if healthy person gets vaccinated (after vaccinationStartTurn)
                if (turnCount >= vaccinationStartTurn && currentStatus.equals("HEALTHY")) {
                    if (rand.nextDouble() < vaccinationProbability) {
                        nextStatuses[r][c] = "VACCINATED";
                    }
                }

                // 2. If infected, check for quarantine, recovery, or spreading
                if (currentStatus.equals("INFECTED")) {
                    if (rand.nextDouble() < quarantineTransitionProb) {
                        nextStatuses[r][c] = "QUARANTINED";
                    } else if (rand.nextDouble() < recoveryProbability) {
                        nextStatuses[r][c] = "RECOVERED";
                    } else {
                        // Spread to Up, Down, Left, Right neighbors
                        int[] rowOffsets = {-1, 1, 0, 0};
                        int[] colOffsets = {0, 0, -1, 1};

                        for (int i = 0; i < 4; i++) {
                            int nr = r + rowOffsets[i];
                            int nc = c + colOffsets[i];

                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                                String neighborStatus = array[nr][nc].getStatus();

                                if (neighborStatus.equals("HEALTHY")) {
                                    if (rand.nextDouble() < healthyInfectionProb) {
                                        nextStatuses[nr][nc] = "INFECTED";
                                    }
                                } else if (neighborStatus.equals("RECOVERED")) {
                                    if (rand.nextDouble() < recoveredInfectionProb) {
                                        nextStatuses[nr][nc] = "INFECTED";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Apply all updated statuses to the Person objects
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                array[r][c].setStatus(nextStatuses[r][c]);
            }
        }
    }

    /**
     * Manually infects a person at a specific row and column.
     */
    public void infectPerson(int r, int c) {
        if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
            this.array[r][c].setStatus("INFECTED");
        }
    }

    /**
     * Returns the 2D array of Person objects.
     */
    public Person[][] getArray() {
        return this.array;
    }
}
