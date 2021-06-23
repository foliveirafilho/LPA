import java.io.Serializable;
import java.util.Random;

public class Vetor implements Serializable {
    private final int QTDE_VALORES = 20;
    private int vetor[];

    public Vetor(){
        vetor = new int[QTDE_VALORES];
        geraValores();

    }

    public void geraValores(){
        Random random = new Random();

        for (int i = 0; i < QTDE_VALORES; i++) {
            vetor[i] = random.nextInt(10);

        }

    }

    public int produtoEscalar(Vetor vetorA, Vetor vetorB){
        int produto = 0;

        for(int i = 0; i < QTDE_VALORES; i++) {
            produto += vetorA.getValor(i) * vetorB.getValor(i);

        }

        return produto;

    }

    public int getValor(int indice){
        return vetor[indice];

    }

    public void imprimeVetor(){
        System.out.print("V = {");
        for(int i = 0; i < QTDE_VALORES; i++) {
            System.out.print(vetor[i]);

            if(i != QTDE_VALORES - 1){
                System.out.print(", ");

            }

        }

        System.out.println("}");

    }

}