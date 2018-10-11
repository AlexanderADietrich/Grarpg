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
public class FireSkeleton extends Enemy{
    public Random r = new Random();
    public FireSkeleton(int x, int y, String name, double hp, Entity t, Chunk c, int level) {
        super(x, y, name, hp, t, "images/fireSkeleton.png", c);
        this.setAi(new ImprovedAI(t, this, c));
        this.setStats(new int[]{3*level, 2*level, 3*level, 3*level, 5*level});
    }
    boolean tempBuff;
    int charge = 1;
    @Override
    public String doFightTick(){
        if (tempBuff){
            charge++;
            tempBuff = false;
        }
        if (r.nextBoolean() || r.nextBoolean()){
            if (r.nextBoolean()){
                return this.getStat(2)*charge + " ./The/burning/skeleton's/eyes/glow/as/a/stream/of/fire/pours/out/of/its/mouth\n (ENERGY)";
            } else{
                this.setHP(this.getHP() - this.getStat(2)*charge);
                return this.getStat(2)*charge +this.getStat(1) + " you/saw/sparks/as/the/skeleton's/flaming/hand/flew/toward/you,/leaving/the/monster/scorched/itself.\n";
            }
        } else {
            tempBuff = true;
            this.setImagePath("images/fireSkeleton2.png");
            return 0 + " The/skeleton's/eyes/burn/brighter,/seeming/to/gather/power.\n";
        }
    }
    
}
