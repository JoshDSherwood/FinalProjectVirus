import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application window containing the grid view, color legend, and control buttons.
 */
public class SimulationFrame extends JFrame {
    private Grid grid;
    private SimulationPanel panel;
    private JButton stepButton;

    public SimulationFrame() {
        setTitle("Outbreak Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        // Initialize your model and start with 1 infected person at (5, 5)
        grid = new Grid(50, 50);
        grid.infectPerson(5, 5);

        // Initialize UI components
        panel = new SimulationPanel(grid);
        stepButton = new JButton("Next Step");

        // 1. Create the Color Legend Panel
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JLabel healthyLabel = new JLabel("■ Healthy");
        healthyLabel.setForeground(Color.GRAY); // Darker gray so it's easy to read against a white background

        JLabel infectedLabel = new JLabel("■ Infected");
        infectedLabel.setForeground(Color.RED);

        JLabel recoveredLabel = new JLabel("■ Recovered");
        recoveredLabel.setForeground(Color.BLUE);

        JLabel vaccinatedLabel = new JLabel("■ Vaccinated");
        vaccinatedLabel.setForeground(Color.GREEN);

        JLabel quarantinedLabel = new JLabel("■ Quarantined");
        quarantinedLabel.setForeground(Color.ORANGE);

        // Add labels to the legend panel
        legendPanel.add(healthyLabel);
        legendPanel.add(infectedLabel);
        legendPanel.add(recoveredLabel);
        legendPanel.add(vaccinatedLabel);
        legendPanel.add(quarantinedLabel);

        // Control panel for the button
        JPanel controlPanel = new JPanel();
        controlPanel.add(stepButton);

        // 2. Set layout and add panels to the frame
        setLayout(new BorderLayout());
        add(legendPanel, BorderLayout.NORTH);   // Puts the color legend at the top
        add(panel, BorderLayout.CENTER);        // Puts your 50x50 grid in the middle
        add(controlPanel, BorderLayout.SOUTH);  // Puts the button at the bottom

        // Button action listener to advance simulation and redraw UI
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Update the grid data (spreads the infection)
                grid.updateSimulation();
                
                // 2. Redraw the panel so the new colors appear on screen
                panel.repaint(); 
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulationFrame().setVisible(true);
            }
        });
    }
}
