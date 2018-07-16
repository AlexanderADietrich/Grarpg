package game;


/**
 *
 * @author voice
 */
public class Tile {
    public String imagePath;
    public int x;
    public int y;
    public String skillTraverse = "go";
    
    public Tile(String s, int x, int y){
        this.imagePath=s;
        this.x=x;
        this.y=y;
    }
}
