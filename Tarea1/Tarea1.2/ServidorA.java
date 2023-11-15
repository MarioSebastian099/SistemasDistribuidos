import java.io.*;
import java.net.*;

class ServidorA {

    static class Worker extends Thread {
        Socket conexion;

        Worker(Socket conexion) {
            this.conexion = conexion;
        }

        /*Dividimos al numero entre todos los n entre numeroInicial y numeroFinal */
        public boolean division(long numero, long numeroInicial, long numeroFinal) {
            boolean bandera = false;
            for (long i = numeroInicial; i <= numeroFinal; i++)
                if (numero % i == 0)
                    bandera = true;

            return bandera;
        }

        public void run() {
            try {
                // entrada y salida de datos
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                /*lectura de datos*/
                long numero = entrada.readLong();
                long numeroInicial = entrada.readLong();
                long numeroFinal = entrada.readLong();
                System.out.println("Instancia: " + numero + ", " + numeroInicial + ", " + numeroFinal);

                // Aqui se regresa el mensaje
                if (division(numero, numeroInicial, numeroFinal)) {
                    // envio de datos
                    salida.write("DIVIDE".getBytes());
                } else {
                    salida.write("NO DIVIDE".getBytes());
                }

                conexion.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception, InterruptedException {
        /*Instancias de los servidores A */
        //ServerSocket servidor = new ServerSocket(5001);
        //ServerSocket servidor = new ServerSocket(5002);
        ServerSocket servidor = new ServerSocket(5003);
        //System.out.println("\nInstancia 1 esperando conexi\u00f3n...");
        //System.out.println("\nInstancia 2 esperando conexi\u00f3n...");
        System.out.println("\nInstancia 3 esperando conexi\u00f3n...");
        for (;;) {//Ponemos al servidor A en escucha
            Socket conexion = servidor.accept();//Se acepta la conexion
            System.out.println("\nRecibiendo datos");
            Worker w = new Worker(conexion);
            w.start();
            Thread.sleep(100000);
            conexion.close();//Cerramos la conexion
        }
    }
}