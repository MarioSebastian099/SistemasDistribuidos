import java.rmi.Naming;

public class Nodo0 {
    public static void main(String[] args) {
        int n = 18; // número de filas de A y número de columnas de B
        int m = 8; // número de columnas de A y número de filas de B
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
    
        //interfaceRMI servidor = (interfaceRMI) Naming.lookup("rmi://localhost/ServidorRMI");

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
        
        //Multiplicacion de matrices
        System.out.println("La matriz A1 es:");
        mostrarMatriz(A1);
        System.out.println("La matriz Bt1 es:");
        mostrarMatriz(Bt1);
        multiplyMatrices(A1, Bt1);
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
    public static float[][] multiplyMatrices(float[][] A, float[][] Bt) {
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
