import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MontanhaRussa{
    private CyclicBarrier cyclicBarrier;
    private int CAPACIDADE_CARRO;

    class Carro extends Thread{
        public void run(){
            System.out.println("Carro iniciando a volta na montanha russa...");

            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            System.out.println("O carro terminou a volta!");

        }

    }

    class Passageiro extends Thread{
        private int id;

        public Passageiro(int id){
            this.id = id;

        }

        public void run(){
            System.out.println("Passageiro " + id + " esperando para dar a volta na montanha russa!");

            try {
                cyclicBarrier.await();

            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (BrokenBarrierException e) {
                e.printStackTrace();

            }

            System.out.println("Passageiro " + id + " entrou no carro!");

        }

    }

    public void iniciaCorrida(int capacidadeCarro, int qtdePassageiros){
        CAPACIDADE_CARRO = capacidadeCarro;
        cyclicBarrier = new CyclicBarrier(CAPACIDADE_CARRO, new Carro());
        ExecutorService executorService = Executors.newFixedThreadPool(qtdePassageiros / 2);

        System.out.println("Iniciando " + (qtdePassageiros / capacidadeCarro) + " corridas...");

        for(int i = 0; i < qtdePassageiros; i++)
            executorService.submit(new Passageiro(i + 1));

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("Os " + qtdePassageiros + " passageiros finalizaram suas voltas na montanha russa.");

    }

    public static void main(String[] args){
        MontanhaRussa montanhaRussa = new MontanhaRussa();
        montanhaRussa.iniciaCorrida(2, 10); // capacidade do carro, quantidade de passageiros: sendo a quantidade um multiplo da capacidade

    }

}