import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        // Crear un socket de servidor en el puerto 1234
        ServerSocket serverSocket = new ServerSocket(1234);

        // Esperar a que un cliente se conecte
        System.out.println("Esperando conexión de cliente...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

        // Crear un hilo de procesamiento para el cliente
        ClientHandler clientHandler = new ClientHandler(clientSocket);
        Thread clientThread = new Thread(clientHandler);
        clientThread.start();
    }
}
