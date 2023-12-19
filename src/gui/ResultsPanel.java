package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import game.Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultsPanel extends JPanel {

    private static JFrame frame;
    private Color orange = new Color(255, 115, 31);

    public ResultsPanel(String[][] results) {
        
        setLayout(new BorderLayout());

        // Create a title label
        JLabel titleLabel = new JLabel("Space Race Results");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Create a table to display the results
        JTable table = new JTable(new DefaultTableModel(results, new String[] { "Name", "Result" }));
        table.setFillsViewportHeight(true);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Center the table
        add(tablePanel, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(orange);
        JButton restartGameButton = new JButton("Restart Game");
        restartGameButton.setBackground(orange);
        buttonsPanel.add(exitButton);
        buttonsPanel.add(restartGameButton);

        // Add buttons below the table
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for the buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        restartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Game.audioPlayer().stop();
                    OpeningPanel.showOpeningPanel();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void showResultPanel(String[][] resultsData) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
             frame = new JFrame("Space Race");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ResultsPanel resultsPanel = new ResultsPanel(resultsData);
                frame.add(resultsPanel);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}
