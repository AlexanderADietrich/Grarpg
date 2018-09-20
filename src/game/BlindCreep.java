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
public class BlindCreep extends Enemy{
    
    public BlindCreep(int x, int y, String name, double hp, Entity t, String path, Chunk c) {
        super(x, y, name, hp, t, path, c);
        this.setAi(new DeafAI(t, this, c));
    }
}
