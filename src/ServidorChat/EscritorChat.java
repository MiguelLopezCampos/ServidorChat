//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
package ServidorChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.System.exit;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//

public class EscritorChat extends Thread implements Observer{
    // Referencia a un socket para enviar/recibir las peticiones/respuestas
    private Socket socketServicio;
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    private InputStream inputStream;
    // stream de escritura (por aquí se envía los datos al cliente)
    private OutputStream outputStream;
    
    public Thread thr;
    
    boolean conectado = true;

    // Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
    private Random random;

    private int id;
    
    String nombre;
    
  //  private LectorChat lector;

    private MensajeChatObservable chat;
    // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
    public EscritorChat(Socket socketServicio, MensajeChatObservable chat, int id, String nombre) throws IOException {
            this.socketServicio=socketServicio;
            random=new Random();
            this.chat = chat;
            this.id = id;
            this.nombre = nombre;
            thr = new Thread(this, "escritor");
            outputStream = socketServicio.getOutputStream();
            chat.addObserver(this);
    }


    // Aquí es donde se realiza el procesamiento realmente:
    public void run()
    {
            // Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
        byte [] datosRecibidos=new byte[1024];
        int bytesRecibidos=0;

        // Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
        byte [] datosEnviar;


        try {
            // Obtiene los flujos de escritura/lectura
            inputStream=socketServicio.getInputStream();
           
            ////////////////////////////////////////////////////////
            
           // lector = new LectorChat(chat, socketServicio, id);
           
            while (conectado)
            {
                byte [] mensajeRecibido = new byte[2048];
                int bytesRecibidosMensaje;
                bytesRecibidosMensaje = inputStream.read(mensajeRecibido);

               // while(chat.todosHanLeido() == false){}
                                		
                String mensaje = new String (mensajeRecibido, 0, bytesRecibidosMensaje);

                 if(mensaje.compareTo("\r\r\rexit") == 0){ 
                     socketServicio.close(); inputStream.close(); 
                 outputStream.close(); conectado=false;}
                else{
               //System.out.println(mensaje);
                chat.setMensaje(nombre + ": " + mensaje + "\n");
                }
            }

        } catch (IOException e) {
                System.err.println("Error al obtener los flujso de entrada/salida.");
        }

    }

    @Override
    public void update(Observable o, Object arg) {
            try {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                byte [] datosEnviar;
                datosEnviar = arg.toString().getBytes();
                outputStream.write(datosEnviar, 0, datosEnviar.length);
            } catch (IOException ex) {
                Logger.getLogger(EscritorChat.class.getName()).log(Level.SEVERE, null, ex);
            }    }


}
