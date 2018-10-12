package game;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic enemy class using basic AI can be extended for more complex enemies.
 * @author Nathan Geddis
 * @created 4/21/2018
 * @modifed 4/21/2018
 */
public class Enemy extends Entity{
    private AI ai;
    public Entity t;
    public Chunk c;
    
    @Override
    public void doTick(){
        ai.nextMove();
    }
    

    
    public Enemy (int x, int y, String name, double hp, Entity t, String path, Chunk c){
        this.t=t;
        this.c=c;
        ai = new ImprovedAI(t, this, c);
        this.skillChecker = new Skills(this);
        this.setXPOS(x);
        this.setYPOS(y);
        this.setHP(hp);
        this.setName(name);
        this.setImagePath(path);
        this.setStats(new int[] {4,4,4,4,4});
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }
    
    
    public void setChunk(Chunk c){
        this.c=c;
        this.ai = new ImprovedAI(t, this, c);
    }
}
