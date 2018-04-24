package game;
public class Commands {
    private Game game;
    public Commands(Game g){
        this.game = g;
    }
    public String wrap(String command, Integer cLength){
        int cPos;
        int wrap = (game.textOutput.getWidth()-1) / 10;
        String newString = "";
        //Wrapping Script. Needs Improving.
        for (cPos=0; cPos+wrap < cLength; cPos+= wrap){
            newString += (command.substring(cPos, cPos+wrap) + "\n");
        }
        return newString + (command.substring(cPos, command.length()) + "\n");
    }
    public void parsePlayerCommand(String command, Player p){
        int cLength = command.length();
        command = command.trim();
        command = wrap(command, cLength);
        game.textOutput.append(command);
        if (command.startsWith("I'm")){
                game.p = new Player(game.activeXPOS, game.activeYPOS, 
                command.substring(4, command.length()), "images/GoodGuy.png");
                game.textOutput.append("You're now \n" + 
                command.substring(4, command.length()) + "\n");
        }
        if (command.startsWith("go") 
            && p.skillChecker.getSkillLevel("go") > 0){
                command = command.substring(3, command.length());
                if (command.startsWith("right")){
                    game.currentChunk.updateLoc(p, 1, 0);
                } 
                else if (command.startsWith("left")){
                    game.currentChunk.updateLoc(p, -1, 0);
                } 
                else if (command.startsWith("down")){
                    game.currentChunk.updateLoc(p, 0, 1);
                } 
                else if (command.startsWith("up")){
                    game.currentChunk.updateLoc(p, 0, -1);
                }
        }
    }
}
