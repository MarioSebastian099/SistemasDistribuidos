import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Nodo0 {
    public static void main(String[] args) {
        int N = 3000; // la dimensión de la matriz N*N
        double[][] A = new double[N][N];
        double[][] B = new double[N][N];
        double[][] aux = new double[N][N];
        double[][] A1 = new double[N/3][N];
        double[][] A2 = new double[N/3][N];
        double[][] A3 = new double[N/3][N];
        double[][] Bt1 = new double[N/3][N];
        double[][] Bt2 = new double[N/3][N];
        double[][] Bt3 = new double[N/3][N];
        double[][] C1 = new double[N/3][N];
        double[][] C2 = new double[N/3][N];
        double[][] C3 = new double[N/3][N/3];
        double[][] C = new double[N][N];
        double checkSum = 0;
        int auxp = 0;
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;

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

                    //Inicializar la matriz B
                        // Inicializar la matriz B
                    for(int i=0; i<N; i++) {
                        for(int j=0; j<N; j++) {
                            aux[i][j] = 3*i-j;
                        }
                    }

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

                    //Si N == 12 MOSTRAMOS LAS MATRICES A, B Y C
                    if(N == 12){
                        // Mostrar la matriz A en la consola
                        System.out.println("Matriz A:");
                        mostrarMatriz(A);

                        //Mostrar matriz B
                        System.out.println("Matriz B:");
                        mostrarMatriz(B);

                        //Recepcion de la matriz C
                        System.out.println("Matriz C:");
                        mostrarMatriz(C);
                    }
                    //CheckSum de la matriz C
                    for(int i = 0; i < N; i++){
                        for(int j = 0; j < N; j++){
                            checkSum += C[i][j];
                        }
                    }
                    
                    //Mostramos en consola el checksum
                    if(b1 == true && b2 == true && b3 == true){
                        System.out.println("El checksum de la matriz C es: " + checkSum);
                    }
                    checkSum = 0;
                    break;
                }

                case 1:{
                    // Enviar las matrices al servidor Nodo1
                    try {

                        // Conectar al servidor Nodo1
                        Socket socket = new Socket("20.203.138.177", 5000);

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
                        for(int i = 0; i < N/3; i++){
                            for(int j = 0; j < N; j++){
                                C[i][j] = C1[i][j];
                            }
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
                    b1 = true;
                    break;
                }
                case 2:{
                    //Enviar las matrices al servidor Nodo2
                    try {

                        // Conectar al servidor Nodo2
                        Socket socket = new Socket("20.203.136.250", 5001);

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

                      // Construir la matriz C2 a partir de los resultados obtenidos del servidor Nodo1
                      for(int i = N/3; i < 2 * (N/3); i++){
                        for(int j = 0; j < N; j++){
                            C[i][j] = C2[auxp][j];
                        }
                        auxp++;
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
                    auxp = 0;
                    b2 = true;
                    break;
                }
                case 3:{  
                    //Enviar las matrices al servidor Nodo3
                    try {

                        // Conectar al servidor Nodo2
                        Socket socket = new Socket("23.102.122.241", 5002);

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

                      // Construir la matriz C3 a partir de los resultados obtenidos del servidor Nodo1
                      for(int i = 2*(N/3); i < 3 * (N/3); i++){
                        for(int j = 0; j < N; j++){
                            C[i][j] = C3[auxp][j];
                        }
                        auxp++;
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
                    auxp = 0;
                    b3 = true;
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