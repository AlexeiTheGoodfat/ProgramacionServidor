package com.example.chatmulticliente;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.IllegalFormatCodePointException;


public class ControllerCliente {
    public Socket conexion = new Socket();
    private InetSocketAddress direccion;
    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;

    @FXML
    public TextArea textArea;

    @FXML
    public TextField campoNick;
    @FXML
    public TextField escribirField;

    @FXML
    void escribir(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            enviar();
        }
    }

    private void recibir() {

        new Thread(new HiloRecibirCliente(this)).start();
    }

    private void conectar() {
        direccion = new InetSocketAddress("localhost", 9876);

        try {
            conexion.connect(direccion);
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            flujoSalida = new PrintWriter(conexion.getOutputStream());

            System.out.println("Conexion establecida");

            flujoSalida.println("CON " + campoNick.getText());

            flujoSalida.flush();
        } catch (IOException e) {

        } catch (NullPointerException x) {

        }

    }

    private void enviar() {
        /*if (!conexion.isClosed()) {*/
            flujoSalida.println("MSG" + escribirField.getText());
            escribirField.setText("");
            flujoSalida.flush();
//        }
    }

    @FXML
    void initialize() {
        try {
            campoNick.setText("Anon");
            conectar();
            recibir();
            assert campoNick != null : "fx:id=\"campoNick\" was not injected: check your FXML file 'cliente.fxml'.";
            assert escribirField != null : "fx:id=\"escribirField\" was not injected: check your FXML file 'cliente.fxml'.";
            assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'cliente.fxml'.";
        } catch (NullPointerException x) {

        }

    }
}
