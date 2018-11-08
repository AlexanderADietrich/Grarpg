package game;

import game.Tiles.Tile;
import game.Tiles.EntranceTile;
import game.Tiles.TreasureTile;
import java.io.File;
import java.util.Iterator;

public class Commands {
    public Game game;
    public Commands(Game g){
        this.game = g;
    }
    public void switchMap(int inpx, int inpy, Entity e, Map mnew){
        //Remove Entity
        game.m.currentChunk.removeEntity(e.getXPOS(), e.getYPOS());
        
        //Switch Maps
        Map temp = game.m;
        game.m=mnew;
        //Switch Chunks
        if (inpx > 0){
            mnew.currentChunk = mnew.chunks[temp.chunkY][0];
        } else if (inpy > 0){
            mnew.currentChunk = mnew.chunks[0][temp.chunkX];
        } else if (inpx < 0){
            mnew.currentChunk = mnew.chunks[temp.chunkY][mnew.chunks[0][0].entities.length-1];
        } else if (inpy < 0){
            mnew.currentChunk = mnew.chunks[mnew.chunks[0][0].entities.length-1][temp.chunkX];
        }
         
        //Re-Add Entity
        if (inpx > 0){
            game.m.currentChunk.addEntity(e, 0, e.getYPOS());
            e.setXPOS(0);
        }
        else if (inpx < 0){
            game.m.currentChunk.addEntity(e, mnew.chunks[0][0].entities.length-1, e.getYPOS());
            e.setXPOS(7);
        }
        else if (inpy > 0){
            game.m.currentChunk.addEntity(e, e.getXPOS(), 0);
            e.setYPOS(0);
        }
        else if (inpy < 0){
            game.m.currentChunk.addEntity(e, e.getXPOS(), mnew.chunks[0][0].entities.length-1);
            e.setYPOS(7);
        }
    }
    
    public boolean checkIfMapSwap(int inpx, int inpy, Entity e){
        if (game.m.chunkY+inpy > game.m.chunks.length-1){
            //*Switch Map
            if (game.mapy < game.mapGrid.length-1) switchMap(0, inpy, e, game.mapGrid[++game.mapy][game.mapx]);
            return true;
        } else if (game.m.chunkY+inpy < 0){
            //*
            if (game.mapy > 0) switchMap(0, inpy, e, game.mapGrid[--game.mapy][game.mapx]);
            return true;
        } else if (game.m.chunkX+inpx > game.m.chunks[0].length-1){
            //*
            if (game.mapx < game.mapGrid[0].length-1) switchMap(inpx, 0, e, game.mapGrid[game.mapy][++game.mapx]);
            return true;
        } else if (game.m.chunkX+inpx < 0){
            //*
            if (game.mapx > 0) switchMap(inpx, 0, e, game.mapGrid[game.mapy][--game.mapx]);
            return true;
        }
        return false;
    }
    
    
    public void switchChunk(int inpx, int inpy, Entity e){
        System.out.println(inpx + " " + inpy);
        
        if (checkIfMapSwap(inpx, inpy, e)) return;
        
        
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
    
    //0 = Str, 1 = Def, 2 = Int, 3 = Disc, 4 = Spd
    public double[] statMods        = {0, 0, 0, 0, 0};
    boolean mod = false;
    int mtemp;
    String defString = "go";

    
    public void parsePlayerCommand(String command, Player p){
        //Processing
        int cLength = command.length();
        command = command.trim();
        
        
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
            In order to aid the goal of getting up to 1400 commands, make
            command modifiers in order to recursively call 'advanced' 
            commands as stat-modified versions of 'primary' commands
        
        TODO: Add Stamina so that Running has a disadvantage
        */
        if (command.toLowerCase().startsWith("jog")){
            statMods[4] = 1.5;
            mod = true;
            parsePlayerCommand("go " + command.substring(4), p);
        }
        if (command.toLowerCase().startsWith("run")){
            statMods[4] = 1.75;
            mod = true;
            parsePlayerCommand("go " + command.substring(4), p);
        }
        if (command.toLowerCase().startsWith("dash")){
            statMods[4] = 2;
            mod = true;
            parsePlayerCommand("go " + command.substring(5), p);
        }
        if (command.toLowerCase().startsWith("rush")){
            statMods[4] = 2.25;
            mod = true;
            parsePlayerCommand("go " + command.substring(5), p);
        }
        if (command.toLowerCase().startsWith("sprint")){
            statMods[4] = 2.5;
            mod = true;
            parsePlayerCommand("go " + command.substring(7), p);
        }
        
        
        
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //Acronyms / Initialisms / Recursive Calls
        if (command.length() == 1){
            if (command.toLowerCase().startsWith("n")){
                parsePlayerCommand(defString + " up", p);
                return;
            }
            if (command.toLowerCase().startsWith("s")){
                parsePlayerCommand(defString + " down", p);
                return;
            }
            if (command.toLowerCase().startsWith("e")){
                parsePlayerCommand(defString + " right", p);
                return;
            }
            if (command.toLowerCase().startsWith("w")){
                parsePlayerCommand(defString + " left", p);
                return;
            }
        }
        if (command.toLowerCase().startsWith("equip") && command.length() < 6){
            parsePlayerCommand("Equipped: ", p);
            return;
        }
        
        game.append(wrap(command, cLength));
        
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //Main Commands
        
        /*
        'attack'  ;
        
        Somewhat unnecessary, as it will barely ever be used, yet being unable
        to do so renders the player feeling somewhat powerless.
        */
        if (command.toLowerCase().startsWith("attack up")){
            if (game.m.currentChunk.entities[game.p.getYPOS()+1][game.p.getXPOS()] != null)
                game.startFight(game.p, game.m.currentChunk.entities[game.p.getYPOS()+1][game.p.getXPOS()]);
        }
        if (command.toLowerCase().startsWith("attack left")){
            if (game.m.currentChunk.entities[game.p.getYPOS()][game.p.getXPOS()-1] != null)
                game.startFight(game.p, game.m.currentChunk.entities[game.p.getYPOS()][game.p.getXPOS()-1]);
        }
        if (command.toLowerCase().startsWith("attack down")){
            if (game.m.currentChunk.entities[game.p.getYPOS()-1][game.p.getXPOS()] != null)
                game.startFight(game.p, game.m.currentChunk.entities[game.p.getYPOS()-1][game.p.getXPOS()]);
        }
        if (command.toLowerCase().startsWith("attack right")){
            if (game.m.currentChunk.entities[game.p.getYPOS()][game.p.getXPOS()+1] != null)
                game.startFight(game.p, game.m.currentChunk.entities[game.p.getYPOS()][game.p.getXPOS()+1]);
        }
        
        
        if (command.toLowerCase().startsWith("show stamina")){
            game.viewStamina = true;
        }
        if (command.toLowerCase().startsWith("hide stamina")){
            game.viewStamina = false;
        }
        if (command.toLowerCase().startsWith("level up")){
            game.swapLevelUpState();
        }
        if (command.toLowerCase().startsWith("close ui")){
            if (game.inventory == true) game.swapInventoryState();
            if (game.levelup == true)  game.swapLevelUpState();
            game.viewStamina = false;
            game.mapActive = false;
        }
        if (command.toLowerCase().startsWith("default")){
            defString = command.substring(8);
            return;
        }
        if (command.toLowerCase().startsWith("inventory")){
            game.swapInventoryState();
            if (game.p.inventory.getiSize() == 1){ 
                game.append(game.p.inventory.getInventory().keySet().iterator().next() + "\n");
                return;
            }
            
            String retVal = "";
            for (String s : game.p.inventory.getInventory().keySet()){
                retVal = retVal + s + ",\n";
            }
            game.append(retVal);
        }
        if (command.toLowerCase().startsWith("grab")){
            for (Tile t : game.getPlayerAdjTiles()){
                //System.out.println(t.x + " " + t.y + " " + t.imagePath);
                if (TreasureTile.class.isInstance(t)){
                    //System.out.println("true");
                    TreasureTile copy = (TreasureTile) t;
                    for (Item e : copy.items){
                        game.p.inventory.getInventory().put(e.name, e);
                    }
                }
            }
        }
        if (command.toLowerCase().startsWith("print")){//Debugging Tool
            System.out.println("PLAYER:\t\t" + game.p.getXPOS() + "," + game.p.getYPOS());
            for (int i = 0; i < game.m.chunks.length; i++){
                for (int b = 0; b < game.m.chunks[i].length; b++)
                    if (game.m.chunks[i][b] == game.m.currentChunk) System.out.println("Chunk:\t\tX" + b + " Y" +i);
            }
            System.out.println("MChunk:\t\tX" + game.m.chunkX + " Y" + game.m.chunkY);
            Iterator i = game.m.currentChunk.fastEntities.iterator();
            Entity e;                
            while (i.hasNext()){                    
                e = (Entity) i.next();
                if (Player.class.isInstance(e)) System.out.println("CHUNKPLAYER:\t" + e.getXPOS() + "," + e.getYPOS());
            }
            System.out.println();
            System.out.println("STATS: ");
            for (int g : game.p.getStats()){
                System.out.print(g + " ");
            }
            System.out.println();
            
            System.out.println("DAMAGETYPES: ");
            for (int g : game.p.getDamageTypes()){
                System.out.print(g + " ");
            }
            System.out.println();
        }
        if (command.toLowerCase().startsWith("enter dungeon")){
            if (EntranceTile.class.isInstance(game.getPlayerTile())) 
                game.switchMapT((EntranceTile) game.getPlayerTile());
        }
        if (command.toLowerCase().startsWith("Equipped:")){
            for (Item e : p.inventory.getInventory().values()){
                if (EquipItem.class.isInstance(e)){
                    if (((EquipItem) e).equipped){
                        game.append(wrap("\t" + e.name + ",\n", cLength));
                    }
                }
            }
        }
        if (command.toLowerCase().startsWith("unequip")){
            Item item = p.inventory.getInventory().get(command.substring(8, command.length()));
            EquipItem eqitem;
            if (item != null){
                if (EquipItem.class.isInstance(item)){
                    eqitem = (EquipItem) item;
                    if (eqitem.equipped == true){
                        for (int i = 0; i < 5; i++){
                            p.getDamageTypes()[i] -= eqitem.buffs[i];
                        }
                        eqitem.equipped = false;
                    } else {
                        game.append(wrap("Not Equipped", 12));
                    }
                }
            }
        }
        if (command.toLowerCase().startsWith("equip")){
            Item item = p.inventory.getInventory().get(command.substring(6, command.length()));
            EquipItem eqitem;
            if (item != null){
                if (EquipItem.class.isInstance(item)){
                    eqitem = (EquipItem) item;
                    if (eqitem.equipped == false) {
                        for (int i = 0; i < 5; i++){
                            p.getDamageTypes()[i] += eqitem.buffs[i];
                        }
                        eqitem.equipped=true;
                    } else {
                        game.append(wrap("Already Equipped", 16));
                    }
                }
            }
            return;
        }
        if (command.toLowerCase().startsWith("use")){
            Item item = p.inventory.getInventory().get(command.substring(4, command.length()));
            OneUseItem oneuse;
            if (item != null){
                if(OneUseItem.class.isInstance(item)) {
                    oneuse = (OneUseItem) item;
                    p.skillChecker.addBuff(oneuse.buff, oneuse.amountBuff, oneuse.time);
                }
            }
        }
        if (command.toLowerCase().startsWith("i'm")){
                game.p.setName(command.substring(4, command.length()));
                game.append("You're now \n" + 
                command.substring(4, command.length()) + "\n");
        } 
        if (command.toLowerCase().startsWith("map")){ 
            game.mapActive = !game.mapActive;  
        }
        /*
        __-__
        -_O_+
        __+__
        */
        if (command.toLowerCase().startsWith("go") 
            && p.skillChecker.getSkillLevel("go") > 0){
                command = command.substring(3, command.length());
                game.makeSound(2);
                if (command.toLowerCase().startsWith("right")){
                    
                    //Switches Chunks
                    if (game.p.getXPOS() + 1 > 7){
                        //if tile on other chunk can be traversed
                        if (game.m.chunkX+1 < game.m.chunks[0].length){
                            if (p.skillChecker.getSkillLevel(
                                    game.m.chunks[game.m.chunkY][game.m.chunkX+1].tiles[p.getYPOS()][0].skillTraverse) > 0){}
                            else return;
                        }
                        
                        if (game.m.chunkX < game.m.width / 8 && game.mapx < game.mapGrid[0].length)
                        switchChunk(1, 0, p);
                        
                        return;
                    }
                    
                    if (mod){
                        mtemp = 0;
                        for (int i = 0; i < 5; i++){
                            if (i == 4) mtemp += p.getStat(i)*statMods[i];
                            statMods[i] = 0;
                        }
                        game.m.currentChunk.updateLoc(p, 1, 0, 250-(mtemp*5));
                        mod = false;
                    } else
                        game.m.currentChunk.updateLoc(p, 1, 0, 250-(p.getStat(4)*5));
                } 
                else if (command.toLowerCase().startsWith("left")){
                    
                    //Switches Chunks
                    if (game.p.getXPOS() - 1 < 0){
                        
                        //if tile on other chunk can be traversed
                        if (game.m.chunkX-1 >= 0){
                            if (p.skillChecker.getSkillLevel(
                                    game.m.chunks[game.m.chunkY][game.m.chunkX-1].tiles[p.getYPOS()][7].skillTraverse) > 0){}
                            else return;
                        }
                        if (game.m.chunkX > 0 || game.mapx > 0)
                            switchChunk(-1, 0, p);
                        return;
                    }
                    
                    if (mod){
                        mtemp = 0;
                        for (int i = 0; i < 5; i++){
                            if (i == 4) mtemp += p.getStat(i)*statMods[i];
                            statMods[i] = 0;
                        }
                        game.m.currentChunk.updateLoc(p, -1, 0, 250-(mtemp*5));
                        mod = false;
                    } else
                        game.m.currentChunk.updateLoc(p, -1, 0, 250-(p.getStat(4)*5));
                } 
                else if (command.toLowerCase().startsWith("down")){
                    //Switches Chunks
                    if (game.p.getYPOS() + 1 > 7 ){
                        //if tile on other chunk can be traversed
                        if (game.m.chunkY+1 < game.m.chunks.length){
                            if (p.skillChecker.getSkillLevel(
                                game.m.chunks[game.m.chunkY+1][game.m.chunkX].tiles[0][p.getXPOS()].skillTraverse) > 0){}
                            else return;
                        }
                        
                        if (game.m.chunkY < game.m.height / 8 || game.mapy < game.mapGrid.length)
                            switchChunk(0, 1, p);
                        return;
                    }
                    
                    if (mod){
                        mtemp = 0;
                        for (int i = 0; i < 5; i++){
                            if (i == 4) mtemp += p.getStat(i)*statMods[i];
                            statMods[i] = 0;
                        }
                        game.m.currentChunk.updateLoc(p, 0, 1, 250-(mtemp*5));
                        mod = false;
                    } else
                        game.m.currentChunk.updateLoc(p, 0, 1, 250-(p.getStat(4)*5));
                } 
                else if (command.toLowerCase().startsWith("up")){
                    //Switches Chunks
                    if (game.p.getYPOS() - 1 < 0){
                        //if tile on other chunk can be traversed
                        if (game.m.chunkY-1 >= 0){
                            if (p.skillChecker.getSkillLevel(
                                    game.m.chunks[game.m.chunkY-1][game.m.chunkX].tiles[7][p.getXPOS()].skillTraverse) > 0){}
                            else return;
                        }

                        if (game.m.chunkY > 0 || game.mapy > 0)
                            switchChunk(0, -1, p);
                        return;
                    }
                    if (game.m.chunkY == 0 && game.mapy > 0){
                        switchChunk(0, -1, p);
                    }
                    
                    if (mod){
                        mtemp = 0;
                        for (int i = 0; i < 5; i++){
                            if (i == 4) mtemp += p.getStat(i)*statMods[i];
                            statMods[i] = 0;
                        }
                        game.m.currentChunk.updateLoc(p, 0, -1, 250-(mtemp*5));
                        mod = false;
                    } else
                        game.m.currentChunk.updateLoc(p, 0, -1, 250-(p.getStat(4)*5));
                }
        }
        
        //test
        //Saves the Game. Latter add ability to specify file name.
        if (command.toLowerCase().startsWith("save")){
            //System.out.println(game.textOutput.getText())
            game.mapToString();
            if (Save.saveFile(game.textOutput.getText(),game.worldMap, game ))
                game.append("Save Successful");
            else
                game.append("Save Failed");
        } 
        
        //Loads the Game, Type file name after Load
        if (command.toLowerCase().startsWith("load")){
            command = command.substring(5, command.length()-1) + ".txt";
            File f = new File(new File("").getAbsoluteFile() + "\\" + command);
            Map temp = Save.loadFile(f, game);
            if (temp != null){
                game.append("Load Successful\n");
                
                //Re-Initiate the Game
                game.reinit(temp);
            }else
                game.append("Load Failed\n");
        } 
        
        //Starts Game form Main Menu
        if (command.toLowerCase().startsWith("start game")){
            game.running = true;
            game.append("Who are you?\n");
        }
    }
}
