import java.io.*;
import java.net.*;

//NODO3
public class Nodo3{

    public static void main(String[] args) {
        try {
            // Creamos el socket del servidor y lo ponemos en espera de conexiones
            ServerSocket servidor = new ServerSocket(5002);
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
            
            // Creamos las matrices A3, Bt1,Bt2,Bt3 y C
            double[][] A3 = (double[][])entrada.readObject();
            double[][] Bt1 = (double[][])entrada.readObject();
            double[][] Bt2 = (double[][])entrada.readObject();
            double[][] Bt3 = (double[][])entrada.readObject();
            double[][] C = new double[n/3][n];

            // Mostramos las matrices A y B en consola
            System.out.println("Matriz A3:");
            mostrarMatriz(A3);
            System.out.println("Matriz Bt1:");
            mostrarMatriz(Bt1);
            System.out.println("Matriz Bt2:");
            mostrarMatriz(Bt2);
            System.out.println("Matriz Bt3:");
            mostrarMatriz(Bt3);

            //Acumulador
            double aux = 0;
            int p1 = 0;

            //Proceso de cálculo de matrices C1 C2 y C3
            System.out.println("PRIMERA PARTE DE LA MATRIZ C:");
            double suma = 0;
            for (int i = 0; i < n/3; i++) {
                for (int j = 0; j < n; j++) {
                    suma = 0;
                    aux = j;
                    for (int k = 0; k < n; k++) {
                        if(aux / (n/3) < n/3  && aux / (n/3) < 1){
                            suma += A3[i][k] * Bt1[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt1[p1][k]);
                            //System.out.println("SUBMATRIZ 1 J / N ES" + aux/(n/3));
                        }else if(aux / (n/3) >= 1 && aux / (n/3) < 2){
                            suma += A3[i][k] * Bt2[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt2[p1][k]);
                            //System.out.println("SUBMATRIZ 2 J / N ES" + aux/(n/3));
                        }else{
                            suma += A3[i][k] * Bt3[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt3[p1][k]);
                            //System.out.println("SUBMATRIZ 3 J / N ES" + aux/(n/3));
                        }
                    }
                    if(p1 % (n/3) == 0 && p1 != 0){
                        p1 = 0;
                        //System.out.println("HACEMOS P1 IGUAL A 0" + p1);
                    }else{
                        p1++;
                        if(p1 == n/3){
                            p1 = 0;
                        }
                        //System.out.println("INCREMENTAMOS P1" + p1);
                    }
                    if(n == 3){
                        p1 = 0;
                    }
                    C[i][j] = suma;
                    System.out.println("LA SUMA AL FINALIZAR UN RENGLON ES " + suma);
                    System.out.print(C[i][j] + " ");
                }
            }
            
            // Mostramos la matriz C en consola 
            System.out.println("Matriz C");
            mostrarMatriz(C);

            //Enviamos la Matriz C1 al cliente
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