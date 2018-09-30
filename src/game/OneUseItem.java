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
public class OneUseItem extends Item{
    public int time;
    public OneUseItem(int gp, String buff, int amountBuff, int time, String name) {
        super(gp, buff, amountBuff, name);
        this.time=time;
    }
}
