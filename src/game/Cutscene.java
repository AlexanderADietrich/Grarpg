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
    public Cutscene(int len){
        images = new Image[len];
    }
    
    /**
     * 
     * @return if the cutscene has more images to show. Should be overriden for
     * cutscenes with multiple possible 'paths.' Should always be called before
     * doCscTick().
     */
    public boolean check(){
        System.out.println("sent + " + sentinel);
        return sentinel < images.length;
    }
    
    public Image getImage() throws ArrayIndexOutOfBoundsException{
        return images[sentinel];
    }
    
    public void doCscTick(){
        sentinel++;
    }
}
