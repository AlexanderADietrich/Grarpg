package game;

import java.util.Random;
/**
 * Basic AI class to be extended to implement more specific movement patterns.
 * @author Nathan Geddis
 * @created 4/21/2018 
 * @modified 4/21/2018
 */
public class AI{
    public int count = 0; // used to keep track of moves and can be used so the enemy only moves every other or every third player move. 
    private Entity Target;
    private Entity Body;
    Random rand = new Random();
    
    public AI(Entity t, Entity b){
        Target = t;
        Body = b;
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
        //random movement for six turns (five first run)
        if (count < 5)
            moveRand();
        //aggressive movement for six turns
         if (5 <= count && count <= 10){         
            int XDIST = Target.getXPOS()-Body.getXPOS();
            int YDIST = Target.getYPOS()-Body.getYPOS();
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
            }
            else
                moveRand();
        }else{
            count = -1;
            moveRand();
        }
        count ++;
    }
    
    public void moveRight(){
        Body.setXPOS(Body.getXPOS()+1);
        //System.out.println("R   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveLeft(){
        Body.setXPOS(Body.getXPOS()-1);
        //System.out.println("L   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveDown(){
        Body.setYPOS(Body.getYPOS()+1);
        //System.out.println("D   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveUp(){
        Body.setYPOS(Body.getYPOS()-1);
        //System.out.println("U   "+ Body.getXPOS() + "   " + Body.getYPOS());
    }
    public void moveRand(){
        int r = rand.nextInt(4);
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
