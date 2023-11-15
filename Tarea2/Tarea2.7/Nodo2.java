import java.io.*;
import java.net.*;

public class Nodo2{

    public static void main(String[] args) {
        try {
            // Creamos el socket del servidor y lo ponemos en espera de conexiones
            ServerSocket servidor = new ServerSocket(5001);
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
            
            // Creamos las matrices A2, Bt1,Bt2,Bt3 y C
            double[][] A2 = (double[][])entrada.readObject();
            double[][] Bt1 = (double[][])entrada.readObject();
            double[][] Bt2 = (double[][])entrada.readObject();
            double[][] Bt3 = (double[][])entrada.readObject();
            double[][] C = new double[1][3];

            // Mostramos las matrices A y B en consola
            System.out.println("Matriz A2:");
            mostrarMatriz(A2);
            System.out.println("Matriz Bt1:");
            mostrarMatriz(Bt1);
            System.out.println("Matriz Bt2:");
            mostrarMatriz(Bt2);
            System.out.println("Matriz Bt3:");
            mostrarMatriz(Bt3);

            //Acumulador
            double aux = 0;
            int p1 = 0;
            int p2 = 0;

            //Proceso de cálculo de matrices C4
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    aux += A2[i][j] * Bt1[i][j];
                    if(j == n-1 && i == m-1){
                        C[p1][p2] = aux;
                        aux = 0;
                        p2 += 1;
                    }
                }
                if(i % n == n-1){
                    p1 += 1;
                    p2 = 0;
                }
            }
            if (m % n != 0) {
                C[p1][p2] = aux;
            }

            //Proceso para el cálculo de matrices C5
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    aux += A2[i][j] * Bt2[i][j];
                    if(j == n-1 && i == m-1){
                        C[p1][p2] = aux;
                        aux = 0;
                        p2 += 1;
                    }
                }
                if(i % n == n-1){
                    p1 += 1;
                    p2 = 0;
                }
            }
            if (m % n != 0) {
                C[p1][p2] = aux;
            }

            //Proceso para el cálculo de matrices C6
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    aux += A2[i][j] * Bt3[i][j];
                    if(j == n-1 && i == m-1){
                        C[p1][p2] = aux;
                        aux = 0;
                        p2 += 1;
                    }
                }
                if(i % n == n-1){
                    p1 += 1;
                    p2 = 0;
                }
            }
            //if (m % n != 0) {
            //    C[p1][p2] = aux;
            //}
            
            // Mostramos la matriz C en consola 
            System.out.println("Matriz C");
            mostrarMatriz(C);

            //Enviamos la Matriz C2 al cliente
            salida.writeObject(C);
            
            // Cerramos los flujos y el socket del cliente
            entrada.close();
            salida.close();
            cliente.close();
            System.out.println("OK");
            
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