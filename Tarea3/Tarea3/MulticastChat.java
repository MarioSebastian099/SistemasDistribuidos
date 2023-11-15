import java.io.IOException;
import java.net.*;

public class MulticastChat {
    private static final int MAX_MESSAGE_LEN = 100; // longitud máxima del mensaje
    private String username;
    private InetAddress groupAddress;
    private MulticastSocket socket;
    private Thread receiverThread;

    public MulticastChat(String username) {
        this.username = username;

        try {
            this.groupAddress = InetAddress.getByName("239.0.0.0");
            this.socket = new MulticastSocket(50000);
            this.socket.joinGroup(this.groupAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Thread para recibir mensajes
        this.receiverThread = new Thread(() -> {
            while (true) {
                byte[] buffer = new byte[MAX_MESSAGE_LEN];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(packet);
                    String received = new String(packet.getData()).trim();
                    String[] parts = received.split("--->");

                    // Despliega el mensaje solo si no lo envió el usuario actual
                    if (!parts[0].equals(username)) {
                        System.out.println(received);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        this.receiverThread.start();
    }

    public void send(String message) {
        String formattedMessage = username + "--->" + message;
        byte[] buffer = formattedMessage.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, groupAddress, 50000);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Se debe especificar el nombre de usuario como argumento");
            System.exit(1);
        }

        MulticastChat chat = new MulticastChat(args[0]);

        while (true) {
            System.out.print("Escribe tu mensaje: ");
            String message = System.console().readLine().trim();
            chat.send(message);
        }
    }
}
