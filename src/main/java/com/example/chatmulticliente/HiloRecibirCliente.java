package com.example.chatmulticliente;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

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
                String cadenaCompleta=flujoEntrada.readLine();
                String hex=cadenaCompleta.substring(cadenaCompleta.indexOf("#"));
                String texto=cadenaCompleta.substring(cadenaCompleta.indexOf(":"),cadenaCompleta.indexOf("#"));
                String nombre=cadenaCompleta.substring(0,cadenaCompleta.indexOf(":"));
                c.escribirColor(nombre+":",Color.BLACK);
                c.escribirColor(texto + "\n",Color.valueOf(hex));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
