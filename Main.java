/**
 * Console driver class to test model rules independently without a GUI window.
 */
public class Main {

    public static void main(String[] args) {
        Grid outbreakGrid = new Grid(50, 50);
        outbreakGrid.infectPerson(5, 5);

        for (int step = 1; step <= 10; step++) {
            System.out.println("--- Simulation Step: " + step + " ---");
            printBoard(outbreakGrid);
            System.out.println(); 
        }
    }

    public static void printBoard(Grid grid) {
        Person[][] array = grid.getArray();

        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[r].length; c++) {
                String status = array[r][c].getStatus();

                if (status.equals("INFECTED")) {
                    System.out.print("I ");
                } else if (status.equals("RECOVERED")) {
                    System.out.print("R ");
                } else {
                    System.out.print(". "); 
                }
            }
            System.out.println(); 
        }
    }
}
