package game;
import java.util.HashMap;

public class Player extends Entity{
    public HashMap<String, Item> inventory = new HashMap<>();
    public int level;
    public Skills skillChecker;
    public String previousFightCommand = "";
    
    public Player(int xPOS, int yPOS, String name, String address){
        skillChecker = new Skills(this);
        this.setXPOS(xPOS);
        this.setYPOS(yPOS);
        this.setName(name);
        this.setHP(100);
        this.setImagePath(address);
    }
    
    public String doFightTick(){
        String temp = previousFightCommand;
        previousFightCommand = "";
        return temp;
    }
}
