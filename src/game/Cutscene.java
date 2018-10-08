package game;

import java.awt.Image;

/**
 * The Cutscene class stores an array of images to display in the "image area"
 * portion of the game screen. Some cutscenes will take in user input, and ther-
 * fore need the game object.
 * 
 * @author Alexander A Dietrich 10/8/18
 */
public abstract class Cutscene {
    public Image[] images;
    public int sentinel = 0;
    public Game game;
    
    /**
     * 
     * @param g, current game object
     * @param i, first || only list of images to show
     */
    public Cutscene(Game g, Image[] i){
        game=g;
        images=i;
    }
    
    /**
     * 
     * @return if the cutscene has more images to show. Should be overriden for
     * cutscenes with multiple possible 'paths.' Should always be called before
     * doCscTick().
     */
    public boolean check(){
        return sentinel < images.length;
    }
    
    /**
     * 
     * @return the current image in the cutscene. Should never be called without
     * check().
     */
    public Image doCscTick() throws ArrayIndexOutOfBoundsException{
        return images[sentinel++];
    }
}
