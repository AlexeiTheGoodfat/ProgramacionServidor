package com.example.chatmulticliente;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


public class ControllerCliente {
    public Socket conexion = new Socket();
    private InetSocketAddress direccion;
    private BufferedReader flujoEntrada;

    public Socket getConexion() {
        return conexion;
    }

    private PrintWriter flujoSalida;
    @FXML
    private TextFlow textFlow;

    @FXML
    public TextArea textArea;

    @FXML
    public TextField campoNick;
    @FXML
    public TextField escribirField;

    @FXML
    void escribir(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!escribirField.getText().isEmpty()) {
                enviar();
            }
        }
    }

    private void recibir() {

        new Thread(new HiloRecibirCliente(this)).start();
    }

    private void enviar() {
        /*if (!conexion.isClosed()) {*/
        flujoSalida.println("MSG"+campoNick.getText()+": " + escribirField.getText());
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
            recibir();
            assert campoNick != null : "fx:id=\"campoNick\" was not injected: check your FXML file 'cliente.fxml'.";
            assert escribirField != null : "fx:id=\"escribirField\" was not injected: check your FXML file 'cliente.fxml'.";
            assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'cliente.fxml'.";
            assert textFlow != null : "fx:id=\"textFlow\" was not injected: check your FXML file 'cliente.fxml'.";
        } catch (NullPointerException x) {

        }

    }

    private String campoNombre() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Apodo");
        dialog.setHeaderText("Apodo");
        dialog.setContentText("Introduce tu apodo");


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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void escribirColor(String msg, Color c){
        try {
            Platform.runLater(()->{
                Text text=new Text(msg);
                text.setFill(c);
                textFlow.getChildren().add(text);
            });
        }catch (NullPointerException e) {
        }
    }

    public PrintWriter getFlujoSalida() {
        return flujoSalida;
    }

}
