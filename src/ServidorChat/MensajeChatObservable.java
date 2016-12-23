/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServidorChat;

import java.util.Observable;

/**
 *
 * @author miguel
 */
public class MensajeChatObservable extends Observable{
    private String mensaje;
    
    public MensajeChatObservable(){}
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
        
        this.setChanged();
        
        this.notifyObservers(this.getMensaje());
    }
}
