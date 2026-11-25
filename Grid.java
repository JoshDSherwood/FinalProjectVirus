/**
 * Models the 2D grid world for the disease spread simulation.
 * Starts entirely healthy so the user can manually select starting infection points.
 */
public class Grid {

    // --- TUNABLE SIMULATION SETTINGS ---
    public static double healthyInfectionProb     = 0.12;
    public static double recoveredInfectionProb   = 0.05;
    public static double recoveryProbability      = 0.06;
    public static double quarantineTransitionProb = 0.01;
    public static double vaccinationProbability   = 0.02;
    public static int vaccinationStartTurn        = 20;

    public int rows;
    public int cols;
    public Person[][] array;
    public int turnCount = 0;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.array = new Person[rows][cols];
        setupCleanPopulation(); // Starts with all healthy people
    }

    /**
     * Initializes the grid with entirely healthy individuals.
     */
    public void setupCleanPopulation() {
        turnCount = 0;

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                this.array[r][c] = new Person("HEALTHY");
            }
        }
    }

    public void resetGrid() {
        setupCleanPopulation();
    }

    public void updateSimulation() {
        turnCount++;

        String[][] nextStatuses = new String[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                nextStatuses[r][c] = array[r][c].getStatus();
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Person currentPerson = array[r][c];
                String currentStatus = currentPerson.getStatus();

                // 1. Check if healthy person gets vaccinated (after vaccinationStartTurn)
                if (turnCount >= vaccinationStartTurn && currentStatus.equals("HEALTHY")) {
                    if (RandomFactory.nextDouble() < vaccinationProbability) {
                        nextStatuses[r][c] = "VACCINATED";
                    }
                }

                // 2. If infected, check for quarantine, recovery, or spreading
                if (currentStatus.equals("INFECTED")) {
                    if (RandomFactory.nextDouble() < quarantineTransitionProb) {
                        nextStatuses[r][c] = "QUARANTINED";
                    } else if (RandomFactory.nextDouble() < recoveryProbability) {
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
                                    if (RandomFactory.nextDouble() < healthyInfectionProb) {
                                        nextStatuses[nr][nc] = "INFECTED";
                                    }
                                } else if (neighborStatus.equals("RECOVERED")) {
                                    if (RandomFactory.nextDouble() < recoveredInfectionProb) {
                                        nextStatuses[nr][nc] = "INFECTED";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                array[r][c].setStatus(nextStatuses[r][c]);
            }
        }
    }

    /**
     * Manually infects a person at a specific row and column (Guard statement included).
     */
    public void infectPerson(int r, int c) {
        // Guard statement: return early if out of bounds
        if (r < 0 || r >= this.rows || c < 0 || c >= this.cols) {
            return;
        }
        this.array[r][c].setStatus("INFECTED");
    }

    public Person[][] getArray() {
        return this.array;
    }
}
