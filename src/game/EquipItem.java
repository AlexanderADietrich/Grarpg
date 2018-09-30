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
public class EquipItem extends Item{
    int[] buffs = new int[5]; //Some Weapons Give Different Values
    public boolean equipped = false;
    public EquipItem(int[] buffs, String name) {
        this.buffs=buffs;
        this.name=name;
        this.gp=buffs.length*10;
    }
    
}
