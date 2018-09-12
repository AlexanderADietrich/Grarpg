package game;
public class EntranceTile extends Tile{
    private Game g;
    public Map Map;
    public EntranceTile reverse;
    
    public EntranceTile(int x, int y, Game g){
        super("images/entranceTile.png", x, y);
        this.g=g;
    }
    
    public EntranceTile(int x, int y, Map outerMap, Game g) {
        super("images/entranceTile.png", x, y);
        this.g=g;
        this.Map= outerMap;
        Dungeon d = new Dungeon(g);
        
        reverse = new EntranceTile(d.entranceX, d.entranceY, g);
        //Place Exit in Dungeon.
        d.tiles[d.entranceY][d.entranceX] = reverse;
        d.currentChunk.tiles[d.entranceY % 8][d.entranceX % 8] = reverse;
        
        reverse.Map = d;
        reverse.reverse = this;
    }
    
}
