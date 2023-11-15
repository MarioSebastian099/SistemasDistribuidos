import java.rmi.Remote;
import java.rmi.RemoteException;

public interface interfaceRMI extends Remote {
    float[][] multiplyMatrices(float[][] A, float[][] Bt) throws RemoteException;
}
