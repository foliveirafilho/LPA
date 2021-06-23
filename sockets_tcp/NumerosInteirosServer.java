import java.net.*;
import java.util.Random;
import java.io.*;
 
class ClientThread implements Runnable {
    protected Socket clientSocket = null;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }

    public void run() {
        Random random = new Random();
        int numero = random.nextInt(1000);
        InetAddress clientAddr = clientSocket.getInetAddress();
        System.out.println("Cliente conectado: " + clientAddr.getHostAddress());

        try {
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            output.writeObject(numero);
            System.out.println("Valor enviado para o cliente: " + numero);
    
            Integer valor = (Integer) input.readObject();
    
            numero = valor.intValue();
            System.out.println("Valor recebido do cliente: " + numero);

            numero += 2;
            output.writeObject(numero);
            System.out.println("Valor enviado para o cliente: " + numero);
    
            output.close();
            input.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        System.out.println("Conexao finalizada!\n");

    }

}

public class NumerosInteirosServer {
    public final static int PORT = 2222;
    protected static ServerSocket server = null; 

    public static void main(String[] args) throws IOException{
        server = new ServerSocket(PORT);

        System.out.println("Aguardando conexoes...");

        while (true) {
            Socket clientSocket = server.accept();
            new Thread(new ClientThread(clientSocket)).start();

        }

    }

} 