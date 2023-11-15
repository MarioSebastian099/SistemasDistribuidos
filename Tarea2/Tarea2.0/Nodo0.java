import java.io.*;
import java.net.*;

public class Nodo0 {
    public static void main(String[] args) {
        int N = 4; // la dimensión de la matriz N*N
        double[][] A = new double[N][N];
        double[][] B = new double[N][N];
        
        // Inicializar la matriz A
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                A[i][j] = 2*i+j;
            }
        }
        
        // Inicializar la matriz B y calcular su transpuesta
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                B[i][j] = 3*i-j;
                
                // Calcular la matriz transpuesta de B mientras se inicializa
                B[j][i] = 3*i-j;
            }
        }
        
        // Mostrar la matriz A en la consola
        System.out.println("Matriz A:");
        mostrarMatriz(A);
        
        // Mostrar la matriz transpuesta de B en la consola
        System.out.println("Matriz transpuesta de B:");
        mostrarMatriz(B);
        // Enviar las matrices al servidor
        try {
            // Conectar al servidor
            Socket socket = new Socket("localhost", 5000);
            
            // Obtener los flujos de entrada y salida
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            //Enviar dimension
            out.writeInt(N);
            
            // Enviar la matriz A
            out.writeObject(A);
            
            // Enviar la matriz B
            out.writeObject(B);
            
            // Recibir la matriz transpuesta de B desde el servidor
            double[][] B_transpuesta = (double[][])in.readObject();
            
            // Mostrar la matriz transpuesta de B recibida del servidor en la consola
            System.out.println("Matriz transpuesta de B recibida del servidor:");
            mostrarMatriz(B_transpuesta);
            
            // Cerrar los flujos y el socket
            out.close();
            in.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Método para mostrar una matriz en la consola
    public static void mostrarMatriz(double[][] matriz) {
        for(int i=0; i<matriz.length; i++) {
            for(int j=0; j<matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
