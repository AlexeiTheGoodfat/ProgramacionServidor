package com.example.chatmulticliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationCliente extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader2 = new FXMLLoader(ApplicationCliente.class.getResource("cliente.fxml"));
        Scene scene = new Scene(fxmlLoader2.load(), 600, 400);
        stage.setTitle("Cliente chat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}