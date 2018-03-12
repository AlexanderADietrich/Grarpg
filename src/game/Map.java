package game;

/**
 *
 * @author voice
 */
public class Map {
    public Tile[][] tiles;
    private int mapWidth;
    private int mapHeight;
    private int areaWidth;
    private int areaHeight;
    public Map(int mapWidth, int mapHeight, int areaWidth, int areaHeight){
        tiles =  new Tile[mapWidth][mapHeight];
        int b = 0;
        int d = 0;
        for (Tile[] tList1 : tiles){
            for (int c = 0; c < tList1.length; c++){
                tList1[c] = new Tile("images/defaultTile.png", b*(areaWidth / mapWidth), d*(areaHeight / mapHeight));
                b++;
            }
            b = 0;
            d++;
        }
    }
}
