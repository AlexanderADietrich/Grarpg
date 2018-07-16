
package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A class that controls the saving and loading of data.
 * @author Nathan Geddis 
 * @created 4/25/2018
 * @modified 4/25/2015
 */
public final class Save {
    
    /**
     * Creates a new save file with the naming convention Save_(Number)
     * @param text Text to be saved to the file
     * @param map A string representation of the game map
     * @return True if Save was successful, False if not.
     */
    public static boolean saveFile(String text, String map){
        // finds an empty save file
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
        
        w.println("#MAP");//Start of map in save file
        w.println(map);
        
        w.close();
        return true;
    }
    
    /**
     * Loads a save file specified by the Load command
     * @param save The file to be Loaded
     * @param game Used to output loaded information to the game
     * @return True if load was successful, False if not.
     */
    public static boolean loadFile(File save, Game game){
        game.running = true;
        String section = "";
        int y = 1; // for counting rows in map
        //Load file into Scanner
        Scanner loader = null;
        try {
            loader = new Scanner(save);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //Put Loaded Data into Game
        while (loader.hasNextLine()){
            if (loader.hasNext("#TEXT") || loader.hasNext("#MAP")){
                section = loader.next();
            }
            if (section.equals("#TEXT"))
                game.textOutput.append(loader.nextLine()+ "\n");
            if (section.equals("#MAP")){
                System.out.println("");
                String row = loader.nextLine();
                for (int x = 0; x < row.length() ; x++){
                    switch (row.charAt(x)) {
                        case '_':
                            game.m.tiles[x][y] = new Tile ("images/defaultTile.png", x, y);
                            System.out.print("_");
                            break;
                        case 'm':
                            game.m.tiles[x][y] = new MountainTile ("images/mountainTile.png", x, y);
                            System.out.print("m");
                            break;
                        case 'w':
                            game.m.tiles[x][y] = new WaterTile ("images/waterTile.png", x, y);
                            System.out.print("w");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        
        System.out.println(Arrays.toString(game.m.tiles));
        loader.close();
        return true;
    }
}