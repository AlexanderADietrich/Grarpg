package game;
public class Commands {
    private Game g;
    public Commands(Game g){
        this.g = g;
    }
    public void parseCommand(String s){
        if (s.trim().startsWith("go")){
            if (s.trim().substring(3, s.trim().length()).startsWith("right")){
                g.setActive(1, 0);
            }
        }
    }
}
