package io.github.dug22.imageshield;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ImageShieldGUI {

    public ImageShieldGUI() {
        LogsUtils.createLogsFile();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }
        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledImage);
        int HEIGHT = 600;
        int WIDTH = 800;
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setTitle("Image Shield");

        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(3, 1));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("🛡 Image Shield");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 32));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headerFootnoteLabel = new JLabel("Keep your image files safe!");
        headerFootnoteLabel.setFont(new Font("Serif", Font.BOLD, 16));
        headerFootnoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(headerLabel);
        headerPanel.add(headerFootnoteLabel);
        frame.add(headerPanel);
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 40));
        container.setOpaque(false);

        JPanel encryptPanel = new JPanel(new BorderLayout());
        encryptPanel.setBackground(Color.LIGHT_GRAY);
        encryptPanel.setPreferredSize(new Dimension(200, 81));
        JLabel encryptLabel = new JLabel("\uD83D\uDD12 Encrypt Image", SwingConstants.CENTER);
        encryptLabel.setFont(new Font("Serif", Font.PLAIN, 21));
        encryptPanel.add(encryptLabel, BorderLayout.CENTER);

        JPanel decryptPanel = new JPanel(new BorderLayout());
        decryptPanel.setBackground(Color.LIGHT_GRAY);
        decryptPanel.setPreferredSize(new Dimension(200, 81));
        JLabel decryptLabel = new JLabel("\uD83D\uDD13 Decrypt Image", SwingConstants.CENTER);
        decryptLabel.setFont(new Font("Serif", Font.PLAIN, 21));
        decryptPanel.add(decryptLabel, BorderLayout.CENTER);

        JPanel viewLogsPanel = new JPanel(new BorderLayout());
        viewLogsPanel.setBackground(Color.LIGHT_GRAY);
        viewLogsPanel.setPreferredSize(new Dimension(200, 81));
        JLabel viewLogsLabel = new JLabel("\uD83D\uDCCB View Logs", SwingConstants.CENTER);
        viewLogsLabel.setFont(new Font("Serif", Font.PLAIN, 21));
        viewLogsPanel.add(viewLogsLabel, BorderLayout.CENTER);

        container.add(encryptPanel);
        container.add(decryptPanel);
        container.add(viewLogsPanel);


        encryptPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                JTextField textField = new JTextField(20);
                JPanel customPanel = new JPanel();
                customPanel.setLayout(new BorderLayout());
                customPanel.add(fileChooser, BorderLayout.CENTER);
                JPanel textPanel = new JPanel();
                textPanel.add(new JLabel("Enter Password:"));
                textPanel.add(textField);
                customPanel.add(textPanel, BorderLayout.SOUTH);
                int result = JOptionPane.showOptionDialog(
                        frame,
                        customPanel,
                        "Encrypt Image",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new String[]{"Encrypt", "Cancel"},
                        "Encrypt"
                );

                if (result == JOptionPane.OK_OPTION) {
                    CryptoUtils.processFile(1, fileChooser.getSelectedFile().getPath(), textField.getText(), fileChooser);
                }
            }
        });

        decryptPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                JTextField textField = new JTextField(20);
                JPanel customPanel = new JPanel();
                customPanel.setLayout(new BorderLayout());
                customPanel.add(fileChooser, BorderLayout.CENTER);
                JPanel textPanel = new JPanel();
                textPanel.add(new JLabel("Enter Password:"));
                textPanel.add(textField);
                customPanel.add(textPanel, BorderLayout.SOUTH);
                int result = JOptionPane.showOptionDialog(
                        frame,
                        customPanel,
                        "Decrypt",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new String[]{"Decrypt Image", "Cancel"},
                        "Decrypt"
                );

                if (result == JOptionPane.OK_OPTION) {
                    CryptoUtils.processFile(2, fileChooser.getSelectedFile().getPath(), textField.getText(), fileChooser);
                }
            }
        });

        viewLogsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LogsUtils.openLogsFile();
            }
        });

        frame.add(container);
        frame.setVisible(true);
    }
}
