import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class App {
    static final int TAMANHO = 5;

    static class Processo implements Runnable {
        private CountDownLatch latch;
        private int id;

        public Processo(CountDownLatch latch, int id){
            this.latch = latch;
            this.id = id;

        }

        public void run(){
            System.out.println("Thread " + id + " iniciada.");
            System.out.println("Contador antes de decrementar: " + latch.getCount());

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
                
            latch.countDown();

            System.out.println("Thread " + id + " finalizada.");
            System.out.println("Contador depois de decrementar: " + latch.getCount());

            if(latch.getCount() == 0)
                System.out.println("Thread " + id + " foi responsável por abrir o latch.");

        }

    }

    public static void main(String[] args){
        System.out.println("Iniciando " + TAMANHO + " threads...");

        CountDownLatch latch = new CountDownLatch(TAMANHO);
        ExecutorService executorService = Executors.newFixedThreadPool(2 * TAMANHO);

        for(int i = 0; i < TAMANHO; i++)
            executorService.submit(new Processo(latch, i + 1));

        executorService.shutdown();

        try {
            latch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("As" + TAMANHO + " foram finalizadas com sucesso!");

    }

}