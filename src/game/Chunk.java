package game;

/**
 *
 * @author voice
 */
public class Chunk {
    public Tile[][] tiles;
    public int chunkWidth;   //Number of Tiles in Map Horizontally
    public int chunkHeight;  //Number of Tiles in Map Vertically
    public Entity[][] entities;
    public Chunk(int chunkWidth, int chunkHeight){
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
        tiles =  new Tile[chunkWidth][chunkHeight];
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
}
