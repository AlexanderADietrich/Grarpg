package game;

import java.io.File;
import java.util.Arrays;

public class Commands {
    public Game game;
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
        //Fast Movement
        //Make a setting for WASD
        if (command.length() == 2){
            if (command.startsWith("n")){
                parsePlayerCommand("go up", p);
                return;
            }
            if (command.startsWith("s")){
                parsePlayerCommand("go down", p);
                return;
            }
            if (command.startsWith("e")){
                parsePlayerCommand("go right", p);
                return;
            }
            if (command.startsWith("w")){
                parsePlayerCommand("go left", p);
                return;
            }
        }
        game.textOutput.append(command);
        
        if (command.startsWith("I'm")){
                game.p = new Player(game.activeXPOS, game.activeYPOS, 
                command.substring(4, command.length()), "images/GoodGuy.png");
                game.textOutput.append("You're now \n" + 
                command.substring(4, command.length()) + "\n");
        }
        if (command.startsWith("Map")){ 
            game.mapActive = !game.mapActive;  
        }
        /*
        __-__
        -_O_+
        __+__
        */
        if (command.startsWith("go") 
            && p.skillChecker.getSkillLevel("go") > 0){
                command = command.substring(3, command.length());
                if (command.startsWith("right")){
                    if (game.p.getXPOS() + 1 > 7 && game.m.chunkX < 7){
                        game.m.currentChunk = game.m.chunks[game.m.chunkX+1][game.m.chunkY];
                        game.m.currentChunk.entities[p.getYPOS()][0] = p;
                        game.m.chunkX++;
                        p.setXPOS(0);
                        return;
                    }
                    game.m.currentChunk.updateLoc(p, 1, 0);
                } 
                else if (command.startsWith("left")){
                    if (game.p.getXPOS() - 1 < 0 && game.m.chunkX > 0){
                        game.m.currentChunk = game.m.chunks[game.m.chunkY][game.m.chunkX-1];
                        game.m.currentChunk.entities[p.getYPOS()][7] = p;
                        game.m.chunkX--;
                        p.setXPOS(7);
                        return;
                    }
                    game.m.currentChunk.updateLoc(p, -1, 0);
                } 
                else if (command.startsWith("down")){
                    if (game.p.getYPOS() + 1 > 7 && game.m.chunkY < 7){
                        game.m.currentChunk = game.m.chunks[game.m.chunkY+1][game.m.chunkX];
                        game.m.currentChunk.entities[0][p.getXPOS()] = p;
                        game.m.chunkY++;
                        p.setYPOS(0);
                        return;
                    }
                    game.m.currentChunk.updateLoc(p, 0, 1);
                } 
                else if (command.startsWith("up")){
                    if (game.p.getYPOS() - 1 < 0 && game.m.chunkY > 0){
                        game.m.currentChunk = game.m.chunks[game.m.chunkX][game.m.chunkY-1];
                        game.m.currentChunk.entities[7][p.getXPOS()] = p;
                        game.m.chunkY--;
                        p.setYPOS(7);
                        return;
                    }
                    game.m.currentChunk.updateLoc(p, 0, -1);
                }
        }
        
        
        
        
        
        
        //test
        //Saves the Game. Latter add ability to specify file name.
        if (command.startsWith("Save")){
            //System.out.println(game.textOutput.getText())
            if (Save.saveFile(game.textOutput.getText(),game.worldMap))
                game.textOutput.append("Save Successful");
            else
                game.textOutput.append("Save Failed");
        } 
        
        //Loads the Game, Type file name after Load
        if (command.startsWith("Load")){
            command = command.substring(5, command.length()-1) + ".txt";
            File f = new File(new File("").getAbsoluteFile() + "\\" + command);
            Map temp = Save.loadFile(f, game);
            if (temp != null){
                game.textOutput.append("Load Successful\n");
                game.m = temp;
            }else
                game.textOutput.append("Load Failed\n");
            
            System.out.println(Arrays.toString(game.m.tiles));
        } 
        
        //Starts Game form Main Menu
        if (command.startsWith("Start Game")){
            game.running = true;
            game.textOutput.append("Who are you?\n");
        }
    }
}
