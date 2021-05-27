import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App{
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void adquireLock(Lock lock1, Lock lock2) throws InterruptedException{
        while (true) {
			boolean pegouLock1 = false;
			boolean pegouLock2 = false;

			try {
				pegouLock1 = lock1.tryLock();
				pegouLock2 = lock2.tryLock();

			} finally {
				if (pegouLock1 && pegouLock2)
                    return;

				else if (pegouLock1)
                    lock1.unlock();

				else if (pegouLock2) 
                    lock2.unlock();

			}

			Thread.sleep(1);

		}

    }

    public static void liberaLock(Lock lock1, Lock lock2){
        lock1.unlock();
        lock2.unlock();

    }

    static class Thread1 extends Thread{
        public void run(){
            System.out.println("Thread1 tentando adquirir todos os locks...");

            try {
                adquireLock(lock1, lock2);
                System.out.println("Thread1 conseguiu todos os locks!");
                Thread.sleep(1000);
                liberaLock(lock1, lock2);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            System.out.println("Thread1 finalizada!");

        }

    }

    static class Thread2 extends Thread{
        public void run(){
            System.out.println("Thread2 tentando adquirir todos os locks...");

            try {
                adquireLock(lock2, lock1);
                System.out.println("Thread2 conseguiu todos os locks!");
                Thread.sleep(1000);
                liberaLock(lock2, lock1);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            System.out.println("Thread2 finalizada!");

        }
        
    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 5; i++)
            executorService.submit(new Thread1());

        for(int i = 0; i < 5; i++)
            executorService.submit(new Thread2());

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("Todas as threads realizaram suas tarefas.");

    }

}