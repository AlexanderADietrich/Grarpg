package game;
public class Commands {
    private Game game;
    public Commands(Game g){
        this.game = g;
    }
    public void parseCommand(String str){
        int b = str.length();
        int i;
        int d = (game.textOutput.getWidth()-1) / 10;
        //Wrapping Script. Needs Improving.
        for (i=0 ; i+d < b; i+= d){
            game.textOutput.append(str.substring(i, i+d) + "\n");
        }
        game.textOutput.append(str.substring(i, str.length()) + "\n");
        str = str.trim();
        
        if (str.startsWith("go")){
            str = str.substring(3, str.length());
            if (str.startsWith("right")){
                game.setActive(1, 0);
            } 
            else if (str.startsWith("left")){
                game.setActive(-1, 0);
            } 
            else if (str.startsWith("down")){
                game.setActive(0, 1);
            } 
            else if (str.startsWith("up")){
                game.setActive(0, -1);
            }
        }
    }
}
