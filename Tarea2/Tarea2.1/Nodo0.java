import java.io.*;
import java.net.*;

public class Nodo0 {
    public static void main(String[] args) {
        int N = 12; // la dimensión de la matriz N*N
        double[][] A = new double[N][N];
        double[][] B = new double[N][N];
        double[][] A1 = new double[N/3][N];
        double[][] A2 = new double[N/3][N];
        double[][] A3 = new double[N/3][N];
        double[][] Bt1 = new double[N/3][N];
        double[][] Bt2 = new double[N/3][N];
        double[][] Bt3 = new double[N/3][N];
        
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

        // Dividimos la matriz A en tres partes.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i < N/3) {
                    A1[i][j] = A[i][j];
                } else if (i < 2*N/3) {
                    A2[i-N/3][j] = A[i][j];
                } else {
                    A3[i-2*N/3][j] = A[i][j];
                }
            }
        }

        // Dividimos la matriz B en tres partes.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i < N/3) {
                    Bt1[i][j] = B[i][j];
                } else if (i < 2*N/3) {
                    Bt2[i-N/3][j] = B[i][j];
                } else {
                    Bt3[i-2*N/3][j] = B[i][j];
                }
            }
        }

        
        // Mostrar la matriz A en la consola
        System.out.println("Matriz A:");
        mostrarMatriz(A);
        
        // Mostrar la matriz transpuesta de B en la consola
        System.out.println("Matriz transpuesta de B:");
        mostrarMatriz(B);

        // Mostramos las submatrices en la consola.
        System.out.println("Submatriz A1:");
        for (int i = 0; i < N/3; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(A1[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Submatriz A2:");
        for (int i = 0; i < N/3; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(A2[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Submatriz A3:");
        for (int i = 0; i < N/3; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(A3[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Submatriz Bt1:");
        for (int i = 0; i < N/3; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(Bt1[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Submatriz Bt2:");
        for (int i = 0; i < N/3; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(Bt2[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("Submatriz Bt3:");
        for (int i = 0; i < N/3; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(Bt3[i][j] + " ");
            }
            System.out.println();
        }

        // Enviar las matrices al servidor
        try {
            // Conectar al servidor
            Socket socket = new Socket("localhost", 5000);
            
            // Obtener los flujos de entrada y salida
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            //Enviar dimension
            out.writeInt(N);
            
            // Enviar la matriz A1 al Nodo1
            out.writeObject(A1);

            // Enviar la matriz Bt1
            out.writeObject(Bt1);

            //Enviar la matriz Bt2
            out.writeObject(Bt2);

            //Enviar la matriz Bt3
            out.writeObject(Bt3);

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
