import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor{
    public static void main(String[] args) throws Exception{
        ServerSocket servidor = new ServerSocket(5001);
        Socket conexion = servidor.accept();
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        /*Recepcion de datos */
        int n = entrada.readInt();
        System.out.println(n);
        double x = entrada.readDouble();
        System.out.println(x);
        byte[] buffer = new byte[4];
        entrada.read(buffer, 0, 4);
        System.out.println(new String(buffer,"UTF-8"));
        /*Envío de datos */
        salida.writeBytes("Hola");
        conexion.close();
    }
}
