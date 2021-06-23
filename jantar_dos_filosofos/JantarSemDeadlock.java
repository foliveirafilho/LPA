import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class JantarSemDeadlock{
    private static int QTDE_FILOSOFOS = 5;
    private static Semaphore hashis[] = new Semaphore[QTDE_FILOSOFOS];

    static class Filosofo extends Thread{
        private int numero;

        public Filosofo(int numero){
            this.numero = numero;

        }

        public void run(){
            while(true){
                if((this.numero % 2) == 0){
                    aguardar();
                    pegaHashi(this.numero);
                    pegaHashi((this.numero + 1) % QTDE_FILOSOFOS);
                    aguardar();
                    devolveHashi((this.numero + 1) % QTDE_FILOSOFOS);
                    devolveHashi(this.numero);

                }else{
                    aguardar();
                    pegaHashi((this.numero + 1) % QTDE_FILOSOFOS);
                    pegaHashi(this.numero);
                    aguardar();
                    devolveHashi(this.numero);
                    devolveHashi((this.numero + 1) % QTDE_FILOSOFOS);

                }

            }

        }

    }

    public static void pegaHashi(int hashi){
        try {
            hashis[hashi].acquire();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("Pegando o hashi " + hashi);

    }

    public static void devolveHashi(int hashi){
        hashis[hashi].release();

        System.out.println("Devolvendo o hashi " + hashi);

    }

    public static void aguardar(){
        Random random = new Random();

        try {
            Thread.sleep(random.nextInt(701) + 100);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(QTDE_FILOSOFOS);

        for (int i = 0; i < QTDE_FILOSOFOS; i++){
            hashis[i] = new Semaphore(1);

        }

        for (int i = 0; i < QTDE_FILOSOFOS; i++){
            executorService.submit(new Filosofo(i));

        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

}