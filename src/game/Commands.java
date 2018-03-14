package game;
public class Commands {
    private Game g;
    public Commands(Game g){
        this.g = g;
    }
    public void parseCommand(String s){
        s = s.trim();
        if (s.startsWith("go")){
            s = s.substring(3, s.length());
            if (s.startsWith("right")){
                g.setActive(1, 0);
            } else if (s.startsWith("left")){
                g.setActive(-1, 0);
            } else if (s.startsWith("down")){
                g.setActive(0, -1);
            } else if (s.startsWith("up")){
                g.setActive(0, 1);
            }
        }
    }
}
