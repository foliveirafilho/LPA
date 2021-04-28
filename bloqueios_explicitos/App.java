public class App{
    static int MAX_PRODUCOES = 10;

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

        public synchronized void retirar(){
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
            for(int i = 0; i < MAX_PRODUCOES; i++){
                deposito.armazenar();

                try {
                    Thread.sleep(tempoProducao * 1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

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
            for(int i = 0; i < MAX_PRODUCOES; i++){
                deposito.retirar();

                try {
                    Thread.sleep(tempoProducao * 1000);
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }

        }
        
    }

    public static void main(String args[]){
        Deposito deposito = new Deposito(8, 60);

        Consumidor consumidor1 = new Consumidor(deposito, 1);
        Consumidor consumidor2 = new Consumidor(deposito, 2);
        Consumidor consumidor3 = new Consumidor(deposito, 3);

        Thread produtor1 = new Thread(new Produtor(deposito, 2));
        Thread produtor2 = new Thread(new Produtor(deposito, 3));
        Thread produtor3 = new Thread(new Produtor(deposito, 1));

        consumidor1.start();
        consumidor2.start();
        consumidor3.start();

        produtor1.start();
        produtor2.start();
        produtor3.start();

        try {
            consumidor1.join();
            consumidor2.join();
            consumidor3.join();
            
            produtor1.join();
            produtor2.join();
            produtor3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

}