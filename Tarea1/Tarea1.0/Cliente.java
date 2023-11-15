import java.io.*;
import java.net.*;

public class Cliente{
    public static void main(String[] args){
        try{
            /*Declaracion del socket */
            Socket conexion = new Socket("localhost",5000);
            /*Entrada y salida de datos */
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexion.getInputStream());
            /*Envio de datos al servidor */
            salida.writeInt(123);
            salida.writeDouble(1234.1234);
            salida.write("hola".getBytes());
            /*Recepcion de datos del servidor */
            byte[] buffer = new byte[4];
            entrada.read(buffer,0,4);
            System.out.println(new String(buffer,"UTF-8"));
            conexion.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}