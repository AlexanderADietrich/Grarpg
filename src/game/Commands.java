package game;
public class Commands {
    private Game game;
    public Commands(Game g){
        this.game = g;
    }
    public void parseCommand(String command){
        int cLength = command.length();
        int cPos;
        int wrap = (game.textOutput.getWidth()-1) / 10;
        //Wrapping Script. Needs Improving.
        for (cPos=0; cPos+wrap < cLength; cPos+= wrap){
            game.textOutput.append(command.substring(cPos, cPos+wrap) + "\n");
        }
        game.textOutput.append(command.substring(cPos, command.length()) + "\n");
        command = command.trim();
        
        if (command.startsWith("go")){
            command = command.substring(3, command.length());
            if (command.startsWith("right")){
                game.setActive(1, 0);
            } 
            else if (command.startsWith("left")){
                game.setActive(-1, 0);
            } 
            else if (command.startsWith("down")){
                game.setActive(0, 1);
            } 
            else if (command.startsWith("up")){
                game.setActive(0, -1);
            }
        }
    }
}
