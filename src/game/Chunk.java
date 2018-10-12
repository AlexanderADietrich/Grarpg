package game;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author voice
 */
public class Chunk {
    public Tile[][] tiles;
    public int chunkWidth;   //Number of Tiles in Map Horizontally
    public int chunkHeight;  //Number of Tiles in Map Vertically
    public Entity[][] entities;
    public HashSet<Entity> fastEntities = new HashSet<>();
    public Game g;
    
    public Entity[] getAllEntities(){
        Iterator iter = fastEntities.iterator();
        Entity[] retVal = new Entity[fastEntities.size()];
        int sentinel = 0;
        while (iter.hasNext()){
            retVal[sentinel++] = (Entity) iter.next();
        }
        return retVal;
    }
    
    public boolean addEntity(Entity e, int x, int y){
        if (x < 0 || x >= entities[0].length || y < 0 || y >= entities.length || entities[y][x] != null) return false;
        entities[y][x] = e;
        e.setXPOS(x);
        e.setYPOS(y);
        fastEntities.add(e);
        //System.out.println(fastEntities.size() + " " + e.getImagePath());
        return true;
    }
    
    public boolean removeEntity(int x, int y){
        if (entities[y][x] != null){
            fastEntities.remove(entities[y][x]);
            entities[y][x] = null;
            return true;
        }else{
            return false;
        }
    }
    
    public void passGame(Game g){
        //System.out.println("WAS PASSED GAME");
        this.g=g;
    }
    
    public Chunk(int chunkWidth, int chunkHeight){
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
        tiles =  new Tile[chunkWidth][chunkHeight];
        entities = new Entity[chunkWidth][chunkHeight];
        int currentXPOS = 0;
        int currentYPOS = 0;
        for (Tile[] tiles2 : tiles){
            for (int currentX = 0; currentX < tiles2.length; currentX++){
                tiles2[currentX] = new Tile("images/defaultTile.png", currentXPOS, currentYPOS);
                currentXPOS++;
            }
            currentXPOS = 0;
            currentYPOS++;
        }
    }
    public Chunk(Tile[][] tiles){
        entities = new Entity[8][8];
        this.tiles = new Tile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++){
            for (int b = 0; b < tiles[0].length; b++){
                this.tiles[i][b] = tiles[i][b];
                //if (tiles[i][b] == null) //System.out.println("null " + i + " " + b);
                //System.out.print(tiles[i][b].imagePath.substring(6, 9));
            }
            //System.out.println();
        }
        //System.out.println();
        chunkWidth = tiles[0].length;
        chunkHeight = tiles.length;
    }
    
    /*
    To be used after finishing a movement animation. Rechecks to make sure
    another entity has not completed an animation before it.
    */
    public void finalizeMove(Entity e, int xDif, int yDif){
        
        if (e.getXPOS() + xDif < entities[0].length
            && e.getYPOS() + yDif < entities.length
            && e.getXPOS() + xDif >= 0
            && e.getYPOS() + yDif >= 0){

            //If the tile attempted is empty.
            if (entities[e.getYPOS()+yDif][e.getXPOS()+xDif] == null) {
                //If the entity is a Player type, and does not have the skill, break.
                if (Player.class.isInstance(e)){
                    Player p = (Player) e;
                    if (p.skillChecker.getSkillLevel(tiles[p.getYPOS()+yDif][p.getXPOS()+xDif].skillTraverse) > 0){}
                    else return;
                }
                removeEntity(e.getXPOS(), e.getYPOS());
                addEntity(e, e.getXPOS()+xDif, e.getYPOS()+yDif);
            }        
        }    
    }
    // Communication Entity <-> Chunk.
    public void updateLoc(Entity e, int xDif, int yDif, int ms){
        if (e.timer.moving) return;
        if (e.getXPOS() + xDif < entities[0].length
            && e.getYPOS() + yDif < entities.length
            && e.getXPOS() + xDif >= 0
            && e.getYPOS() + yDif >= 0){

            //If the tile attempted is empty.
            if (entities[e.getYPOS()+yDif][e.getXPOS()+xDif] == null) {
                
                    
                    if (e.skillChecker.getSkillLevel(tiles[e.getYPOS()+yDif][e.getXPOS()+xDif].skillTraverse) > 0){
                        if (250 - ms > 0){
                            if (!e.lockout){
                                if (!e.useStamina((250-ms)/10))
                                    e.setAni(ms, xDif, yDif);
                                return;
                            } else {
                                return;
                            }
                        }
                        else
                            e.useStamina(1);
                    } else {
                        return;
                    }
                    e.setAni(ms, xDif, yDif);
                    
                
                
                
            } else {


                //System.out.println(Enemy.class.isInstance(e));
                //System.out.println(Player.class.isInstance(entities[e.getYPOS()+yDif][e.getXPOS()+xDif]));

                //System.out.println(Enemy.class.isAssignableFrom(e.getClass()));
                //System.out.println(g != null);
                //System.out.println(Player.class.isInstance(entities[e.getYPOS()+yDif][e.getXPOS()+xDif]));

                if (g != null && (Enemy.class.isAssignableFrom(e.getClass())) &&
                        Player.class.isInstance(entities[e.getYPOS()+yDif][e.getXPOS()+xDif])){
                    //System.out.println(e.getXPOS() + " " + e.getYPOS()  + " " + entities[e.getYPOS()+yDif][e.getXPOS()+xDif].getXPOS() + " " + entities[e.getYPOS()+yDif][e.getXPOS()+xDif].getYPOS());
                    g.startFight(e, entities[e.getYPOS()+yDif][e.getXPOS()+xDif]);

                }
            }
        } 
    }
}
