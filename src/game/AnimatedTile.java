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
public class AnimatedTile extends Tile{
    public String[] slist;
    public int flop;
    
    public AnimatedTile(String[] slist, int x, int y, int flop) {
        super(slist[0], x, y);
        this.slist = slist;
        this.flop=flop;
    }
    
    public int sentinel = 0;
    public int sentflop = 0;
    public void doTick(){
        if (sentflop++ == flop){
            //System.out.println(sentinel + " " + sentflop);
            this.imagePath = slist[sentinel++];
        }
        if (sentflop > flop){
            sentflop = 0;
        }
        if (sentinel >= slist.length){
            sentinel = 0;
        }
    }
    
}