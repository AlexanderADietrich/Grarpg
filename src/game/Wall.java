
package game;

/**
 * A Basic Wall tile
 * @author Nathan Geddis
 */
public class Wall extends Entity {
    
    public Wall(int xPos, int yPos){
        setxPos(xPos);
        setyPos(yPos);
        setName("Wall");
    }
    public Wall(int xPos, int yPos, String name){
        setxPos(xPos);
        setyPos(yPos);
        setName(name);
    }
    
}
