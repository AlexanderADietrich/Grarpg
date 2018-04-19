package game;

/**
 *
 * @author voice
 */
public class Map {
    public Tile[][] tiles;
    private int mapWidth;   //Number of Tiles in Map Horizontally
    private int mapHeight;  //Number of Tiles in Map Vertically
    private int areaWidth;  //Width of Map Area (pixels).
    private int areaHeight; //Height of Map Area (pixels).
    public Map(int mapWidth, int mapHeight, int areaWidth, int areaHeight){
        tiles =  new Tile[mapWidth][mapHeight];
        int currentXPOS = 0;
        int currentYPOS = 0;
        for (Tile[] tiles2 : tiles){
            for (int currentX = 0; currentX < tiles2.length; currentX++){
                tiles2[currentX] = new Tile("images/defaultTile.png", currentXPOS*(areaWidth / mapWidth), currentYPOS*(areaHeight / mapHeight));
                currentXPOS++;
            }
            currentXPOS = 0;
            currentYPOS++;
        }
    }
}
