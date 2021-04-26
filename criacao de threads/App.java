import java.lang.Thread;

public class App {
    static class ContadorTempo{
        private int tick;

        public ContadorTempo(int tick){
            this.tick = tick;

        }

        public ContadorTempo(){
            this.tick = 0;

        }

        public void nextTick(){
            this.tick++;

        }

        public int getTick(){
            return this.tick;

        }

    }

    static class Relogio extends Thread{
        private ContadorTempo contadorTempo;
        private int qtdeTick;
        private int inicialTick;

        public Relogio(int inicialTick, int qtdeTick){
            this.contadorTempo = new ContadorTempo(inicialTick);
            this.inicialTick = inicialTick;
            this.qtdeTick = qtdeTick;

        }

        public Relogio(int qtdeTick){
            this.contadorTempo = new ContadorTempo();
            this.inicialTick = 0;
            this.qtdeTick = qtdeTick;

        }

        public Relogio(){
            this.contadorTempo = new ContadorTempo();
            this.inicialTick = 0;
            this.qtdeTick = 0;

        }

        public void run(){
            System.out.println("Tick: " + contadorTempo.getTick());

            for(int i = 0; i < qtdeTick; i++){
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

                contadorTempo.nextTick();

                System.out.println("Tick: " + contadorTempo.getTick());

            }

        }

    }

    public static void main(String args[]){
        Relogio relogio1 = new Relogio(10, 10);
        Relogio relogio2 = new Relogio(20);
        Relogio relogio3 = new Relogio(0);

        relogio1.start();
        relogio2.start();
        relogio3.start();

    }

}