
package game;

/**
 * A Basic Wall tile
 * @author Nathan Geddis
 */
public class Wall extends Entity {
    
    public Wall(int xPOS, int yPOS){
        setXPOS(xPOS);
        setYPOS(yPOS);
        setName("Wall");
        setHP(1000);
    }
    public Wall(int xPOS, int yPOS, String name){
        setXPOS(xPOS);
        setYPOS(yPOS);
        setName(name);
        setHP(1000);
    }
    public Wall(int xPOS, int yPOS, String name, double hp){
        setXPOS(xPOS);
        setYPOS(yPOS);
        setName(name);
        setHP(hp);
    }
    
}
