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
    
    //Truly Terrible Currently
    public String doTick(){
        for (int i = 0; i < entities.length; i++){
            parseThis = entities[i].doFightTick().trim().split(" ");
            if (parseThis.length < 2) break;
            if (parseThis.length == 2){
                damage = (double) Integer.parseInt(parseThis[0]);
                reason = parseThis[1];
                if (i != 0) target = entities[0].getName();
                else target = entities[1].getName();
            } else {
                damage = (double) Integer.parseInt(parseThis[0]);
                reason = parseThis[1];
                target = parseThis[2];
            }
            /*
            Damage the Target and Print the Reason.
            */
        }
        return "PLACEHOLDER";
    };
}
