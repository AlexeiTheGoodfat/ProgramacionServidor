package com.example.chatmulticliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloRecibirCliente implements Runnable {
    ControllerCliente c;
    private LanzaServidor servidor;

    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;

    public HiloRecibirCliente(ControllerCliente controllerCliente) {
        this.c = controllerCliente;
    }

    @Override
    public void run() {

        try (BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(c.conexion.getInputStream()))) {
            c.escribirField.appendText(flujoEntrada.readLine());
        } catch (IOException e) {
        }
    }

}
