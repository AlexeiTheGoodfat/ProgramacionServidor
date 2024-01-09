package com.example.chatmulticliente;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;


public class ControllerCliente {
    public Socket conexion = new Socket();
    static String nombre;
    private InetSocketAddress direccion;
    private BufferedReader flujoEntrada;

    public Socket getConexion() {
        return conexion;
    }

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

    /*public void escribirField() {
        try {
            flujoEntrada=new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            textArea.appendText(campoNick.getText()+":"+flujoEntrada.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private void enviar() {
        /*if (!conexion.isClosed()) {*/
        flujoSalida.println("MSG" + escribirField.getText());
        escribirField.setText("");
        flujoSalida.flush();
//        }
    }

    public BufferedReader getFlujoEntrada() {
        return flujoEntrada;
    }

    @FXML
    void initialize() {
        try {
            conectar();
            nombre = campoNick.getText();
            assert campoNick != null : "fx:id=\"campoNick\" was not injected: check your FXML file 'cliente.fxml'.";
            assert escribirField != null : "fx:id=\"escribirField\" was not injected: check your FXML file 'cliente.fxml'.";
            assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'cliente.fxml'.";
        } catch (NullPointerException x) {

        }

    }

    private String campoNombre() {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");


        Optional<String> result = dialog.showAndWait();
        return result.get();
    }

    public void conectar() {
        while (this.campoNick.getText().isEmpty()) {
            this.campoNick.setText(campoNombre());
        }
        direccion = new InetSocketAddress("localhost", 9876);

        try {
            conexion.connect(direccion);
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            flujoSalida = new PrintWriter(conexion.getOutputStream());

            System.out.println("Conexion establecida");

            flujoSalida.println("CON" + campoNick.getText());
            flujoSalida.flush();
            recibir();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PrintWriter getFlujoSalida() {
        return flujoSalida;
    }

}
