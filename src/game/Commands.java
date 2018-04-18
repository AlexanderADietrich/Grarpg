package game;
public class Commands {
    private Game g;
    public Commands(Game g){
        this.g = g;
    }
    public void parseCommand(String str){
        int b = str.length();
        int i;
        for (i=0 ; i+19 < b; i+= 19){
            g.s.append(str.substring(i, i+19) + "\n");
        }
        g.s.append(str.substring(i, str.length()) + "\n");
        str = str.trim();
        if (str.startsWith("go")){
            str = str.substring(3, str.length());
            if (str.startsWith("right")){
                g.setActive(1, 0);
            } else if (str.startsWith("left")){
                g.setActive(-1, 0);
            } else if (str.startsWith("down")){
                g.setActive(0, 1);
            } else if (str.startsWith("up")){
                g.setActive(0, -1);
            }
        }
    }
}
