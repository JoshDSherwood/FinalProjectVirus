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
    private JButton resetButton;

    public SimulationFrame() {
        setTitle("Virus Outbreak Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        grid = new Grid(50, 50);

        panel = new SimulationPanel(grid);
        stepButton = new JButton("Next Step");
        resetButton = new JButton("Reset");

        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JLabel healthyLabel = new JLabel("■ Healthy");
        healthyLabel.setForeground(Color.GRAY);

        JLabel infectedLabel = new JLabel("■ Infected");
        infectedLabel.setForeground(Color.RED);

        JLabel recoveredLabel = new JLabel("■ Recovered");
        recoveredLabel.setForeground(Color.BLUE);

        JLabel vaccinatedLabel = new JLabel("■ Vaccinated");
        vaccinatedLabel.setForeground(Color.GREEN);

        JLabel quarantinedLabel = new JLabel("■ Quarantined");
        quarantinedLabel.setForeground(Color.ORANGE);

        legendPanel.add(healthyLabel);
        legendPanel.add(infectedLabel);
        legendPanel.add(recoveredLabel);
        legendPanel.add(vaccinatedLabel);
        legendPanel.add(quarantinedLabel);

        JPanel controlPanel = new JPanel();
        controlPanel.add(resetButton);
        controlPanel.add(stepButton);

        setLayout(new BorderLayout());
        add(legendPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.resetGrid();
                panel.repaint();
            }
        });

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.updateSimulation();
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
