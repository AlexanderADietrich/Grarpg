package game;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 *
 * @author voice
 */
public class LevelUpMenu {
    public HashMap<String, Button> buttons = new HashMap<>();
    private Game g;
    String[] statNames = new String[]{"strength", "defense", "intelligence", "discipline", "speed"};
    private class LUMActionListener implements ActionListener{
        private int change;
        private int stat;
        public LUMActionListener(int change, int stat){
            this.change =change;
            this.stat   =stat;
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (g.p.levelUp(stat, change)){
                g.append(g.commandHandler.wrap("Leveled up " + statNames[stat] + " by " + change, ("Leveled up" + statNames[stat] + " by " + change).length()));
            } else {
                g.append(g.commandHandler.wrap("Not Enough Experience", 21));
            }
        }
        
    }
    public Button[] levelUpButtons = new Button[5];
    public LUMActionListener[] levelUpListeners = new LUMActionListener[5];
    public LevelUpMenu(Game g){
        //SECTION: Initialize~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (int i = 0; i < 5; i++){
            levelUpListeners[i] = new LUMActionListener(1, i);
            levelUpButtons[i] = new Button(statNames[i] + " +1");
            levelUpButtons[i].addActionListener(levelUpListeners[i]);
            levelUpButtons[i].setBounds((g.areaWidth/5)*i, g.areaHeight-20, (g.areaWidth/5), 20);
        }
        this.g=g;
    }
    public void updateButtons(){
        for (int i = 0; i < 5; i++){
            levelUpButtons[i].setBounds((g.areaWidth/5)*i, g.areaHeight-20, (g.areaWidth/5), 20);
        }
    }
}
