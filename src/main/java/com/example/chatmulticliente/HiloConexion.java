package com.example.chatmulticliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class HiloConexion implements Runnable {

    private final Socket conexion;
    private LanzaServidor servidor;

    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;
    HiloRecibirCliente c;
    int r= ThreadLocalRandom.current().nextInt(256);
    int g=ThreadLocalRandom.current().nextInt(256);
    int b=ThreadLocalRandom.current().nextInt(256);

    public HiloConexion(LanzaServidor servidor, Socket conexion) {
        this.conexion = conexion;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            flujoSalida=new PrintWriter(conexion.getOutputStream());
            while (true) {
                String lectura = flujoEntrada.readLine();
                String comando = lectura.substring(0, 3);
                if (comando.equals("MSG")) {
                    servidor.enviarMsg(" "+lectura.substring(3)+" "+hex());
                }

                if (comando.equals("CON")) {
                    servidor.c.escribirTexto("Se ha conectado " + lectura.substring(3));
                }

                if (comando.equals("EXI")) {
                    break;
                }

            }

        } catch (IOException e) {
            System.out.println("No se pudo crear algún flujo");
            e.printStackTrace();
        } finally {
            try {
                flujoEntrada.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void enviar(String msg) {
        flujoSalida.println(msg);
        flujoSalida.flush();

    }
    private String hex(){

        return "#"+Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
    }
}
