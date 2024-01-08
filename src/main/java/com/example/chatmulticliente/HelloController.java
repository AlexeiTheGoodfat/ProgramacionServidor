package com.example.chatmulticliente;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HelloController {
    @FXML
    private TextArea textArea;
    ControllerCliente c2;
    @FXML
    void initialize() {
        new Thread(new LanzaServidor(this)).start();
    }
    public void escribirTexto(String s) {
        textArea.appendText(s+"\n");
    }
}