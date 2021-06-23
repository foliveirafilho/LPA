import java.net.*;
import java.io.*;
 
class ClientThread implements Runnable {
    protected Socket clientSocket = null;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }

    public void run() {
        InetAddress clientAddr = clientSocket.getInetAddress();
        System.out.println("Cliente conectado: " + clientAddr.getHostAddress());

        try {
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            Vetor vetorA = (Vetor) input.readObject();
            Vetor vetorB = (Vetor) input.readObject();

            System.out.println("Calculando produto escalar...");
            int produtoEscalar = vetorA.produtoEscalar(vetorA, vetorB);

            output.writeObject(produtoEscalar);
            System.out.println("Resultado do produto enviado ao cliente!");
    
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

public class ProdutoEscalarServer {
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