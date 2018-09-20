/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author voice
 */
public class DeafAI extends AI{
    
    public DeafAI(Entity t, Entity b, Chunk c) {
        super(t, b, c);
    }
    @Override
    public void nextMove(){
        //System.out.println(sound + "DEAF?");
        XDIST = Target.getXPOS()-Body.getXPOS();
        YDIST = Target.getYPOS()-Body.getYPOS();
        sound += incomingSound;
        incomingSound = 0;
        if (sound > 10){ //Attack If Has Heard Target
            attack();
        }
        else if (count == 20){ //Move Randomly Very Rarely
            moveRand();
            count = 0;
        }
        else
            count++;
    }
    
}
