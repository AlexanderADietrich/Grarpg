package game;

/**
 * Basic enemy class using basic AI can be extended for more complex enemies.
 * @author Nathan Geddis
 * @created 4/21/2018
 * @modifed 4/21/2018
 */
public class Enemy extends Entity{
    private AI ai;
    
    public Enemy (int x, int y, String name, double hp, Entity t, String path){
        ai = new AI(t,this);
        this.setXPOS(x);
        this.setYPOS(y);
        this.setHP(hp);
        this.setName(name);
        this.setImagePath(path);
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }
    
}
