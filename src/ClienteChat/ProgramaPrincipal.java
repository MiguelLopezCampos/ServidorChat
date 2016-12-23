/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClienteChat;
import ServidorChat.*;
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
public class ProgramaPrincipal {
    
    public static void main(String[] args) {

        byte []buferEnvio;
        byte []buferRecepcion=new byte[256];
        int bytesLeidos=0;

        // Nombre del host donde se ejecuta el servidor:
        String host="localhost";
        // Puerto en el que espera el servidor:
        int port=8989;
        // Socket para la conexión TCP
        Socket socketServicio=null;
        
        ClienteView view;
        

        EscritorClienteChat escritor;
        LectorClienteChat lector;

        try {
                // Creamos un socket que se conecte a "hist" y "port":
                //////////////////////////////////////////////////////
                // socketServicio= ... (Completar)
               
                socketServicio=new Socket(host,port);
                //////////////////////////////////////////////////////

                escritor = new EscritorClienteChat(socketServicio);
                view = new ClienteView(escritor);
                lector = new LectorClienteChat(view, socketServicio);
                NameCapturer introducirNombres = new NameCapturer(view, true);
                String nombre = introducirNombres.getName();
                view.setNombre(nombre);
                OutputStream output = socketServicio.getOutputStream();
                byte [] nombreB;
                nombreB = nombre.getBytes();
                output.write(nombreB, 0, nombreB.length);
                output.flush();
                lector.start();

                view.showView();

                // Excepciones:*/
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
}
