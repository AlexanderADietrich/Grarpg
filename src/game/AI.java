package game;

import java.util.Random;
/**
 * Basic AI class to be extended to implement more specific movement patterns.
 * @author Nathan Geddis
 * @created 4/21/2018 
 * @modified 4/21/2018
 * @modified 9/20/2018
 */
public class AI{
    public int count = 0; // used to keep track of moves and can be used so the enemy only moves every other or every third player move. 
    
    //Player Makes Sounds that Influence Some AI's
    public int sound = 0;
    public int incomingSound = 0;
    
    public int XDIST;
    public int YDIST;
    public Chunk currentChunk;
    public Entity Target;
    public Entity Body;
    Random rand = new Random();
    
    public AI(Entity t, Entity b, Chunk c){
        Target = t;
        Body = b;
        currentChunk = c;
    }
    
    public Chunk getCurrentChunk() {
        return currentChunk;
    }

    public void setCurrentChunk(Chunk currentChunk) {
        this.currentChunk = currentChunk;
    }

    public Entity getTarget() {
        return Target;
    }

    public void setTarget(Entity Target) {
        this.Target = Target;
    }

    /*
    Contains the specifecs algorithm for the ai called whenever the player
    makes a move
    */
    public void nextMove(){
        XDIST = Target.getXPOS()-Body.getXPOS();
        YDIST = Target.getYPOS()-Body.getYPOS();
        //System.out.println("Count: " + count);
        if (count < 5){
            //System.out.println("WANDERING");
            moveRand();
        }
        //aggressive movement for six turns
        else if (count >= 5 && count <= 10 ){
            if (sound >= 10){
                //System.out.println("ATTACKING");
                attack();
            }
            if (XDIST < 6 && YDIST < 6){
                //System.out.println("HEARING \t" + sound);
                sound += incomingSound;
                incomingSound = 0;
            } else {
                incomingSound = 0;
            }
        }else{
            //System.out.println("WANDERING AGAIN");
            count = -1;
            moveRand();
        }
        count++;
    }
    
    public void attack(){
        if (Math.abs(XDIST) > Math.abs(YDIST)){
                    if (XDIST > 0)
                        moveRight();
                    else
                        moveLeft();
                }
        else if (Math.abs(XDIST) < Math.abs(YDIST)){
            if (YDIST > 0)
                moveDown();
            else 
                moveUp();
        } else {
            if (YDIST > 0)
                moveDown();
            else if (YDIST < 0)
                moveUp();
            else if (XDIST > 0)
                moveRight();
            else if (XDIST < 0)
                moveLeft();
        }
    }
    public void moveRight(){
        currentChunk.updateLoc(Body, 1, 0);
        //System.out.println("R   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveLeft(){
        currentChunk.updateLoc(Body, -1, 0);
        //System.out.println("L   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveDown(){
        currentChunk.updateLoc(Body, 0, 1);
        //System.out.println("D   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveUp(){
        currentChunk.updateLoc(Body, 0, -1);
        //System.out.println("U   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveRand(){
        int r = rand.nextInt(4);
        //System.out.println("Rand: " + r);
        switch (r) {
            case 0:
                moveRight();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveUp();
            default:
                break;
        }
    }
}
