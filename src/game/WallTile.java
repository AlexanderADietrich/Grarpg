
package game;

/**
 * A Basic Wall tile
 * @author Nathan Geddis
 */
public class WallTile extends Tile {
    public WallTile(String s, int x, int y){
        super(s, x, y);
        skillTraverse = "NO-SKILL";
    }
}
