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
public abstract class Quest {
    public String complete = "";
    
    
    /**
     * Generate points of interest for the quest, add triggers to them.
     * @param m the map to be edited
     */
    public abstract void specialGen(Map m);
    
    /**
     * Tick triggers and otherwise operate the quest's internal machinations.
     */
    public abstract void doTick();
    
    /**
     * Check if the quest has been completed.
     * @return if the quest has been completed.
     */
    public abstract boolean check();
    
}
