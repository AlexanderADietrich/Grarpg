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
     * @return the current image in the cutscene. Should be overriden for
     * cutscenes with multiple possible 'paths.' Should never be called without
     * check().
     */
    public Image doCscTick() throws ArrayIndexOutOfBoundsException{
        return images[sentinel++];
    }
}
