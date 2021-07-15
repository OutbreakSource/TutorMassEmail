package gui;

import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
        textArea.update(textArea.getGraphics());

    }

    @Override
    public void write(int b) throws IOException {
        CompletableFuture.runAsync(()->textArea.append(String.valueOf((char) b)));
        //textArea.append(String.valueOf((char)b));
       // Platform.runLater(()->textArea.append(String.valueOf((char) b)));
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 25));
    }

}