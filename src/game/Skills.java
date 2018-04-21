package game;

import java.util.HashMap;

/**
 *
 * @author voice
 */
    /*
        TODO;   Make a list of skills callable by the Commmands class. 
            Make this class check the level of the skills, and if they're
            currently usable by the holder of this Skills object.
    */
public class Skills {
    private HashMap<String, Integer> skills = new HashMap<>();
    private Entity e;
    public Skills(Entity e){
        //Add Default Skills. All other skills will have to be learned.
        this.addSkill("attack", 1);
        this.addSkill("go", 1);
        this.e=e;
    }
    public void addSkill(String skill, Integer level){
        skills.put(skill, level);
    }
    public int getSkillLevel(String skill){
        return skills.get(skill);
    }
}
