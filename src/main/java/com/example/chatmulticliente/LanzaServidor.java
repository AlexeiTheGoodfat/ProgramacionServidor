package com.example.chatmulticliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LanzaServidor implements Runnable {
    HelloController c;

    LanzaServidor(HelloController c) {
        this.c = c;
    }

    List<HiloConexion> conexiones = new ArrayList<>();

    @Override
    public void run() {
        ServerSocket serverSocket;
        final int PUERTO = 9876;
        try {
            while (true) {
                serverSocket = new ServerSocket(PUERTO);
                c.escribirTexto("### Servidor inciado");

                Socket conexion;

                conexion = serverSocket.accept();

                c.escribirTexto("--- Petición de conexión recibida");

                HiloConexion hc = new HiloConexion(this, conexion);
                conexiones.add(hc);
                new Thread(hc).start();
            }
        } catch (IOException e) {

        }

    }

    public void enviarMsg(String msg) {
        c.escribirTexto("Envio msg:" + msg);
        for (HiloConexion hc : conexiones) {
            hc.enviar(msg);
        }
    }
}

