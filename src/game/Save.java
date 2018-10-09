
package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A class that controls the saving and loading of data.
 * @author Nathan Geddis 
 * @created 4/25/2018
 * @modified 10/8/2018
 */
public final class Save {
    
    /**
     * Creates a new save file with the naming convention Save_(Number)
     * @param text Text to be saved to the file
     * @param map A string representation of the game map
     * @return True if Save was successful, False if not.
     */
    public static boolean saveFile(String text, String map, Game g){
        // finds an empty save file
        map = map.substring(0, map.length()-2);
        int count = 0;
        boolean testFile = false;
        while (!testFile){
            File f = new File("Save_" + count + ".txt");
            if(f.exists() && !f.isDirectory()) { 
                count++; 
            } else
                testFile = true;
        }
        //creates save file
        File save = new File("Save_" + count + ".txt");
        PrintWriter w = null;
        try {
            w = new PrintWriter(save, "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //Save text to File
        w.println("#TEXT"); //Start of text output in save file
        String[] strs = text.split("\n");
        for (String s: strs){
            w.println(s);
        }
        
        w.println("#MAP");//Start of map data in save file
        w.println(map);
        
        w.println("#PLAYER");//Start of Player data in save file
        w.println(g.p.getName()); //Player Name
        w.println(g.p.level); //Player Level
        w.println(g.p.exp); //Player EXP 
        w.println(g.p.expNeeded); //Player EXP to next level
        w.println(g.p.getXPOS()); //Player x coordinate
        w.println(g.p.getYPOS()); //Player y coordinate
        w.println(g.p.getHP()); // Player current HP
        w.println(g.p.stamina); // Player current stamina
        w.println(g.p.regen); // Player stamina regen rate
        
        w.println("#PLAYERINVENTORY");//Start of Player Invnetory data in save file
        Iterator it = g.p.inventory.getInventory().entrySet().iterator();
        while (it.hasNext()) {
            Entry pair = (Entry)it.next();
            
            if (OneUseItem.class.isInstance(pair.getValue())){
                w.print('O'); // first char O corispondes to One Use Item
                OneUseItem temp = (OneUseItem) pair.getValue();
                w.print(temp.time + '/'); // value specific to One Use Item 
                writeItemData(temp, w); //Writes the common item data to save file
                
            }else if(EquipItem.class.isInstance(pair.getValue())){
                w.print('E'); // first char E corispondes to an equipable item
                EquipItem temp = (EquipItem) pair.getValue();
                for (int i : temp.buffs){ // value specific to Equip Item 
                    w.print(i +'/');
                }
                w.print(temp.equipped); // value specific to Equip Item
                w.print('/');
                writeItemData(temp, w); //Writes the common item data to save file
            }
            w.println(); //New line for Next item
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        //w.println("#PLAYERSKILLS");
        //Iterator 
        
        //w.println("#ENEMY");//Start of Enemy data in save file
        
        //w.close();
        return true;
    }
    
    /**
     * Loads a save file specified by the Load command
     * @param save The file to be Loaded
     * @param game Used to output loaded information to the game
     * @return True if load was successful, False if not.
     */
    public static Map loadFile(File save, Game game){
        String section = "";
        String mapString = "";
        Map loadedMap = null;
        int y = 0; // for counting rows in map
        //Load file into Scanner
        Scanner loader = null;
        try {
            loader = new Scanner(save);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
            return loadedMap;
        }
        //Put Loaded Data into Game
        while (loader.hasNextLine()){
            if (loader.hasNext("#TEXT") || loader.hasNext("#MAP")){
                section = loader.next();
            }
            if (section.equals("#TEXT"))
                game.textOutput.append(loader.nextLine()+ "\n");
            if (section.equals("#MAP"))
                mapString = mapString + loader.nextLine() +"\n";
            else {
                loader.nextLine();
            }
        }
        //System.out.println(mapString.length());
        loadedMap = new Map(mapString, game);
        
    loader.close();
    return loadedMap;    
    }
    
    /**
     * Writes to save file the item data common to all item types
     * @param i Item to save
     * @param w PrintWriter to write to save file
     */
    public static void writeItemData(Item i, PrintWriter w){
        w.print(i.name + '/');
        w.print(i.gp + '/');
        w.print(i.buff + '/');
        w.print(i.amountBuff + '/');
    }
}