public class Matrices {
    public static void main(String[] args) {
        int N = 4; // la dimensión de la matriz N*N
        int[][] A = new int[N][N];
        int[][] B = new int[N][N];
        
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
    }
    
    // Método para mostrar una matriz en la consola
    public static void mostrarMatriz(int[][] matriz) {
        for(int i=0; i<matriz.length; i++) {
            for(int j=0; j<matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
