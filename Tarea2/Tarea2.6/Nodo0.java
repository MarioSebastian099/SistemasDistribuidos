import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Nodo0 {
    public static void main(String[] args) {
        int N = 12; // la dimensión de la matriz N*N
        double[][] A = new double[N][N];
        double[][] B = new double[N][N];
        double[][] aux = new double[N][N];
        double[][] A1 = new double[N/3][N];
        double[][] A2 = new double[N/3][N];
        double[][] A3 = new double[N/3][N];
        double[][] Bt1 = new double[N/3][N];
        double[][] Bt2 = new double[N/3][N];
        double[][] Bt3 = new double[N/3][N];
        double[][] C1 = new double[1][3];
        double[][] C2 = new double[1][3];
        double[][] C3 = new double[1][3];
        double[][] C = new double[3][3];
        double checkSum = 0;

        Scanner sc = new Scanner(System.in);

        //Seleccionamos el nodo  
        for(;;){
            //Seleccionamos el nodo
            System.out.println("Seleccione el nodo a ejecutar");
            int opcion = sc.nextInt();
            switch(opcion){
                case 0:{
                    // Inicializar la matriz A
                    for(int i=0; i<N; i++) {
                        for(int j=0; j<N; j++) {
                            A[i][j] = 2*i+j;
                        }
                    }
                    
                    // Mostrar la matriz A en la consola
                    System.out.println("Matriz A:");
                    mostrarMatriz(A);

                    //Mostrar matriz B
                    System.out.println("Matriz B:");
                    
                    // Inicializar la matriz B
                    for(int i=0; i<N; i++) {
                        for(int j=0; j<N; j++) {
                            aux[i][j] = 3*i-j;
                            System.out.print(aux[i][j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();

                    //Calcular la matriz transpuesta de B
                    for(int i=0; i<N; i++) {
                        for(int j=0; j<N; j++) {
                            B[j][i] = aux[i][j];
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

                    //Recepcion de la matriz C
                    System.out.println("Matriz C:");
                    mostrarMatriz(C);

                    //CheckSum de la matriz C
                    for(int i = 0; i < 3; i++){
                        for(int j = 0; j < 3; j++){
                            checkSum += C[i][j];
                        }
                    }
                    
                    //Mostramos en consola el checksum
                    System.out.println("El checksum de la matriz C es: " + checkSum);

                    break;
                }

                case 1:{
                    // Enviar las matrices al servidor Nodo1
                    try {
                        // Conectar al servidor Nodo1
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

                        // Recibir la matriz C desde el servidor Nodo1
                        C1 = (double[][])in.readObject();

                        //double[][] B_transpuesta = (double[][])in.readObject();
                        
                        // Construir la matriz C a partir de los resultados obtenidos del servidor Nodo1
                        for(int i = 0; i < 3; i++){
                            C[0][i] = C1[0][i];
                        }
                        // Mostrar la matriz C1 recibida del servidor Nodo1 en la consola
                        System.out.println("Matriz C recibida del servidor Nodo1:");
                        mostrarMatriz(C1);
                        
                        // Cerrar los flujos y el socket
                        out.close();
                        in.close();
                        socket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2:{
                    //Enviar las matrices al servidor Nodo2
                    try {
                        // Conectar al servidor Nodo2
                        Socket socket = new Socket("localhost", 5001);

                        // Obtener los flujos de entrada y salida
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                        //Enviar dimension
                        out.writeInt(N);
                        
                        // Enviar la matriz A2 al Nodo2
                        out.writeObject(A2);

                        // Enviar la matriz Bt1
                        out.writeObject(Bt1);

                        //Enviar la matriz Bt2
                        out.writeObject(Bt2);

                        //Enviar la matriz Bt3
                        out.writeObject(Bt3);

                        // Recibir la matriz C2 desde el servidor Nodo2
                        C2 = (double[][])in.readObject();

                        //double[][] B_transpuesta = (double[][])in.readObject();

                        // Construir la matriz C a partir de los resultados obtenidos del servidor Nodo1
                        for(int i = 0; i < 3; i++){
                            C[1][i] = C2[0][i];
                        }
                        
                        // Mostrar la matriz C2 recibida del servidor Nodo2 en la consola
                        System.out.println("Matriz C2 recibida del servidor Nodo2:");
                        mostrarMatriz(C2);
                        
                        // Cerrar los flujos y el socket
                        out.close();
                        in.close();
                        socket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 3:{  
                    //Enviar las matrices al servidor Nodo3
                    try {
                        // Conectar al servidor Nodo2
                        Socket socket = new Socket("localhost", 5002);

                        // Obtener los flujos de entrada y salida
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                        //Enviar dimension
                        out.writeInt(N);
                        
                        // Enviar la matriz A2 al Nodo2
                        out.writeObject(A3);

                        // Enviar la matriz Bt1
                        out.writeObject(Bt1);

                        //Enviar la matriz Bt2
                        out.writeObject(Bt2);

                        //Enviar la matriz Bt3
                        out.writeObject(Bt3);

                        // Recibir la matriz C3 desde el servidor Nodo3
                        C3 = (double[][])in.readObject();
                        //double[][] B_transpuesta = (double[][])in.readObject();

                        // Construir la matriz C a partir de los resultados obtenidos del servidor Nodo1
                        for(int i = 0; i < 3; i++){
                            C[2][i] = C3[0][i];
                        }
                        
                        // Mostrar la matriz C3 recibida del servidor Nodo3 en la consola
                        System.out.println("Matriz C3 recibida del servidor Nodo3:");
                        mostrarMatriz(C3);
                        
                        // Cerrar los flujos y el socket
                        out.close();
                        in.close();
                        socket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
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
