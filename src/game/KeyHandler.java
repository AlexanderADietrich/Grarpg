/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.PriorityQueue;

/**
 *
 * @author voice
 */
public class KeyHandler {
    public PriorityQueue<Integer> commands = new PriorityQueue<>();
    public int current;
    public Game g;
    
    public KeyHandler(Game g){this.g=g;}
    
    //Execute a command
    public void doTick(){
        if (commands.size() == 0) return;
        
        current = commands.poll();
        if (current == 9){
            g.switchInput();
        }
        if (g.useKeys == false){
            switch (current) {
                case 87:
                    g.commandHandler.parsePlayerCommand("n", g.p);
                    break;
                case 65:
                    g.commandHandler.parsePlayerCommand("w", g.p);
                    break;
                case 83:
                    g.commandHandler.parsePlayerCommand("s", g.p);
                    break;
                case 68:
                    g.commandHandler.parsePlayerCommand("e", g.p);
                    break;
                default:
                    break;
            }
        }
    }
    
    public void handleKey(int keycode){
        if (!commands.contains(keycode)) commands.add(keycode);
    }
    
}
