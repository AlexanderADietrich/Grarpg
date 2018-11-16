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
public class OnKillTrigger extends Trigger{
    //Entity that contains the trigger, and must be monitored by the trigger
    public Entity e;
    public OnKillTrigger(Entity e){
        this.e=e;
    }
    @Override
    public void doTick(){
        completed = (e.getHP() <= 0);
    }
    
}
