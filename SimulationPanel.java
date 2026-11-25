import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom JPanel that draws the grid visually and supports clicking cells to set starting conditions.
 */
public class SimulationPanel extends JPanel {
    private Grid grid;

    public SimulationPanel(Grid grid) {
        this.grid = grid;
        setBackground(Color.WHITE);

        // Allow clicking cells to set starting conditions before running
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int cellWidth = getWidth() / grid.cols;
                int cellHeight = getHeight() / grid.rows;

                int c = e.getX() / cellWidth;
                int r = e.getY() / cellHeight;

                grid.infectPerson(r, c);
                repaint();
            }
        });
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
                    g.setColor(Color.LIGHT_GRAY);
                }

                g.fillRect(x, y, cellWidth - 1, cellHeight - 1);
            }
        }
    }
}
