/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClienteChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author miguel
 */
public class LectorClienteChat extends Thread{
    
    private ClienteView vista;
    private Socket socket;
    
    public LectorClienteChat(ClienteView vista, Socket socket)
    {
        this.vista = vista;
        this.socket = socket;
    }
    
    public void run()
    {
        try {
                InputStream inputStream = socket.getInputStream();

                
                while(true)
                {
                    int bytesLeidos;
                    byte [] mensaje = new byte[2048];
                    bytesLeidos = inputStream.read(mensaje);
                    String msg = new String(mensaje, 0, bytesLeidos);
                   // System.out.println(msg);
                    vista.addMensaje(msg);
                }


                // Excepciones:*/
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
}
