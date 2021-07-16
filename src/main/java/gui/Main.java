package gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws MessagingException, IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gui.CheckList.main();
    }
}