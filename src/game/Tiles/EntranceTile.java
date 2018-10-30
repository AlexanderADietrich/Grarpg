package game.Tiles;

import game.Dungeon;
import game.Game;
import game.Map;

public class EntranceTile extends Tile{
    private Game g;
    public Map Map;
    public EntranceTile reverse;
    private Dungeon d;
    
    public EntranceTile(int x, int y, Game g){
        super("images/entranceTile.png", x, y);
        this.g=g;
    }
    
    public EntranceTile(int x, int y, Map outerMap, Game g, Dungeon d) {
        super("images/entranceTile.png", x, y);
        this.g=g;
        this.Map= outerMap;
        this.d = d;
        
        reverse = new EntranceTile(d.entranceX, d.entranceY, g);
        //Place Exit in Dungeon.
        this.d.tiles[this.d.entranceY][this.d.entranceX] = reverse;
        this.d.currentChunk.tiles[this.d.entranceY % 8][this.d.entranceX % 8] = reverse;
        
        reverse.Map = this.d;
        reverse.reverse = this;
    }
    
}
