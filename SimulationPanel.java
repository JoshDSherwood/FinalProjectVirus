import javax.swing.*;
import java.awt.*;

/**
 * Custom JPanel that draws the grid visually on the screen with 5 status colors.
 */
public class SimulationPanel extends JPanel {
    private Grid grid;

    public SimulationPanel(Grid grid) {
        this.grid = grid;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Person[][] array = grid.getArray();
        int rows = grid.rows;
        int cols = grid.cols;

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = c * cellWidth;
                int y = r * cellHeight;

                // Pick color based on person's status (5 distinct colors)
                String status = array[r][c].getStatus();
                if (status.equals("INFECTED")) {
                    g.setColor(Color.RED);
                } else if (status.equals("RECOVERED")) {
                    g.setColor(Color.BLUE);
                } else if (status.equals("VACCINATED")) {
                    g.setColor(Color.GREEN);
                } else if (status.equals("QUARANTINED")) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.LIGHT_GRAY); // Default for HEALTHY
                }

                // Fill the cell and draw a border around it
                g.fillRect(x, y, cellWidth - 1, cellHeight - 1);
            }
        }
    }
}
