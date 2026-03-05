package io.github.dug22.imageshield;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Arrays;

public class CryptoUtils {

    public static void processFile(int mode, String filePath, String password, JFileChooser fileChooser) {
        try {

            if (!filePath.endsWith(".png") && !filePath.endsWith(".jpg") && !filePath.endsWith(".jpeg")) {
                JOptionPane.showMessageDialog(null, "You can only encrypt or decrypt image files! (extensions include: .png, .jpg, or .jpeg");
                return;

            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must type a password!");
                return;
            }

            File imageFile = new File(filePath);
            byte[] imageFileContent = Files.readAllBytes(imageFile.toPath());
            byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            keyBytes = sha256.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(mode, secretKey);
            byte[] outputData = cipher.doFinal(imageFileContent);
            Files.write(imageFile.toPath(), outputData);
            if(SystemTray.isSupported()){
                SystemTray tray = SystemTray.getSystemTray();
                TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("images/icon.png"), "Image Shield Alert");
                icon.setImageAutoSize(true);
                try {
                    tray.add(icon);
                    if(mode == 1) {
                        icon.displayMessage("Image Shield Alert", "You successfully encrypted " + fileChooser.getSelectedFile().getPath(), TrayIcon.MessageType.INFO);
                        LogsUtils.logAction("You successfully encrypted the following file: " + filePath);
                    } else if (mode == 2) {
                        icon.displayMessage("Image Shield Alert", "You successfully decrypted " + fileChooser.getSelectedFile().getPath(), TrayIcon.MessageType.INFO);
                        LogsUtils.logAction("You successfully decrypted the following file: " + filePath);
                    }
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred! Check to see if you entered the right password, or if the file was encrypted previously!");
        }
    }
}
