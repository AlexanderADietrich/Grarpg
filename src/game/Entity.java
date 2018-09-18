
package game;

/**
 * Basic Entity Class  
 * @author Nathan Geddis
 * @contributor Alexander Dietrich, 4/20/18
 */
public class Entity {
    private int xPOS;
    private int yPOS;
    private double hp; // could be put in stats if changed to int. Not sure why it is double
    // Index 0 = Strength, Index 1 = Defence, Index 2 = Intelligence, Index 3 = Discipline, Index 4 = Speed
    private int[] stats = new int[4];
    private String name;
    
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
    
    public String doFightTick(){
        return "1 DEFAULTDAMAGECAUSE";
        /*
        Current plan is that during a fight, names should be printed
        at the start (possibly reprinted onclick of sprite?) and that the player
        should have to type in the name to attack. By default, not specifying a
        name should attack a random entity within the fight container. 
        */
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
