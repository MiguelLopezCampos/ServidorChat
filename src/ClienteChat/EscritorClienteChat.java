/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClienteChat;

import ServidorChat.EscritorChat;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class EscritorClienteChat {
    
    ClienteView view;
    private Socket socket;
    
    public EscritorClienteChat(Socket socket)
    {
        this.socket = socket;

    }
    
    
    public void cerrarCliente() throws IOException
    {
        OutputStream outputStream = socket.getOutputStream();
        
        byte [] datosEnviar;
        datosEnviar = "\r\r\rexit".getBytes();
        
        try {
            outputStream.write(datosEnviar, 0, datosEnviar.length);
        } catch (IOException ex) {
            Logger.getLogger(EscritorChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void EnviarMensaje(String mensaje) throws IOException
    {
        OutputStream outputStream;
            
        outputStream = socket.getOutputStream();
          



            byte [] datosEnviar;

            datosEnviar = mensaje.getBytes();
        try {
            outputStream.write(datosEnviar, 0, datosEnviar.length);
        } catch (IOException ex) {
            Logger.getLogger(EscritorChat.class.getName()).log(Level.SEVERE, null, ex);
        }

		
    }
}
