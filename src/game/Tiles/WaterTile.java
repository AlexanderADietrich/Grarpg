/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Tiles;

/**
 *
 * @author voice
 */
public class WaterTile extends AnimatedTile{
    public WaterTile(String[] slist, int x, int y, int flop) {
        super(slist, x, y, flop);
        skillTraverse = "swim";
    }
}
