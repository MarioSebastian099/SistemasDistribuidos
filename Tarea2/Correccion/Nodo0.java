import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Nodo0 {
    public static void main(String[] args) {
        int N = 3; // la dimensión de la matriz N*N
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
                    for(int i = 0; i < N; i++){
                        for(int j = 0; j < N; j++){
                            checkSum += C[i][j];
                        }
                    }
                    
                    //Mostramos en consola el checksum
                    System.out.println("El checksum de la matriz C es: " + checkSum);
                    checkSum = 0;
                    break;
                }

                case 1:{
                    // Enviar las matrices al servidor Nodo1
                    try {
                        //Iniciar servidor Nodo1
                        Thread hilo1 = new Thread(new Runnable(){
                            public void run(){
                                Nodo1.main(5000);
                            }
                        });
                        hilo1.start();

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
                    break;
                }
                case 2:{
                    //Enviar las matrices al servidor Nodo2
                    try {
                        //Iniciamos Nodo2
                        Thread hilo2 = new Thread(new Runnable() {
                            public void run(){
                                Nodo2.main(5001);
                            }
                        });
                        hilo2.start();

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
                    break;
                }
                case 3:{  
                    //Enviar las matrices al servidor Nodo3
                    try {
                        //Iniciar el servidor Nodo3
                        Thread hilo3 = new Thread(new Runnable() {
                           public void run(){
                            Nodo3.main(5002);
                           } 
                        });
                        hilo3.start();

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

//NODO1
class Nodo1{

    public static void main(int puerto) {
        try {
            // Creamos el socket del servidor y lo ponemos en espera de conexiones
            ServerSocket servidor = new ServerSocket(puerto);
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
            
            // Creamos las matrices A1,A2,A3, Bt1,Bt2,Bt3 y C
            double[][] A1 = (double[][])entrada.readObject();
            double[][] Bt1 = (double[][])entrada.readObject();
            double[][] Bt2 = (double[][])entrada.readObject();
            double[][] Bt3 = (double[][])entrada.readObject();
            double[][] C = new double[n/3][n];

            // Mostramos las matrices A y B en consola
            System.out.println("Matriz A1:");
            mostrarMatriz(A1);
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
                            suma += A1[i][k] * Bt1[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt1[p1][k]);
                            //System.out.println("SUBMATRIZ 1 J / N ES" + aux/(n/3));
                        }else if(aux / (n/3) >= 1 && aux / (n/3) < 2){
                            suma += A1[i][k] * Bt2[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt2[p1][k]);
                            //System.out.println("SUBMATRIZ 2 J / N ES" + aux/(n/3));
                        }else{
                            suma += A1[i][k] * Bt3[p1][k];
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

//NODO2
class Nodo2{

    public static void main(int puerto) {
        try {
            // Creamos el socket del servidor y lo ponemos en espera de conexiones
            ServerSocket servidor = new ServerSocket(puerto);
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
            double[][] C = new double[n/3][n];

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

            //Proceso de cálculo de matrices C4 C5 y C6
            System.out.println("PRIMERA PARTE DE LA MATRIZ C:");
            double suma = 0;
            for (int i = 0; i < n/3; i++) {
                for (int j = 0; j < n; j++) {
                    suma = 0;
                    aux = j;
                    for (int k = 0; k < n; k++) {
                        if(aux / (n/3) < n/3  && aux / (n/3) < 1){
                            suma += A2[i][k] * Bt1[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt1[p1][k]);
                            //System.out.println("SUBMATRIZ 1 J / N ES" + aux/(n/3));
                        }else if(aux / (n/3) >= 1 && aux / (n/3) < 2){
                            suma += A2[i][k] * Bt2[p1][k];
                            //System.out.println("Los elementos a multilicar son +: " + A1[i][k] + " * " + Bt2[p1][k]);
                            //System.out.println("SUBMATRIZ 2 J / N ES" + aux/(n/3));
                        }else{
                            suma += A2[i][k] * Bt3[p1][k];
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

//NODO3
class Nodo3{

    public static void main(int puerto) {
        try {
            // Creamos el socket del servidor y lo ponemos en espera de conexiones
            ServerSocket servidor = new ServerSocket(puerto);
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