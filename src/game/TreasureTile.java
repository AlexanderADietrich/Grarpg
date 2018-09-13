/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author voice
 */
public class TreasureTile extends Tile{
    public Item[] items;
    public TreasureTile(int x, int y, int level){
        super("images/treasureTile.png", x, y);
        generateTreasure(level);
    }
    public void generateTreasure(int level){
        items = new Item[level];
        for (int i = 0; i < items.length; i++){
            items[i] = new OneUseItem(1000, "climb", 1, 10);
        }
    }
    
}
