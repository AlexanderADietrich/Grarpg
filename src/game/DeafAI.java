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
    public boolean heard = false;
    
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
            heard = true;
            attack();
        }
        else if (count == 20){ //Move Randomly Very Rarely
            moveRand();
            count = 0;
        }
        else
            count++;
    }
    @Override
    public void moveRight(){
        if (!heard) currentChunk.updateLoc(Body, 1, 0, 500);
        else currentChunk.updateLoc(Body, 1, 0, 50);
    }
    @Override
    public void moveLeft(){
        if (!heard) currentChunk.updateLoc(Body, -1, 0, 500);
        else currentChunk.updateLoc(Body, -1, 0, 50);
    }
    @Override
    public void moveDown(){
        if (!heard) currentChunk.updateLoc(Body, 0, 1, 500);
        else currentChunk.updateLoc(Body, 0, 1, 50);
    }
    @Override
    public void moveUp(){
        if (!heard) currentChunk.updateLoc(Body, 0, -1, 500);
        else currentChunk.updateLoc(Body, 0, -1, 50);
    }
    
}
