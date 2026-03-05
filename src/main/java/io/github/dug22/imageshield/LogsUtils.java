package io.github.dug22.imageshield;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogsUtils {

    private static final Path logsPath = Paths.get(System.getProperty("user.home"), "ImageShield", "logs.txt");
    public static void createLogsFile() {

        try {
            Files.createDirectories(logsPath.getParent());

            if (Files.notExists(logsPath)) {
                Files.createFile(logsPath);
            }
        } catch (IOException e) {
            System.err.println("Could not create logs file: " + e.getMessage());
        }
    }

    public static void logAction(String message){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("[%s] %s%n", timestamp, message);
        try {
            Files.writeString(logsPath,
                    logEntry,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    public static void openLogsFile(){
        Desktop desktop = Desktop.getDesktop();
        File logsFile = logsPath.toFile();
        if(logsFile.exists()){
            try {
                desktop.open(logsFile);
            } catch (IOException e) {
                System.err.println("The given logs file, doesn't exist!");
            }
        }
    }
}
