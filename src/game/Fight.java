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
    
    public Fight(Game g){this.g=g;}
    
    public void start(Entity[] entities){
        this.entities = entities;
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
        Boolean[] turns = new Boolean[entities.length];
        for (int i = 0; i < turns.length; i++) turns[i] = false;
        turns[0] = true;
        for (int i = 0; i < entities.length; i++){
            for (Boolean b : turns) System.out.print(b + " ");
            System.out.println();
            if (!turns[i]) continue;
            
            //Current Entity "attacks"
            parseThis = entities[i].doFightTick().trim().split(" ");
            
            if (parseThis.length < 2) continue;
            if (parseThis.length == 2){
                damage = (double) Integer.parseInt(parseThis[0]);
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
                    e.setHP(e.getHP()-damage);
                    g.textOutput.append("damaged for " + damage + " " + reason + "\n");
                    succeeded = true;
                }
            }
            if (!succeeded) g.textOutput.append("Missed!");
            
            //Pass the turn around.
            if (i == entities.length-1) turns[0] = true;
            else turns[i+1] = true;
            turns[i] = false;
        }
    }
}
