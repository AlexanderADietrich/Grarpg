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
    boolean isPhys; //Assumes physical, checks if magical. Use Discipline for Magic Defense.
    boolean breaker;
    public void doTick(){
        isPhys = true;
        breaker = false;
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
                entities[i] = null;
                g.fighting = false;
                System.out.println("Hgere");
                breaker = true;
                break;
            }
            
            
            //Current Entity "attacks"
            if (entities[i] != null){
                parseThis = entities[i].doFightTick().trim().split(" ");
            
                //if (parseThis != null && parseThis.length >= 2) System.out.println(parseThis[1]);

                if (parseThis.length < 2) continue;
                if (parseThis.length >= 2){
                    if (parseThis.length == 3){
                        if (parseThis[2].equals("ENERGY")){
                            isPhys = false;
                        }
                    }
                    try { damage = (double) Integer.parseInt(parseThis[0]); }
                    catch (Exception ex) { continue; }
                    parseThis[1] = parseThis[1].replace("/", " ");
                    reason = parseThis[1];
                    if (i == 0) target = entities[1].getName();
                    else target = entities[i-1].getName();
                }

                for (Entity e : entities){
                    if (e.getName().equals(target)){
                        if (damage == 0){
                            toAppend += reason;
                        } else {
                        //System.out.println("DAMAGE = " + damage);
                        if (!isPhys)
                            damage -= e.getStat(1);
                        else
                            damage -= e.getStat(3);
                        //System.out.println("DAMAGE = " + damage);
                        if (damage <= 0) damage = 1;
                        //System.out.println("DAMAGE = " + damage);
                        e.setHP(e.getHP() - damage);
                        toAppend += g.commandHandler.wrap(("damaged for " + damage + " " + reason), ("damaged for " + damage + " " + reason).length());
                        //g.append(g.commandHandler.wrap(("damaged for " + damage + " " + reason), ("damaged for " + damage + " " + reason).length()));
                        }
                    }
                }
            }
            
            turn = !turn;
            if (breaker) break;
        }
        g.append(toAppend);
    }
}
