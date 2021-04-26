public class ContadorTempo {
    private int tick;

    public ContadorTempo(int tick){
        this.tick = tick;

    }

    public ContadorTempo(){
        this.tick = 0;

    }

    public int getTick(){
        return this.tick;

    }

    public void setTick(int tick){
        this.tick = tick;

    }

    public void nextTick(){
        this.tick++;

    }
    
}
