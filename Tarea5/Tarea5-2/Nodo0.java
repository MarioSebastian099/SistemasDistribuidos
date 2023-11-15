import java.rmi.Naming;
import java.security.Principal;

public class Nodo0 {
    public static void main(String[] args) {
        int n = 900; // número de filas de A y número de columnas de B
        int m = 400; // número de columnas de A y número de filas de B
        float [][] A1 = new float[n/9][m];
        float [][] A2 = new float[n/9][m];
        float [][] A3 = new float[n/9][m];
        float [][] A4 = new float[n/9][m];
        float [][] A5 = new float[n/9][m];
        float [][] A6 = new float[n/9][m];
        float [][] A7 = new float[n/9][m];
        float [][] A8 = new float[n/9][m];
        float [][] A9 = new float[n/9][m];
        float [][] Bt1 = new float[n/9][m];
        float [][] Bt2 = new float[n/9][m];
        float [][] Bt3 = new float[n/9][m];
        float [][] Bt4 = new float[n/9][m];
        float [][] Bt5 = new float[n/9][m];
        float [][] Bt6 = new float[n/9][m];
        float [][] Bt7 = new float[n/9][m];
        float [][] Bt8 = new float[n/9][m];
        float [][] Bt9 = new float[n/9][m];
        float [][] auxA1 = new float[n/3][n];
        float [][] auxA2 = new float[n/3][n];
        float [][] auxA3 = new float[n/3][n];
        float [][] Af = new float[n][n];

        try{
             // inicialización de la matriz A
            float[][] A = new float[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    A[i][j] = 2 * i + 3 * j;
                }
            }

            // inicialización de la matriz B
            float[][] B = new float[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    B[i][j] = 3 * i - 2 * j;
                }
            }

            // impresión de las matrices A y B
            System.out.println("Matriz A:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(A[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("Matriz B:");
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(B[i][j] + " ");
                }
                System.out.println();
            }

            // cálculo de la matriz transpuesta de B
            float[][] Bt = new float[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    Bt[i][j] = B[j][i];
                }
            }

            // impresión de la matriz transpuesta de B
            System.out.println("Matriz B transpuesta:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(Bt[i][j] + " ");
                }
                System.out.println();
            }
            //División de la matrices A y Bt en 9 partes iguales
            for(int i = 0; i < 9; i++){
                if(i == 0){
                    A1 = dividirMatrices(A, n, m, i * (n/9));
                    Bt1 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 1){
                    A2 = dividirMatrices(A, n, m, i * (n/9));
                    Bt2 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 2){
                    A3 = dividirMatrices(A, n, m, i * (n/9));
                    Bt3 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 3){
                    A4 = dividirMatrices(A, n, m, i * (n/9));
                    Bt4 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 4){
                    A5 = dividirMatrices(A, n, m, i * (n/9));
                    Bt5 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 5){
                    A6 = dividirMatrices(A, n, m, i * (n/9));
                    Bt6 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 6){
                    A7 = dividirMatrices(A, n, m, i * (n/9));
                    Bt7 = dividirMatrices(Bt, n, m, i * (n/9));
                }else if(i == 7){
                    A8 = dividirMatrices(A, n, m, i * (n/9));
                    Bt8 = dividirMatrices(Bt, n, m, i * (n/9));
                }else{
                    A9 = dividirMatrices(A, n, m, i * (n/9));
                    Bt9 = dividirMatrices(Bt, n, m, i * (n/9));
                }
            }

            //Creacion de las instancias de los objetos remotos
            String url = "rmi://localhost/ServidorRMI";
            String url1 = "rmi://localhost/ServidorRMI";
            String url2 = "rmi://localhost/ServidorRMI";

            //Obtenemos la referencia que apunta al objeto remoto asociado a la URL
            //Obtenemos la referencia al objeto remoto utilizando el metodo lookup, esta referencia es utilizada para invocar
            //los metodos remotos
            InterfaceRMI servidor = (InterfaceRMI)Naming.lookup(url);
            InterfaceRMI servidor1 = (InterfaceRMI)Naming.lookup(url);//Modificar cuando este en la nube
            InterfaceRMI servidor2 = (InterfaceRMI)Naming.lookup(url);//Modificar cuando este en la nube

            //float[][] C = servidor.multiplyMatrices(A1, Bt1);
            //mostrarMatriz(C);
            //Creacion de los hilos para la multiplicacion de matrices en paralelo
            Hilo t0 = new Hilo(servidor, A1, A2, A3, Bt1, Bt2, Bt3, Bt4, Bt5, Bt6, Bt7, Bt8, Bt9, n, m, auxA1);
            Hilo t1 = new Hilo(servidor1, A4, A5, A6, Bt1, Bt2, Bt3, Bt4, Bt5, Bt6, Bt7, Bt8, Bt9, n, m, auxA2);
            Hilo t2 = new Hilo(servidor2, A7, A8, A9, Bt1, Bt2, Bt3, Bt4, Bt5, Bt6, Bt7, Bt8, Bt9, n, m, auxA3);
            //Iniciamos hilos y creamos la barrera
            t0.start();
            t1.start();
            t2.start();
            t0.join();
            t1.join();
            t2.join();

            //System.out.println("La matriz FINAL es:");
            //mostrarMatriz(auxA1);
            //mostrarMatriz(auxA2);
            //mostrarMatriz(auxA3);
            //Unimos las matrices devueltas por los nodos
            for(int i = 0; i < n/3; i++){
                for(int j = 0; j < n; j++){
                    Af[i][j] = auxA1[i][j];
                }
            }
            for(int i = 0; i < n/3; i++){
                for(int j = 0; j < n; j++){
                    Af[i+auxA2.length][j] = auxA2[i][j];
                }
            }
            for(int i = 0; i < n/3; i++){
                for(int j = 0; j < n; j++){
                    Af[i+auxA3.length*2][j] = auxA3[i][j];
                }
            }
            //Si n = 9 y n = 4 mostramos matriz
            if(n == 9 && m == 4){
                mostrarMatriz(Af);
            }
            //Calculo de checksum
            int sum = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    sum += Af[i][j];
                }
            }
            System.out.println("El CheckSum es:" + sum);
        }catch(Exception e){
            System.out.println("Excepción en ClienteRMI: " + e);
        }
        
        //Multiplicacion de matrices
        //System.out.println("La matriz A1 es:");
        //mostrarMatriz(A1);
        //System.out.println("La matriz Bt1 es:");
        //mostrarMatriz(Bt1);
        //multiplyMatrices(A1, Bt1);
    }

    //Creacion de los hilos para posteriormente multiplicar matrices
    static public class Hilo extends Thread{
        private InterfaceRMI servidor;
        private float[][] A1;
        private float[][] A2;
        private float[][] A3;
        private float[][] Bt1;
        private float[][] Bt2;
        private float[][] Bt3;
        private float[][] Bt4;
        private float[][] Bt5;
        private float[][] Bt6;
        private float[][] Bt7;
        private float[][] Bt8;
        private float[][] Bt9;
        private int n;
        private int m;
        private float[][] auxA = new float[n/3][n];
        public Hilo(InterfaceRMI servidor, float[][] A1,float[][]A2, float[][]A3 ,float Bt1[][], float Bt2[][], float Bt3[][], float Bt4[][], float Bt5[][], float Bt6[][], float Bt7[][], float Bt8[][], float Bt9[][], int n, int m, float auxA[][]){
            this.servidor = servidor;
            this.A1 = A1;
            this.A2 = A2;
            this.A3 = A3;
            this.Bt1 = Bt1;
            this.Bt2 = Bt2;
            this.Bt3 = Bt3;
            this.Bt4 = Bt4;
            this.Bt5 = Bt5;
            this.Bt6 = Bt6;
            this.Bt7 = Bt7;
            this.Bt8 = Bt8;
            this.Bt9 = Bt9;
            this.n = n;
            this.m = m;
            this.auxA = auxA;
        }
        public void run(){
            float[][] subA;
            int indice = 0;
            int aux = 0;
            try{
                //Bloque de código a ejecutar en el hilo
                System.out.println("La matriz A1 recibida es:");
                mostrarMatriz(A3);
                System.out.println("La matriz Bt1 recibida es:");
                mostrarMatriz(Bt1);

                //for(int i = 0; i < 9; i++){
                for(int j = 0; j < 3; j++){
                    if(j == 0){
                        subA = servidor.multiplyMatrices(A1, Bt1);
                        //Acomodamos las matrices en la matriz final
                        for(int k = subA[0].length*indice; k < subA.length; k++){
                            for(int l = 0; l < subA[0].length; l++){
                                auxA[k][l] = subA[k][l];
                            }
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A1, Bt2);
                        System.out.println("La nueva matriz A es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//columnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt3);
                        System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//columnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt4);
                        System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt5);
                        //System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt6);
                        //System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt7);
                        //System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt8);
                        //System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt9);
                        //System.out.println("La submatriz A en la tercer multiplicacion es:");
                        mostrarMatriz(subA);
                        for(int k = 0; k < subA.length; k++){//filas
                            System.out.println("Entramos al primer for");
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                System.out.println("Entramos al segundo for");
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        System.out.println("La matriz es para j = 1 es::");
                        mostrarMatriz(auxA);
                    }else if (j == 1){
                        System.out.println("Entramos a j = 2");
                        subA = servidor.multiplyMatrices(A2, Bt1);
                        indice = subA.length;
                        //Acomodo de matrices
                        for(int k = subA[0].length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo");
                            for(int l = 0; l < subA[0].length; l++){
                                System.out.println("Entramos al segundo ciclo");
                                auxA[indice][l] = subA[indice-subA.length][l];
                            }
                            indice +=1;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A2, Bt2);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt3);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt4);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*2] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt5);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*3] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt6);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*4] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt7);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*5] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt8);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*6] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt9);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la segunda iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la segunda iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*7] = subA[indice-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        System.out.println("La matriz para j = 2 es::");
                        mostrarMatriz(auxA);
                    }else{
                        System.out.println("Entramos a j = 3");
                        subA = servidor.multiplyMatrices(A3, Bt1);
                        indice = subA.length+subA.length;
                        //Acomodo de matrices
                        for(int k = subA[0].length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo");
                            for(int l = 0; l < subA[0].length; l++){
                                System.out.println("Entramos al segundo ciclo");
                                auxA[indice][l] = subA[indice-subA.length-subA.length][l];
                            }
                            indice +=1;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt2);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt3);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt4);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*2] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt5);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*3] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt6);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*4] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt7);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*5] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt8);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*6] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        indice += 1;
                        subA = servidor.multiplyMatrices(A3, Bt9);
                        indice = subA.length+subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            System.out.println("Entramos al primer ciclo de la tercera iteracion");
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                System.out.println("Entramos al segundo de la tercera iteracion ciclo");
                                System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                                auxA[indice][l+subA[0].length*7] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                                //System.out.println("Indice, l y sublenght valen: " + indice + subA.length);
                            }
                            indice +=1;
                            aux = 0;
                        }
                        System.out.println("La matriz para j = 3 es::");
                        mostrarMatriz(auxA);
                    }
                }
                //}
                mostrarMatriz(auxA);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        public float[][] getResultado(){
            return auxA;
        }
    }

    //Division de las matrices en 9 partes iguales
    public static float[][] dividirMatrices(float[][] A, int n, int m, int incremento) {
        float [][] An = new float[n/9][m];
        //float [][] Btn = new float[n/9][m];
        for(int i = 0; i < n/9; i++){
            for (int j = 0; j < m; j++){
                An[i][j] = A[incremento+i][j];
                //Btn[i][j] = Bt[i][j];
            }
        }
        //System.out.println("Filas de la submatriz: " + An.length + "Columnas de la submatriz" + An[0].length);
        //mostrarMatriz(An);
        return An;
    }

    //Metodo para multiplicar matrices
    /*public static float[][] multiplyMatrices(float[][] A, float[][] Bt) {
        if (A[0].length != Bt[0].length) {
            throw new IllegalArgumentException("La dimensión de las matrices no es compatible para la multiplicación");
        }
        int m = A.length;
        int n = Bt.length;
        float[][] result = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                float sum = 0.0f;
                for (int k = 0; k < A[0].length; k++) {
                    sum += A[i][k] * Bt[j][k];
                }
                result[i][j] = sum;
            }
        }
        System.out.println("El producto de las matrices es:");
        mostrarMatriz(result);
        return result;
    }*/
    

    // Método para mostrar una matriz en la consola
    public static void mostrarMatriz(float[][] matriz) {
        for(int i=0; i<matriz.length; i++) {
            for(int j=0; j<matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
