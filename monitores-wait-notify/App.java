import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App{
    static int MAX_PRODUCOES = 10;

    static class Deposito{
        private boolean retirando = true;
        private int qtdeCaixas;
        private int maxCaixas;

        public Deposito(int maxCaixas){
            this.qtdeCaixas = 1;
            this.maxCaixas = maxCaixas;

        }

        public synchronized void armazenar(){
            while(!retirando){
                try {
                    wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }

            retirando = false;

            if(qtdeCaixas < maxCaixas){
                qtdeCaixas++;
                System.out.println("Caixa armazenada no deposito. Quantidade de caixas = " + qtdeCaixas);

            }else
                System.out.println("Deposito cheio. Não foi possível armazenar nova caixa.");

            notifyAll();

        }

        public synchronized void retirar(){
            while(retirando){
                try {
                    wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }

            retirando = true;

            if(qtdeCaixas > 0){
                qtdeCaixas--;
                System.out.println("Caixa retirada do deposito. Quantidade de caixas = " + qtdeCaixas);

            }else
                System.out.println("Deposito vazio. Não foi possível retirar a caixa.");

            notifyAll();

        }

        public int getQtdeCaixas(){
            return qtdeCaixas;

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
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Deposito deposito = new Deposito(60);
        Random random = new Random();

        for(int i = 0; i < MAX_PRODUCOES; i++){
            executorService.submit(new Produtor(deposito, random.nextInt(201)));
            executorService.submit(new Consumidor(deposito, random.nextInt(201)));


        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("Ao fim do dia, o deposito ficou com " + deposito.getQtdeCaixas() + " caixa(s)!");

    }

}