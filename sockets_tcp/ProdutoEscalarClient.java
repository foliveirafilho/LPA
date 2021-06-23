import java.net.*;
import java.io.*;

public class ProdutoEscalarClient {
    public final static int PORT = 2222;
    
    public static void main(String[] args) throws Exception {
        String hostname = args.length > 0 ? args[0] : "localhost";
        Socket server = new Socket(hostname, PORT);
        ObjectInputStream entrada = new ObjectInputStream(server.getInputStream());
        ObjectOutputStream saida = new ObjectOutputStream(server.getOutputStream());
        Vetor vetorA = new Vetor();
        Vetor vetorB = new Vetor();

        vetorA.imprimeVetor();
        vetorB.imprimeVetor();

        saida.writeObject(vetorA);
        saida.writeObject(vetorB);
        
        Integer produtoEscalar = (Integer) entrada.readObject();

        System.out.println("O produto escalar calculado foi " + produtoEscalar);

        entrada.close();
        server.close();

    }

}