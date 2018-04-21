package game;
import java.util.HashMap;

public class Player extends Entity{
    private HashMap<String, Item> inventory = new HashMap<>();
    private int level;
    private Skills skillChecker;
    
    public Player(int xPOS, int yPOS, String name){
        this.setXPOS(xPOS);
        this.setYPOS(yPOS);
        this.setName(name);
        this.setHP(100);
    }
}
