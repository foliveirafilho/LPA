import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App{
    static int MAX_PRODUCOES = 20;

    static class Deposito{
        private final Object lock = new Object();

        int qtdeCaixas;
        int maxCaixas;

        public Deposito(int qtdeCaixas, int maxCaixas){
            this.qtdeCaixas = qtdeCaixas;
            this.maxCaixas = maxCaixas;

        }

        public Deposito(int maxCaixas){
            this.qtdeCaixas = 0;
            this.maxCaixas = maxCaixas;

        }

        public void armazenar(){
            synchronized(lock){
                if(qtdeCaixas < maxCaixas){
                    qtdeCaixas++;
                    System.out.println("Caixa armazenada no deposito. Quantidade de caixas = " + qtdeCaixas);
    
                }else
                    System.out.println("Deposito cheio. Não foi possível armazenar nova caixa.");

            }

        }

        public void retirar(){
            synchronized(lock){
                if(qtdeCaixas > 0){
                    qtdeCaixas--;
                    System.out.println("Caixa retirada do deposito. Quantidade de caixas = " + qtdeCaixas);
    
                }else
                    System.out.println("Deposito vazio. Não foi possível retirar a caixa.");

            }

        }

    }

    static class Produtor implements Runnable{
        Deposito deposito;
        int tempoProducao;

        public Produtor(Deposito deposito, int tempoProducao){
            this.deposito = deposito;
            this.tempoProducao = tempoProducao;

        }

        public void run(){
            deposito.armazenar();

            try {
                Thread.sleep(tempoProducao);

            } catch (InterruptedException e){
                e.printStackTrace();

            }

        }

    }

    static class Consumidor extends Thread{
        Deposito deposito;
        int tempoProducao;

        public Consumidor(Deposito deposito, int tempoProducao){
            this.deposito = deposito;
            this.tempoProducao = tempoProducao;

        }

        public void run(){
            deposito.retirar();

            try {
                Thread.sleep(tempoProducao);

            } catch (InterruptedException e){
                e.printStackTrace();

            }

        }
        
    }

    public static void main(String args[]){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Deposito deposito = new Deposito(8, 60);
        Random random = new Random();

        for(int i = 0; i < MAX_PRODUCOES; i++){
            executorService.submit(new Produtor(deposito, random.nextInt(201)));
            executorService.submit(new Consumidor(deposito, random.nextInt(201)));


        }

        executorService.shutdown();

    }

}