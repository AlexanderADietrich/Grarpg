
package game;

/**
 * Basic Entity Class  
 * @author Nathan Geddis
 * @contributor Alexander Dietrich, 4/20/18
 */
public class Entity {
    private int xPOS;
    private int yPOS;
    private double hp;
    private String name;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    private String imagePath;

    public int getXPOS() {
        return xPOS;
    }

    public void setXPOS(int xPOS) {
        this.xPOS = xPOS;
    }

    public int getYPOS() {
        return yPOS;
    }

    public void setYPOS(int yPOS) {
        this.yPOS = yPOS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHP(){
        return hp;
    }
    
    public void setHP(double hp){
        this.hp = hp;
    }
    
    /*
    FOR LATER; SOME ITEMS ALLOW ONE TO ATTACK WALLS/ USUALLY NON ATTACKABLE
    ENTITIES.
    public boolean changeHP(Entity e, double hp){
        if (hp < 0)
            if (e.canAttack(this)){
                this.hp += hp;
                return true;
            }
            else
                return false;
        else {
            this.hp += hp;
            return true;
        }
    }
    */
}
