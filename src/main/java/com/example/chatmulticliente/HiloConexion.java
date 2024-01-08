package com.example.chatmulticliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloConexion implements Runnable {

    private final Socket conexion;
    private LanzaServidor servidor;

    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;

    public HiloConexion(LanzaServidor servidor, Socket conexion) {
        this.conexion = conexion;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try(BufferedReader flujoEntrada=new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
            while (true) {
                String lectura = flujoEntrada.readLine();
                String comando = lectura.substring(0, 3);
                if (comando.equals("MSG")) {
                    servidor.enviarMsg(" "+lectura.substring(3));
                }

                if (comando.equals("CON")) {
                    servidor.c.escribirTexto("Se ha conectado " + lectura.substring(4));
                }

                if (comando.equals("EXI")) {
                    break;
                }

            }

        } catch (IOException e) {
            System.out.println("No se pudo crear algún flujo");
        }
    }
    public void enviar(String msg){
        try(PrintWriter flujoSalida = new PrintWriter(conexion.getOutputStream())) {
            flujoSalida.println(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
