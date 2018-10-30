package game;

/**
 * Some NPCs are helpful, some are not, all deliver some message to the player
 * and all can be killed.
 * @author voice
 */
public class NPC extends Entity{
    public Game g;
    public NPC(Game g, int x, int y){
        this.setXPOS(x);
        this.setYPOS(y);
        this.setName("NPC");
        this.setImagePath("images/defaultNPC.png");
        this.skillChecker = new Skills(this);
        this.g=g;
    }
    @Override
    public void doTick(){
        if (g.getPlayerAdjEntities() != null && g.getPlayerAdjEntities().length > 0) 
            for (Entity e : g.getPlayerAdjEntities()){
                if (e != null)
                    if (e.equals(this)){
                        dispMessage();
                    }
            }
    }
    /**
     * Should display a message that if the player responds to, changes the NPC's
     * behavior.
     */
    public void dispMessage(){
        g.append(g.commandHandler.wrap("Hello!", 7));
    }
    
}
