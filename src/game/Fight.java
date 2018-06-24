package game;

/**
 *
 * @author voice
 */
public class Fight {
    private Entity[] entities;
    private Game g;
    public Fight(Game g){this.g=g;}
    private String[] parseThis;
    double damage = 0;
    String reason = "";
    String target = "";
    
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
        for (int i = 0; i < entities.length; i++){
            parseThis = entities[i].doFightTick().trim().split(" ");
            if (parseThis.length < 2) break;
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
                    g.textOutput.append("damaged for " + damage + " " + reason);
                    succeeded = true;
                }
            }
            if (!succeeded) g.textOutput.append("Missed!");
        }
    }
}
