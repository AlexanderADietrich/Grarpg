package game;

/**
 *
 * @author voice
 */
public class Fight {
    public Entity[] entities;
    private Game g;
    private String[] parseThis;
    double damage = 0;
    String reason = "";
    String target = "";
    boolean turn = true;
    
    public Fight(Game g){this.g=g;}
    
    public void start(Entity[] entities){
        this.entities = entities;
        // Sorts entitys by their speed so the fastest entity goes first.
        boolean sorted = false;
        while (! sorted){
            sorted = true;
            for (int i = entities.length - 1; i > 0 ; i--){
                if (entities[i].getStat(4) > entities[i-1].getStat(4)){
                    Entity temp = entities[i];
                    entities[i] = entities[i-1];
                    entities[i-1] = temp;
                    sorted = false;
                }
            }
        }
    }
      
    /*
    Iterate over entities
        For each entity get its fight command, composed of:
            damage reason target
            double String String
            if target is null target next or previous entity
            iterate over entities
                if entity's name is target name, damage it and print the reason
                
    */
    public void doTick(){
        String toAppend = "";
        for (int i = 0; i < entities.length; i++){
            if (turn) 
                if (i == 1) continue;
            if (!turn)
                if (i == 0) continue;
            
            //Check for death
            if (entities[i].getHP() <= 0){
                //System.out.println("DEAD");
                g.m.currentChunk.removeEntity(entities[i].getXPOS(), entities[i].getYPOS());
                g.fighting = false;
            }
            
            
            //Current Entity "attacks"
            parseThis = entities[i].doFightTick().trim().split(" ");
            //if (parseThis != null && parseThis.length >= 2) System.out.println(parseThis[1]);
            
            if (parseThis.length < 2) continue;
            if (parseThis.length == 2){
                try { damage = (double) Integer.parseInt(parseThis[0]); }
                catch (Exception ex) { continue; }
                parseThis[1] = parseThis[1].replace("/", " ");
                reason = parseThis[1];
                if (i == 0) target = entities[1].getName();
                else target = entities[i-1].getName();
            } else {
                damage = (double) Integer.parseInt(parseThis[0]);
                reason = parseThis[1];
                target = parseThis[2];
            }
            Boolean succeeded = false;
            
            for (Entity e : entities){
                if (e.getName().equals(target)){
                    //System.out.println("DAMAGE = " + damage);
                    damage -= e.getStat(1);
                    //System.out.println("DAMAGE = " + damage);
                    if (damage <= 0) damage = 1;
                    //System.out.println("DAMAGE = " + damage);
                    e.setHP(e.getHP() - damage);
                    toAppend += g.commandHandler.wrap(("damaged for " + damage + " " + reason), ("damaged for " + damage + " " + reason).length());
                    //g.append(g.commandHandler.wrap(("damaged for " + damage + " " + reason), ("damaged for " + damage + " " + reason).length()));
                    succeeded = true;
                }
            }
            if (!succeeded) toAppend += "Missed!";
            
            turn = !turn;
        }
        g.append(toAppend);
    }
}
