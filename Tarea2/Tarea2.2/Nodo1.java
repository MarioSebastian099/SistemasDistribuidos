import java.io.*;
import java.net.*;

public class Nodo1{

    public static void main(String[] args) {
        try {
            // Creamos el socket del servidor y lo ponemos en espera de conexiones
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor en espera de conexión...");
            
            // Aceptamos la conexión del cliente
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado");
            
            // Obtenemos los flujos de entrada y salida
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            
            // Leemos la dimensión de las matrices
            int n = entrada.readInt();
            System.out.println("Dimensión de las matrices: " + n);
            
            // Calculamos la dimensión de las submatrices
            int m = n / 3;
            
            // Creamos las matrices A1,A2,A3 y Bt1,Bt2,Bt3
            double[][] A1 = (double[][])entrada.readObject();
            double[][] Bt1 = (double[][])entrada.readObject();
            double[][] Bt2 = (double[][])entrada.readObject();
            double[][] Bt3 = (double[][])entrada.readObject();
            
            // Mostramos las matrices A y B en consola
            System.out.println("Matriz A1:");
            mostrarMatriz(A1);
            System.out.println("Matriz Bt1:");
            mostrarMatriz(Bt1);
            System.out.println("Matriz Bt2:");
            mostrarMatriz(Bt2);
            System.out.println("Matriz Bt3:");
            mostrarMatriz(Bt3);
            
            // Cerramos los flujos y el socket del cliente
            entrada.close();
            salida.close();
            cliente.close();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void mostrarMatriz(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}