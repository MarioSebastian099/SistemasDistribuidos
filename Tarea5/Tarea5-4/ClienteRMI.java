import java.rmi.Naming;
import java.security.Principal;

public class ClienteRMI {
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

            // cálculo de la matriz transpuesta de B
            float[][] Bt = new float[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    Bt[i][j] = B[j][i];
                }
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
            String url1 = "rmi://10.0.0.5/ServidorRMI";
            String url2 = "rmi://10.0.0.6/ServidorRMI";

            //Obtenemos la referencia que apunta al objeto remoto asociado a la URL
            //Obtenemos la referencia al objeto remoto utilizando el metodo lookup, esta referencia es utilizada para invocar
            //los metodos remotos
            InterfaceRMI servidor = (InterfaceRMI)Naming.lookup(url);
            InterfaceRMI servidor1 = (InterfaceRMI)Naming.lookup(url1);//Modificar cuando este en la nube
            InterfaceRMI servidor2 = (InterfaceRMI)Naming.lookup(url2);//Modificar cuando este en la nube

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
                System.out.println("Matriz C:");
                mostrarMatriz(Af);
            }
            //Calculo de checksum
            double sum = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    sum += Af[i][j];
                }
            }
            System.out.println("El CheckSum es:" + sum);
        }catch(Exception e){
            System.out.println("Excepción en ClienteRMI: " + e);
        }
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
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//columnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt3);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//columnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt4);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt5);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt6);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt7);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt8);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                        aux = 0;
                        indice +=1;
                        subA = servidor.multiplyMatrices(A1, Bt9);
                        for(int k = 0; k < subA.length; k++){//filas
                            for(int l = subA[0].length*indice; l < subA[0].length*indice+subA[0].length; l++){//col Noumnas
                                auxA[k][l] = subA[k][aux];
                                aux += 1;
                            }
                            aux = 0;
                        }
                    }else if (j == 1){
                        subA = servidor.multiplyMatrices(A2, Bt1);
                        indice = subA.length;
                        //Acomodo de matrices
                        for(int k = subA[0].length; k < subA.length*2; k++){
                            for(int l = 0; l < subA[0].length; l++){
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt3);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt4);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*2] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt5);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*3] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt6);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*4] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt7);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*5] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt8);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*6] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                        subA = servidor.multiplyMatrices(A2, Bt9);
                        indice = subA.length;
                        aux = 0;
                        //Acomodo de matrices
                        for(int k = subA.length; k < subA.length*2; k++){
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*7] = subA[indice-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                    }else{
                        subA = servidor.multiplyMatrices(A3, Bt1);
                        indice = subA.length+subA.length;
                        //Acomodo de matrices
                        for(int k = subA[0].length; k < subA.length*2; k++){
                            for(int l = 0; l < subA[0].length; l++){
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*2] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*3] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*4] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*5] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*6] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
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
                            for(int l = subA[0].length; l < subA[0].length*2; l++){
                                auxA[indice][l+subA[0].length*7] = subA[indice-subA.length-subA.length][aux];
                                aux +=1;
                            }
                            indice +=1;
                            aux = 0;
                        }
                    }
                }
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
        for(int i = 0; i < n/9; i++){
            for (int j = 0; j < m; j++){
                An[i][j] = A[incremento+i][j];
            }
        }
        return An;
    }

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