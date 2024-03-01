
package sockets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author ariel
 */
public class Cliente {

    private final int PUERTO = 5000;
    private final String HOST = "localhost";
    private String mensaje;
    private Socket cliente;
    private boolean salir = false;
    
    private DataInputStream entrada;
    private DataOutputStream salida;
    private BufferedReader teclado;
    
    public Cliente(){
        //establece la conexion con el servidor
        
        try{
            cliente = new Socket(HOST, PUERTO);
            System.out.println("Conectado");
            
            //lee los mensajes del servidor
            entrada = new DataInputStream(new BufferedInputStream(cliente.getInputStream()));
            
            //envía los mensajes al servidor
            salida = new DataOutputStream(cliente.getOutputStream());
            
            //lee los mensajes digitados por el usuario
            teclado = new BufferedReader(new InputStreamReader(System.in));
            
            //inicializa el mensaje
            mensaje = "";
            
            while (!salir){
                try{
                    System.out.println("Digite el mensaje");
                    
                    //envía un mensaje al servidor
                    mensaje = teclado.readLine();
                    salida.writeUTF(mensaje);
                    
                    //lee un mensaje del servidor
                    mensaje = entrada.readUTF();
                    
                    if(!mensaje.equalsIgnoreCase("salir")){
                        System.out.println("El servidor dice: " + mensaje);
                    }else{
                        salir = true;
                    }
                }catch (IOException ioException){
                    System.out.println("No se pudo recibir ni enviar la información");
                }//fin del catch 
            }//fin del while
            
            System.out.println("Cerrando las conexiones");
            cliente.close();
            entrada.close();
            teclado.close();
            salida.close();
            System.out.println("Conexiones cerradas");
        }catch (UnknownHostException unknownHostException){
            System.out.println("No se encontró el host para la conexion");
        }catch (IOException ioException){
            System.out.println("Se produjo un error en la conexion: " + ioException.toString());
        }
    }
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
    }
}
