package game;

public class ImprovedAI extends AI{
    public boolean attack = false;
    public double TOTALDIST;
    
    public ImprovedAI(Entity t, Entity b, Chunk c) {
        super(t, b, c);
    }
    
    public double temp;
    @Override
    public void nextMove(){
        recalc();
        
        if (TOTALDIST < 7){
            sound += incomingSound;
            incomingSound = 0;
        } else {
            if (incomingSound > 0) sound++;
            incomingSound = 0;
        }
        
        if (sound > 10){ //Attack If Has Heard Target
            temp = TOTALDIST;
            attack();
            
            //If It Hasn't Moved (IE. stuck on wall)) move randomly instead.
            recalc();
            if (temp == TOTALDIST){
                moveRand();
            }
        }
        else if (count == 20){ //Move Randomly Very Rarely
            moveRand();
            count = 0;
        }
        else
            count++;
    }
    public void recalc(){
        XDIST = Target.getXPOS()-Body.getXPOS();
        YDIST = Target.getYPOS()-Body.getYPOS();
        TOTALDIST = Math.sqrt(Math.pow(XDIST, 2) + Math.pow(YDIST, 2));
    }
}
