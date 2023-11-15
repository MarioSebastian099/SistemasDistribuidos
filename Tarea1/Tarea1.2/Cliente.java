import java.io.*;
import java.net.*;
import java.util.*;

class Cliente {
    public static void main(String[] args) {
        try {
            Socket conexion = new Socket("localhost", 5000); // Creamos un socket y lo ligamos al puerto 5000
            /* Entrada y Salida de datos */
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexion.getInputStream());

            boolean bandera = true; // Variable que verifica si el número ingresado por el usuario es mayor a 1
            Scanner datos = new Scanner(System.in); // Entrada de datos desde el teclado
            long dato;
            while (bandera) {
                System.out.println("Introduzca un numero para verificar su primalidad: \n"); // Pedimos el número al usuario
                dato = datos.nextLong(); // Leemos el dato 
                if (dato <= 1) {
                    System.out.println("El numero ingresado debe ser mayor a 1.\n"); // Si el número es menor a 1 volvemos a pedir otro número
                } else {
                    bandera = false; // Salimos del ciclo while
                    salida.writeLong(dato); // Mandamos el dato al servidor
                    /* Recepcion de datos desde el servidor */
                    byte[] buffer = new byte[11];
                    entrada.read(buffer, 0, 11);
                    System.out.println("\n");
                    System.out.println(new String(buffer, "UTF-8"));
                    Thread.sleep(1000); // Esperamos 1 segundo
                    conexion.close(); // Cerramos la conexión
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
