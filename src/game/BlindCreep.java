/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Random;

/**
 *
 * @author voice
 */
public class BlindCreep extends Enemy{
    
    public BlindCreep(int x, int y, String name, double hp, Entity t, String path, Chunk c) {
        super(x, y, name, hp, t, path, c);
        this.setAi(new DeafAI(t, this, c));
        this.setStats(new int[] {10, 20, 1, 10, 5});
    }
    
    public Random r = new Random();
    boolean broken = false;
    @Override
    public String doFightTick(){
        if (broken){
            return 0 + " The/monster/stares/at/you/blankly,/its/rotten/heart/slowly/oozing"
                    +"/blue/blood/as/it/waits/for/death";
        }
        if (r.nextBoolean()){
            return this.getStat(0) + " as/the/creaking/skeleton/bashes/you/with"
                    + "/unnatural/might";
            
        }
        else {
            broken = true;
            this.setImagePath("images/deafCreepBroken.png");
            return this.getStat(0) + " ,/you/hear/a/loud/crack/as/the/insane/force/of/this/monster"
                    + "/shatters/its/arm/into/splinters";
        }
    }
}
