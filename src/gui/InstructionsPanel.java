package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructionsPanel extends JPanel {

    public InstructionsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 600)); // Set the preferred size for the panel

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(0, 0, 0, 10); // Add left padding
        gbc1.gridx = 0;

        // Title Label
        JLabel titleLabel = new JLabel("Game Instructions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel to hold image and sentence components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1)); // 3 rows, one column

        // First line with image and sentence
        JPanel line1 = new JPanel(new BorderLayout());
        ImageIcon image1 = new ImageIcon("resources/arrows.png"); // Replace with your image file path
        JLabel imageLabel1 = new JLabel(image1);
        line1.add(imageLabel1, BorderLayout.WEST);

        JLabel sentence1 = new JLabel("<html>  &nbsp&nbsp   Use Keboard Arrows to move your  <br> &nbsp&nbsp Spaceship </html>");
        sentence1.setFont(new Font("Arial", Font.PLAIN, 16));
        line1.add(sentence1, BorderLayout.CENTER);

        contentPanel.add(line1);

        // Second line with image and sentence
        JPanel line2 = new JPanel(new BorderLayout());
        ImageIcon image2 = new ImageIcon("resources/asteroid_small.png"); // Replace with your image file path
        JLabel imageLabel2 = new JLabel(image2);
        line2.add(imageLabel2, BorderLayout.WEST);

        JLabel sentence2 = new JLabel("<html> &nbsp&nbsp Catch the \"Timer Astroid\" and get 10 more <br> &nbsp&nbsp seconds. </html>");
        sentence2.setFont(new Font("Arial", Font.PLAIN, 16));
        line2.add(sentence2, BorderLayout.CENTER);

        contentPanel.add(line2);

        add(contentPanel, BorderLayout.CENTER);

        // Second line with image and sentence
        JPanel line3 = new JPanel(new BorderLayout());
        ImageIcon image3 = new ImageIcon("resources/freezer.jpg"); // Replace with your image file path
        JLabel imageLabel3 = new JLabel(image3);
        line3.add(imageLabel3, BorderLayout.WEST);

        JLabel sentence3 = new JLabel("<html>  &nbsp&nbsp  Catch the \"Freeze image\" and the Asteroids  <br> &nbsp&nbsp  will be Frozen! </html>");
        sentence3.setFont(new Font("Arial", Font.PLAIN, 16));
        line3.add(sentence3, BorderLayout.CENTER);

        contentPanel.add(line3);

        add(contentPanel, BorderLayout.CENTER);

        // "Got it" Button
        JButton gotItButton = new JButton("Got it");
        gotItButton.setBackground(new Color(255, 115, 31));
        gotItButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the InstructionPanel when the button is clicked
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(InstructionsPanel.this);
                frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(gotItButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void showInstructions() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Instruction Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new InstructionsPanel());
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }
}
