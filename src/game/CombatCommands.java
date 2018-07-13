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
public class CombatCommands extends Commands{
    private Game g;
    
    public CombatCommands(Game g) {
        super(g);
        this.g=g;
    }
    
    @Override
    public void parsePlayerCommand(String command, Player p){
        command = command.trim();
        p.previousFightCommand = command;
        command = wrap(command, command.length());
        
        g.textOutput.append(command);
        //TODO: different ways to attack, then reformat and send to commands.
    }
    
}
