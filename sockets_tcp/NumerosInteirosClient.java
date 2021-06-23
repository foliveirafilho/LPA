import java.net.*;
import java.io.*;

public class NumerosInteirosClient {
    public final static int PORT = 2222;
    
    public static void main(String[] args) throws Exception {
        String hostname = args.length > 0 ? args[0] : "localhost";
        Socket server = new Socket(hostname, PORT);
        ObjectInputStream entrada = new ObjectInputStream(server.getInputStream());
        ObjectOutputStream saida = new ObjectOutputStream(server.getOutputStream());
        
        Integer serverData = (Integer) entrada.readObject();

        int numero = serverData.intValue();
        System.out.println("Valor recebido do servidor: " + numero);

        numero++;
        saida.writeObject(numero);
        System.out.println("Valor enviado ao servidor: " + numero);

        serverData = (Integer) entrada.readObject();
        numero = serverData.intValue();
        System.out.println("Valor recebido do servidor: " + numero);

        entrada.close();
        server.close();

    }

}