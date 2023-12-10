package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// import org.apache.poi.ss.usermodel.Color;

import game.Game;
import main.MyContent;
import main.MyGame;
import main.MyKeyboardListener;
import main.MyMouseHandler;
import main.MyPeriodicLoop;

public class OpeningPanel extends JPanel {
    private JTextField nameTextField;
    private JLabel nameLabel;
    private JLabel titleLabel;
    private static JFrame frame;

    public OpeningPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600)); // Set a preferred size for the panel

        // Create a JLabel to display the background image
        ImageIcon backgroundImage = new ImageIcon("resources/space_b.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);
        // Create a title label
        titleLabel = new JLabel("Welcome to Space Race Game!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        // Create a sub-panel for the text input and button
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false); // Make the input panel transparent

        nameLabel = new JLabel("What is your name?");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        inputPanel.add(nameLabel, BorderLayout.EAST);

        nameTextField = new JTextField(10);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 30); // Create a font with a font size of 16
        nameTextField.setFont(textFieldFont); // Set the font for the text field

        inputPanel.add(nameTextField, BorderLayout.EAST);

        backgroundLabel.add(inputPanel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for the image
        ImageIcon centerImage = new ImageIcon("resources/shuttle_T.jpg"); // Replace with your image file path
        JLabel imageLabel = new JLabel(centerImage);
        imagePanel.add(imageLabel);
        inputPanel.add(imagePanel); // Add the image panel to the input panel

        Font buttonFont = new Font("Arial", Font.PLAIN, 30);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical space

        JButton insructionsButton = new JButton("Game Insructions");
        insructionsButton.setFont(buttonFont);
        insructionsButton.setBackground(new Color(255, 115, 31));

        insructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new InstructionsPanel();
                InstructionsPanel.showInstructions();
            }
        });

       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout for buttonPanel


        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);

        JButton startGameButton = new JButton("Start Game!");

        startGameButton.setFont(buttonFont);
        startGameButton.setBackground(new Color(255, 115, 31));

        buttonPanel.add(insructionsButton);
        buttonPanel.add(startGameButton);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredName = nameTextField.getText();
                if (enteredName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You must enter a name.");
                } else {
                    MyGame game = new MyGame(enteredName);
                    game.setGameContent(new MyContent());
                    MyPeriodicLoop periodicLoop = new MyPeriodicLoop();
                    game.setTimer();
                    periodicLoop.setContent(game.getContent());
                    game.setPeriodicLoop(periodicLoop);
                    game.setMouseHandler(new MyMouseHandler());
                    game.setKeyboardListener(new MyKeyboardListener());

                    // Close Opening Panel
                    frame.dispose();
                    // Start our game :)
                    game.initGame();
                    // Game.audioPlayer().play("resources/audio/audio_sample.wav", 0);
                    Game.audioPlayer().play(Game.gameMusicPath, 0);

                }
            }

        });
    }

    public static void showOpeningPanel() throws Exception {

        frame = new JFrame("My Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        OpeningPanel openingPanel = new OpeningPanel();
        frame.add(openingPanel);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);

    }
}
