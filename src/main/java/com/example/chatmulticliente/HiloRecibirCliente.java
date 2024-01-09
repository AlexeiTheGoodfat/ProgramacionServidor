package com.example.chatmulticliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloRecibirCliente implements Runnable {
    ControllerCliente c;

    public HiloRecibirCliente(ControllerCliente c) {
        this.c = c;
    }

    @Override
    public void run() {
        BufferedReader flujoEntrada = c.getFlujoEntrada();
        try {
            while (flujoEntrada.read() != 0) {
                c.textArea.appendText(c.nombre+ ": " + flujoEntrada.readLine() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
