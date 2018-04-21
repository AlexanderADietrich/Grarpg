package game;
import java.util.HashMap;

public class Player extends Entity{
    public HashMap<String, Item> inventory = new HashMap<>();
    public int level;
    public Skills skillChecker;
    
    public Player(int xPOS, int yPOS, String name){
        this.setXPOS(xPOS);
        this.setYPOS(yPOS);
        this.setName(name);
        this.setHP(100);
    }
}
