
package game;

import java.util.Arrays;
import java.util.Objects;

/**
 * Basic Entity Class  
 * @author Nathan Geddis
 * @contributor Alexander Dietrich, 4/20/18
 */
public class Entity {
    public Skills skillChecker;
    public boolean lockout;
    
    
    public Trigger tSlot = null;
    private int xPOS;
    private int yPOS; 
    private double hp; // could be put in stats if changed to int. Not sure why it is double
    
    // Index 0 = Strength, Index 1 = Defence, Index 2 = Intelligence, Index 3 = Discipline, Index 4 = Speed
    private int[] stats = new int[4];
    
    /*
    Damage Types for Weapons, 
    
    index 0 = blunt, index 1 = shield (not damage, yet useful for armor),
    index 2 = magic, index 3 = chi/special, index 4 = ranged/bladed
    */
    private int[] damageTypes = new int[5];
    
    private String name;
    
    /**
     * 
     * @param i
     * @return whether the entity has stamina left. Default is infinite stamina.
     */
    public boolean useStamina(int i){return false;}

    /* TIMER:
    movement/animation controller:
        currentMax              =0 not animating
        currentMax              >0 animating
        amount between tiles    = (double) (time left/max time)
        time left               = currentMax - (current time - initTime)
        time left as percent    = (currentTime - initTime)/currentMax
            as the maximum for (currentTime - initTime) due to the if stat-
            ement on the fourth line of check() is equal to currentMax.
    */
    public class Timer{
        public Timer(){}
        public boolean moving = false;
        public long initTime = System.currentTimeMillis();  //ms
        public long currentTime;                            //ms
        public double currentMax = 0;                       //ms
        public int dx = 0;
        public int dy = 0;
        /*
        returns 0 if not animating, returns -1.0 if done animating, >0 else
        */
        public double check(){ 
            //System.out.println("CURRENTMAX\t"+currentMax);
            //if (currentMax > 0) System.out.println("retVal    \t"+(1.0-((currentTime - initTime)/currentMax)));
            
            if (currentMax == 0) return 0;
            
            currentTime = System.currentTimeMillis();
            //System.out.println("LIFETIME  \t" + (currentTime - initTime));
            if (currentTime - initTime > currentMax){
                //if the timer has run out then reset/move*
                moving = false;
                currentMax = 0;
                return -1.0; //used to communicate "finish movement"
            }

            return ((currentTime - initTime)/currentMax);
        }
    }
    public Timer timer = new Timer();
    
    public void setAni(int currentMax, int dx, int dy){
        timer.moving        = true;
        timer.dx            = dx;
        timer.dy            = dy;
        timer.initTime      = System.currentTimeMillis();
        timer.currentMax    = currentMax;
    }
   
    
    @Override
    public boolean equals(Object e){
        if (e.getClass().isInstance(this)){
            Entity b = (Entity) e;
            if (b.getXPOS() == this.getXPOS()){
                if (b.getYPOS() == this.getYPOS()){
                    if (b.name.equals(this.name)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.skillChecker);
        hash = 13 * hash + (this.lockout ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.tSlot);
        hash = 13 * hash + this.xPOS;
        hash = 13 * hash + this.yPOS;
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.hp) ^ (Double.doubleToLongBits(this.hp) >>> 32));
        hash = 13 * hash + Arrays.hashCode(this.stats);
        hash = 13 * hash + Arrays.hashCode(this.damageTypes);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.timer);
        hash = 13 * hash + Objects.hashCode(this.imagePath);
        return hash;
    }
    
    public String doFightTick(){
        return "1 DEFAULTDAMAGECAUSE";
    }
    
    public void doTick(){
        
    }
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
    
    /**
    * Returns all the entities stats as an Array
    * @return int[] array containing all the entity's stats
    */
    public int[] getStats() {
        return stats;
    }
    
    /**
    * Returns a single stat from the stats Array
    * @param index index corresponding to desired stat
    * @return int the stat to be retrieved
    */
    public int getStat(int index) {
        try {
            return stats[index];
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }
    
    /**
    * Sets the Stat Array to be equal to a new array of updated stats.
    * @param stats int[] with each index corresponding to one of the entity's stats 
    */
    public void setStats(int[] stats) {
        this.stats = stats;
    }
    
    /**
    * Sets a single stat in the stats array to be equal to a new number. 
    * @param index the index of the stat to be changed
    * @param stat the new value or the stat to be changed
    */
    public void setStat(int index, int stat){
        this.stats[index] = stat;
    }
    
    public int getDamage(int index){
        if (index < 5){
            return damageTypes[index];
        }
        return -1;
    }
    
    public int[] getDamageTypes() {
        return damageTypes;
    }

    public void setDamageTypes(int[] damagetypes) {
        this.damageTypes = damagetypes;
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
