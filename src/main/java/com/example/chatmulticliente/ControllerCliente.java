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
    public Socket conexion;
    private InetSocketAddress direccion;
    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField campoNick;
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

        conexion = new Socket();

        direccion = new InetSocketAddress("localhost", 9876);

        try {
            /*campoNick.setText("Anon");*/
            conexion.connect(direccion);
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            flujoSalida = new PrintWriter(conexion.getOutputStream());

            System.out.println("Conexion establecida");
            /*flujoSalida.println("CON " + campoNick.getText());*/
            flujoSalida.flush();
        } catch (IOException e) {
            System.out.println("Se ha producido algún error en la conexión");
            textArea.appendText("Se ha producido algún error en la conexión");
        }

    }

    private void enviar() {
        try (PrintWriter flujoSalida = new PrintWriter(conexion.getOutputStream())) {
            flujoSalida.println("MSG" + escribirField.getText());
            flujoSalida.flush();
            this.escribirField.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void initialize() {
        assert campoNick != null : "fx:id=\"campoNick\" was not injected: check your FXML file 'cliente.fxml'.";
        assert escribirField != null : "fx:id=\"escribirField\" was not injected: check your FXML file 'cliente.fxml'.";
        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'cliente.fxml'.";

    }
    public ControllerCliente(){
        conectar();
        recibir();
    }
}
