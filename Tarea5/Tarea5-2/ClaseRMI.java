import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI {
    
    public ClaseRMI() throws RemoteException {
        super();
    }

    public float[][] multiplyMatrices(float[][] A, float[][] Bt) throws RemoteException {
        // Aquí va el código que definimos anteriormente
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
                System.out.println(sum);
            }
        }
        System.out.println("El producto de las matrices es:");
        //mostrarMatriz(result);
        return result;
    }
}
