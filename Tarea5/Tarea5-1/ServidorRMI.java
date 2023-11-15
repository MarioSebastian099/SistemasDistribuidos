import java.rmi.Naming;
public class ServidorRMI {
    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost/ServidorRMI";
        claseRMI obj = new claseRMI();
        // Registra la instancia en el rmiregistry
        Naming.rebind(url, obj);
    }
}
