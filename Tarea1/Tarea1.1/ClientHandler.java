import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Crear un objeto de entrada para recibir los parámetros
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            // Leer los parámetros del cliente
            long param1 = input.readLong();
            long param2 = input.readLong();
            long param3 = input.readLong();

            // Procesar los parámetros
            long result = param1 + param2 + param3;

            // Enviar el resultado al cliente
            System.out.println("El resultado es: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
