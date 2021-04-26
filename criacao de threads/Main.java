public class Main {
    public static void main(String args[]){
        Thread relogio1 = new Thread(new Relogio(10, 10));
        Thread relogio2 = new Thread(new Relogio(20));
        Thread relogio3 = new Thread(new Relogio());

        relogio1.start();
        relogio2.start();
        relogio3.start();

    }
    
}
