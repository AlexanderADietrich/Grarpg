
package game;

/**
 * Basic Entity Class  
 * @author Nathan Geddis
 */
public class Entity {
    private int xPos;
    private int yPos;
    private String name;

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
