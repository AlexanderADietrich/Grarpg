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
public class SlashCutscene extends Cutscene{
    public SlashCutscene(Game g){
        super(14);
        for (int i = 0; i < 14; i++)
            this.images[i] = g.cscImages.get("cscimages/slashcsc" + i + ".png");
    }
    
}
