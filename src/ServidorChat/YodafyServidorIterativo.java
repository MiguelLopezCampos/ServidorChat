package ServidorChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

    public static void main(String[] args) {

            // Puerto de escucha
        int cliente = 0;
        MensajeChatObservable chat;
        int port=8989;
        // array de bytes auxiliar para recibir o enviar datos.
        byte []buffer=new byte[256];
        // Número de bytes leídos
        int bytesLeidos=0;


        ServerSocket socketServidor;
        Socket socketConexion=null;
        try {
            // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
            //////////////////////////////////////////////////
            // ...serverSocket=... (completar)
            socketServidor = new ServerSocket(port);
            chat = new MensajeChatObservable();
            //////////////////////////////////////////////////

            // Mientras ... siempre!
            do {

                // Aceptamos una nueva conexión con accept()
                /////////////////////////////////////////////////
                // socketServicio=... (completar)
                socketConexion = socketServidor.accept();
                //////////////////////////////////////////////////
           /* OutputStream outputStream=socketConexion.getOutputStream();
            InputStream inputStream = socketConexion.getInputStream();
            
            byte [] datosEnviar = "Elige tu nombre para chatear: ".getBytes();
            outputStream.write(datosEnviar,0,datosEnviar.length);
            outputStream.flush();
            // Lee la frase a Yodaficar:
            ////////////////////////////////////////////////////////
            // read ... datosRecibidos.. (Completar)
            byte [] datosRecibidos = new byte [2048];
            int bytesRecibidos=inputStream.read(datosRecibidos);
            String nombre = new String (datosRecibidos, 0, bytesRecibidos);
                outputStream.close();
                inputStream.close();*/
           //System.out.println(nombre);
                // Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
                // argumento el nuevo socket, para que realice el procesamiento
                // Este esquema permite que se puedan usar hebras más fácilmente.
                InputStream input = socketConexion.getInputStream();
                byte [] nombre = new byte [2048];
                int bytesrecibidos;
                bytesrecibidos = input.read(nombre);
                String nombre1 = new String(nombre, 0, bytesrecibidos);
                //input.close();
                //chat.addProceso();
                EscritorChat procesador=new EscritorChat(socketConexion, chat, cliente, nombre1);
                procesador.start();
                cliente++;

            } while (true);

        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto "+port);
        }

    }

}
