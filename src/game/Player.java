package game;
import java.util.HashMap;
import java.util.Random;

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
        this.inventory.addInventory("SwimBottle", new OneUseItem(100, "swim", 10, 60, "SwimBottle"));
        this.inventory.addInventory("SWORD OF TOLEDO", new EquipItem(new int[] {2, 1, 0, 0, 3}, "SWORD OF TOLEDO"));
        
    }
    int stemp;
    public Random r = new Random();
    public String doFightTick(){
        String temp = previousFightCommand;
        previousFightCommand = "";
        if (temp != "") System.out.println(temp);
        if ("The Toledo Avocado".equals(temp)){
            return "1000000 as/The/Toledo/Avocado/Obliterates/Their/Soul";
        } 
        if ("slash".equals(temp)){
            stemp = this.getDamage(4);
            if (this.getDamage(4) > 0){
                return (stemp+this.getStat(0)) + " as/your/blade/dances/through/sinew";
            }
        }
        if ("hit".equals(temp)) {
            stemp = this.getStat(0);
            if (stemp > 14) 
                if (r.nextBoolean())
                    return (stemp+1) + " as/thunder/becomes/jealous/of/your/punch";
                else 
                    return (stemp+1) + " as/the/earth/shudders/at/your/might";
            else if (stemp > 11) 
                if (r.nextBoolean())
                    return (stemp+1) + " as/your/fist/whistles/through/the/air";
                else 
                    return (stemp+1) + " as/bones/crack/and/jostle";
            else if (stemp > 7)
                if (r.nextBoolean())
                    return (stemp+1) + " as/your/fist/collides/with/bone";
                else
                    return (stemp+1) + " with/crushing/force";
                
            else {
                if (r.nextBoolean())
                    return (stemp+1) + " with/a/satisfying/THWACK";
                else 
                    return (stemp+1) + " with/bruising/force";
            }
        }
        return temp;
    }
}
