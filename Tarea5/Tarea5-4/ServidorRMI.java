import java.rmi.Naming;
public class ServidorRMI {
    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost/ServidorRMI";
        ClaseRMI obj = new ClaseRMI();
        // Registra la instancia en el rmiregistry
        Naming.rebind(url, obj);/*El parametro url tiene que coincidir con los nombres de la llamada naming lookup*/ //Modificar cuando este en la nube
    }
}
