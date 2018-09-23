package game;
import java.util.HashMap;

public class Player extends Entity{
    public Inventory inventory;
    public int level;
    public Skills skillChecker;
    public String previousFightCommand = "";
    
    public Player(int xPOS, int yPOS, String name, String address, int iSize, Game g){
        skillChecker = new Skills(this);
        this.setXPOS(xPOS);
        this.setYPOS(yPOS);
        this.setName(name);
        this.setHP(100);
        this.setImagePath(address);
        this.setStats(new int[] {5,5,5,5,5});
        inventory = new Inventory(iSize, g);
        this.inventory.addInventory("SwimBottle", new OneUseItem(100, "swim", 10, 60));
    }
    
    public String doFightTick(){
        String temp = previousFightCommand;
        previousFightCommand = "";
        if ("The Toledo Avocado".equals(temp)){
            return "1000000 as/The/Toledo/Avocado/Obliterates/Your/Soul";
        }
        return temp;
    }
}
