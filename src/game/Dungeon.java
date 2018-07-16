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
public class Dungeon extends Map{
    
    public Dungeon(Game g) {
        super(g);
        width = 32;
        height = 32;
        tiles = new Tile[32][32];
        for (int i = 0; i < 64; i++){
            for (int b = 0; b < 64; b++){
                tiles[b][i] = new Tile("images/defaultTile.png", b, i);
            }
        }
        chunks = new Chunk[4][4];
    }
    
}
