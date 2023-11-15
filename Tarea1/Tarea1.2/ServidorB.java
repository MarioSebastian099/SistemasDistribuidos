import java.io.*;
import java.net.*;
import java.util.Scanner;

class ServidorB {

    // Declaracion de objetos y variables estaticas para el uso de synchronized
    static Object obj = new Object();
    static int contador;

    static class Worker extends Thread {
        Socket conexion;
        long numero;
        long numeroInicial;
        long numeroFinal;

        Worker(Socket conexion, long numero, long numeroInicial, long numeroFinal) {
            this.conexion = conexion;
            this.numero = numero;
            this.numeroInicial = numeroInicial;
            this.numeroFinal = numeroFinal;
        }

        public void run() {
            try {
                /*Entrada y salida de datos*/
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());

                System.out.println("\nInstancia.");//indicamos en que instancia nos encontramos
                // Primero se envian los datos númericos a los servidores
                System.out.println("\t" + numero + ", " + numeroInicial + ", " + numeroFinal);
                salida.writeLong(numero);
                salida.writeLong(numeroInicial);
                salida.writeLong(numeroFinal);
                // Se realiza la sincronización de los hilos para que no existe error en los
                // resultados
                synchronized (obj) {
                    byte[] buffer = new byte[9];
                    // Aqui se puede llamar a la función hecha por nosotros o el método
                    // perteneciente a la clase InputStream
                    entrada.read(buffer, 0, 9);//leemos la respuesta del servidor A
                    String cadena = new String(buffer, "UTF-8");//creamos el string donde vamos a leer la respuesta del servidor A
                    if (cadena.startsWith("NO")) {//Comparamos una parte de la cadena, si inicia con no, entonces es primo
                        System.out.println("\nEnviado desde el servidor A: " + cadena);
                        contador++;//Incrementamos el contador
                    } else {//No es primo
                        System.out.println("\nEnviado desde el servidor A: " + cadena);//Mostramos la cadena recibida edl servidor A
                    }

                }
                // Cerrar la conexion
                conexion.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca el puerto para el servidor B");
        int puertob = scanner.nextInt();
        ServerSocket servidor = new ServerSocket(puertob);//Creamos un socket y lo ligamos al puerto 5000
        System.out.println("\nEsperando conexion");//Mostramos mensaje en espera de una conexion
        for (;;) {//El servidor se queda escuchando indefinidamente
            Socket conexion = servidor.accept();//Aceptamos la conexion
            System.out.println("\nConexion establecida");//Se notifica al usuario que se ha establecido una conexion
            /*Entrada y salida de datos */
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexion.getInputStream());
            long k;//Declaramos el parametro K
            long numero = entrada.readLong();//leemos el numero recibido 
            k = numero / 3;//Segun la definicion k = n/3
            System.out.println("\n\nSe ha recibido el numero:  " + numero);
            System.out.println("Enviando a los servidores A");
            /*Se crean los sockets para los servidores A */
            Socket conexionA1 = new Socket("localhost", 5001);//lo ligamos al puerto 5001
            Socket conexionA2 = new Socket("localhost", 5002);//lo ligamos al puerto 5002
            Socket conexionA3 = new Socket("localhost", 5003);//lo ligamos al puerto 5003

            /*Mandamos los intervalos a los servidores como se especificó en la tabla */
            Worker A1 = new Worker(conexionA1, numero, 2, k);//Instancia 1
            Worker A2 = new Worker(conexionA2, numero, k + 1, 2 * k);// Instancia 2
            Worker A3 = new Worker(conexionA3, numero, 2 * k + 1, numero - 1);// Instancia 3

            /*Se inician los hilos y se utilizan barreras para esperar a que termine la ejecucion del hilo actual y evitar errores en el procesamiento */
            A1.start();
            A1.join();
            A2.start();
            A2.join();
            A3.start();
            A3.join();

            if (contador == 3)//Si los 3 servidores A devolvieron no divide, entonces tenemos un número primo
                salida.write("ES PRIMO".getBytes());//Se manda al cliente que el número es primo 
            if (contador < 3)
                salida.write("NO ES PRIMO".getBytes());//Se manda al cliente que el número no es primo
            contador = 0;//reiniciamos el contador

            System.out.println("\nEsperando conexion...");//Volvemos a esperar otra conexion del cliente

            /*Cerramos las conexicones */
            conexionA1.close();
            conexionA2.close();
            conexionA3.close();
            conexion.close();
        }
    }
}