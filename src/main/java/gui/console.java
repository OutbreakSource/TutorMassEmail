package gui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class console{

    private void executeTask(TextArea textArea, String command) {
        Task<String> commandLineTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                StringBuilder commandResult = new StringBuilder();
                try {
                    Process p = Runtime.getRuntime().exec(command);
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        commandResult.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return commandResult.toString();
            }
        };

        commandLineTask.setOnSucceeded(event -> textArea.appendText(commandLineTask.getValue()));

        new Thread(commandLineTask).start();
    }
}
