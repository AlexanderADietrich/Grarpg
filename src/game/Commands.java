package game;

import java.io.File;
import java.util.Iterator;

public class Commands {
    public Game game;
    public Commands(Game g){
        this.game = g;
    }
    
    public void switchChunk(int inpx, int inpy, Entity e){
        //Remove Entity
        game.m.currentChunk.removeEntity(e.getXPOS(), e.getYPOS());

        //Switch Chunks
        game.m.currentChunk = game.m.chunks[game.m.chunkY+inpy][game.m.chunkX+inpx];
        game.m.chunkX+=inpx;
        game.m.chunkY+=inpy;
        game.m.currentChunk.passGame(game);
        //System.out.println("IS IT NULL? " + (game == null));
        

        //Re-Add Entity
        if (inpx > 0){
            game.m.currentChunk.addEntity(e, 0, e.getYPOS());
            e.setXPOS(0);
        }
        else if (inpx < 0){
            game.m.currentChunk.addEntity(e, 7, e.getYPOS());
            e.setXPOS(7);
        }
        else if (inpy > 0){
            game.m.currentChunk.addEntity(e, e.getXPOS(), 0);
            e.setYPOS(0);
        }
        else if (inpy < 0){
            game.m.currentChunk.addEntity(e, e.getXPOS(), 7);
            e.setYPOS(7);
        }
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
        
        if (command.startsWith("inventory")){
            game.swapInventoryState();
            if (game.p.inventory.getiSize() == 1){ 
                game.textOutput.append(game.p.inventory.getInventory().keySet().iterator().next() + "\n");
                return;
            }
            
            String retVal = "";
            for (String s : game.p.inventory.getInventory().keySet()){
                retVal = retVal + s + ",\n";
            }
            game.textOutput.append(retVal);
        }
        if (command.startsWith("grab")){
            for (Tile t : game.getPlayerAdjTiles()){
                //System.out.println(t.x + " " + t.y + " " + t.imagePath);
                if (TreasureTile.class.isInstance(t)){
                    //System.out.println("true");
                    TreasureTile copy = (TreasureTile) t;
                    for (Item e : copy.items){
                        game.p.inventory.getInventory().put(game.nameGen.getName(e.buff), e);
                    }
                }
            }
        }
        if (command.startsWith("PRINT")){//Debugging Tool
            System.out.println("PLAYER:\t\t" + game.p.getXPOS() + "," + game.p.getYPOS());
            Iterator i = game.m.currentChunk.fastEntities.iterator();
            Entity e;                
            while (i.hasNext()){                    
                e = (Entity) i.next();
                if (Player.class.isInstance(e)) System.out.println("CHUNKPLAYER:\t" + e.getXPOS() + "," + e.getYPOS());
            }
        }
        if (command.startsWith("Enter Dungeon")){
            if (EntranceTile.class.isInstance(game.getPlayerTile())) 
                game.switchMap((EntranceTile) game.getPlayerTile());
        }
        if (command.startsWith("Use")){
            Item item = p.inventory.getInventory().get(command.substring(4, command.length()-1));
            OneUseItem oneuse;
            if (item != null){
                if(OneUseItem.class.isInstance(item)) {
                    oneuse = (OneUseItem) item;
                    p.skillChecker.addBuff(oneuse.buff, oneuse.amountBuff, oneuse.time);
                }
            }
        }
        if (command.startsWith("I'm")){
                game.p.setName(command.substring(4, command.length()));
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
                game.makeSound(2);
                if (command.startsWith("right")){
                    
                    //Switches Chunks
                    if (game.p.getXPOS() + 1 > 7 && game.m.chunkX < game.m.width / 8){
                        //if tile on other chunk can be traversed
                        if (p.skillChecker.getSkillLevel(
                                game.m.chunks[game.m.chunkY][game.m.chunkX+1].tiles[p.getYPOS()][0].skillTraverse) > 0){}
                        else return;
                        switchChunk(1, 0, p);
                        
                        return;
                    }
                    
                    game.m.currentChunk.updateLoc(p, 1, 0, 250-(p.getStat(4)*5));
                } 
                else if (command.startsWith("left")){
                    
                    //Switches Chunks
                    if (game.p.getXPOS() - 1 < 0 && game.m.chunkX > 0){
                        //if tile on other chunk can be traversed
                        if (p.skillChecker.getSkillLevel(
                                game.m.chunks[game.m.chunkY][game.m.chunkX-1].tiles[p.getYPOS()][7].skillTraverse) > 0){}
                        else return;
                        
                        switchChunk(-1, 0, p);
                        return;
                    }
                    
                    game.m.currentChunk.updateLoc(p, -1, 0, 250-(p.getStat(4)*5));
                } 
                else if (command.startsWith("down")){
                    
                    //Switches Chunks
                    if (game.p.getYPOS() + 1 > 7 && game.m.chunkY < game.m.height / 8){
                        //if tile on other chunk can be traversed
                        if (p.skillChecker.getSkillLevel(
                                game.m.chunks[game.m.chunkY+1][game.m.chunkX].tiles[0][p.getXPOS()].skillTraverse) > 0){}
                        else return;
                        
                        switchChunk(0, 1, p);
                        return;
                    }
                    
                    game.m.currentChunk.updateLoc(p, 0, 1, 250-(p.getStat(4)*5));
                } 
                else if (command.startsWith("up")){
                    
                    //Switches Chunks
                    if (game.p.getYPOS() - 1 < 0 && game.m.chunkY > 0){
                        //if tile on other chunk can be traversed
                        if (p.skillChecker.getSkillLevel(
                                game.m.chunks[game.m.chunkY-1][game.m.chunkX].tiles[7][p.getXPOS()].skillTraverse) > 0){}
                        else return;
                        
                        switchChunk(0, -1, p);
                        return;
                    }
                    
                    game.m.currentChunk.updateLoc(p, 0, -1, 250-(p.getStat(4)*5));
                }
        }
        
        
        
        
        
        
        //test
        //Saves the Game. Latter add ability to specify file name.
        if (command.startsWith("Save")){
            //System.out.println(game.textOutput.getText())
            game.mapToString();
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
                
                //Re-Initiate the Game
                game.reinit(temp);
            }else
                game.textOutput.append("Load Failed\n");
        } 
        
        //Starts Game form Main Menu
        if (command.startsWith("Start Game")){
            game.running = true;
            game.textOutput.append("Who are you?\n");
        }
    }
}
