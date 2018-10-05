package game;
import java.util.HashMap;
import java.util.Random;

public class Player extends Entity{
    public Inventory inventory;
    public int level;
    public Skills skillChecker;
    public String previousFightCommand = "";
    public double stamina = 100; //Between 0 and 100;
    public int regen = 1;//Regen for Stamina
    public boolean lockout = false;
    
    public Player(int xPOS, int yPOS, String name, String address, int iSize, Game g){
        skillChecker = new Skills(this);
        this.setXPOS(xPOS);
        this.setYPOS(yPOS);
        this.setName(name);
        this.setHP(100);
        this.setImagePath(address);
        this.setStats(new int[] {6,5,5,5,10});
        
        inventory = new Inventory(iSize, g);
        this.inventory.addInventory("SwimBottle", new OneUseItem(100, "swim", 10, 60, "SwimBottle"));
        this.inventory.addInventory("SWORD OF TOLEDO", new EquipItem(new int[] {2, 1, 0, 0, 3}, "SWORD OF TOLEDO"));   
    }
    
    public boolean useStamina(int stamuse){
        stamina -= stamuse;
        if (stamina < 0){
            stamina = 0;
            lockout = true;
            return true;
        } else {
            return false;
        }
    }
    public boolean regenStamina(){
        for (int i = 0; i < regen; i++){
            stamina++;
        }
        if (stamina > 100){
            stamina = 100;
            lockout = false;
            return true;
        } else {
            return false;
        }
    }
    
    int stemp;
    double dtemp;
    public Random r = new Random();
    
    //0 = Str, 1 = Def, 2 = Int, 3 = Disc, 4 = Spd
    public double[] statMods        = {0, 0, 0, 0, 0};
    public boolean mod = false;
    
    @Override
    public String doFightTick(){
        String temp = previousFightCommand;
        previousFightCommand = "";
        
        
        //SECTION: Recursive Calls / Mods For Ease of Code~~~~~~~~~~~~~~~~~~~~~~
        if ("stab".equalsIgnoreCase(temp)){
            statMods = new double[]{0.75, 0, 0, 0, 0.25};
            previousFightCommand = "slash";
            mod = true;
            return doFightTick();
        }
        
        
        //SECTION: Main Commands~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        if ("The Toledo Avocado".equalsIgnoreCase(temp)){
            return "1000000 as/The/Toledo/Avocado/Obliterates/Their/Soul";
        }
        if ("slash".equals(temp)){
            if (this.getDamage(4) > 0){
                stemp = this.getDamage(4)+this.getStat(0);
                if (mod){
                    stemp = 0;
                    for (int i = 0; i < statMods.length; i++){
                        dtemp += 1.0*statMods[i]*this.getStat(i);
                        statMods[i] = 0;
                    }
                    dtemp += this.getDamage(4);
                    mod = false;
                }

                stemp = (int) (stemp + dtemp);
                dtemp = 0;
                return (stemp) + " as/your/blade/dances/through/sinew";
            }
        }
        if ("hit".equalsIgnoreCase(temp)) {
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
