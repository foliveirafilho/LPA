public class Relogio implements Runnable{
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
