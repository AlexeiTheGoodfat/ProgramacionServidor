package com.example.chatmulticliente;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class HelloController {
    @FXML
    private TextArea textArea;
    @FXML
    void initialize() {
        new Thread(new LanzaServidor(this)).start();
    }
    public void escribirTexto(String s) {
/*        Platform.runLater(() -> {
            textArea.appendText(s+"\n");
        });*/
        textArea.appendText(s+"\n");
    }
}