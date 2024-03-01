package sockets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ariel
 */
public class Servidor {

    private ServerSocket servidor;
    private Socket cliente;
    private String mensaje;
    private boolean salir = false;
    private final int PUERTO = 5000;
    
    private DataInputStream entrada;
    private DataOutputStream salida;
    private BufferedReader teclado;
    
    public Servidor(){
        //inicializar el servidor
        try{
            servidor = new ServerSocket(5000);
            System.out.println("Servidor inicializado");
            System.out.println("Esperando al cliente");
            
            cliente = servidor.accept();
            System.out.println("Cliente aceptado");
            
            //recibe mensajes desde el cliente
            entrada = new DataInputStream(new BufferedInputStream(cliente.getInputStream()));
            
            //envia los mensajes al cliente
            salida = new DataOutputStream(cliente.getOutputStream());
            
            //lee los mensajes del teclado
            teclado= new BufferedReader(new InputStreamReader(System.in));
            
            //se inicia el mensaje
            mensaje = "";
            
            while (!salir){
                try{
                    //lee lo que envía el cliente
                    mensaje = entrada.readUTF();
                    
                    if(!mensaje.equalsIgnoreCase("salir")){
                        System.out.println("El cliente dice: " + mensaje);
                        
                        System.out.println("Digite el mensaje");
                        
                        //envía un mensaje al cliente
                        mensaje = teclado.readLine();
                        salida.writeUTF(mensaje);
                        
                    }else{
                        salir = true;
                        salida.writeUTF("salir");
                    }
                }catch (IOException ioException){
                    System.err.println("No se pudo recibir ni enviar la información");
                    salir = true;
                }
                
            }//fin del while
            
            System.out.println("Cerrando las conexiones");
            cliente.close();
            servidor.close();
            entrada.close();
            teclado.close();
            salida.close();
            System.out.println("Conexiones cerradas");
        
        }catch (IOException ioException){
            System.err.println("Se produjo un error en la conexión: " + ioException.toString());
        }
    }

    public static void main(String[] args) {
           Servidor servidor = new Servidor();
    }
    
}
