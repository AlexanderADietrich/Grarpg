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
public abstract class Trigger {
    public boolean completed;
    /**
     * Updates whether the Trigger has been completed.
     */
    public void doTick(){
        
    }
    /**
     * 
     * @return if the trigger has been completed.
     */
    public boolean check(){
        return completed;
    }
}
