package game;
public class Commands {
    private Game g;
    public Commands(Game g){
        this.g = g;
    }
    public void parseCommand(String s){
        if (s.trim().startsWith("go")){
            if (s.substring(3, s.length()).startsWith("right")){
                g.setActive(1, 0);
            }
        }
    }
}
