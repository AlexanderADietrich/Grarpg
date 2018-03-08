package game;

import java.util.ArrayList;

/**
 *
 * @author voice
 */
public class Tile {
    public String imagePath;
    public int x;
    public int y;
    public Tile(String s, int x, int y){
        this.imagePath=s;
        this.x=x;
        this.y=y;
    }
    /*private ArrayList<Game.GameObject> list = new ArrayList<>();
    public void addActor(Game.GameObject a){
        list.add(a);
    }*/

    
}
