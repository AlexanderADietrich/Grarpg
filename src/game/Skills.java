package game;

import java.util.HashMap;
import java.util.Iterator;

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
    private HashMap<String, Integer> buffs = new HashMap<>();
    private HashMap<Timer,  Integer> timers = new HashMap<>();
    Iterator                         timeIter;
    Timer                            cTimeIter;
    int                              cTimerCTime;
    
    private Entity e;
    public Skills(Entity e){
        //Add Default Skills. All other skills will have to be learned.
        this.addSkill("attack", 1);
        this.addSkill("go", 1);
        this.addSkill("swim", 0);
        this.addSkill("climb", 0);
        this.addSkill("BLOCKED", 0);
        this.addSkill("NO-SKILL", -1);
        this.e=e;
    }
    public void addBuff(String skill, int amount, int timer){
        if (buffs.containsKey(skill)){
            buffs.put(skill, buffs.get(skill) + amount);
        }
        timers.put(new Timer(skill, timer), amount);
        buffs.put(skill, amount);
    }
    public void doSkillTick(){
          timeIter = timers.keySet().iterator();
          while (timeIter.hasNext()){
              cTimeIter = (Timer) timeIter.next();
              cTimerCTime = (int) ((System.currentTimeMillis() - cTimeIter.timeCreated)/1000);
              System.out.println("POTION + " + (cTimeIter.timer - cTimerCTime));
              if (cTimeIter.timer - cTimerCTime <= 0){
                  System.out.println("REMOVED");
                  buffs.put(cTimeIter.skill, buffs.get(cTimeIter.skill) - timers.get(cTimeIter));
                  timers.remove(cTimeIter);
              }
          }
    }
    public void addSkill(String skill, Integer level){
        skills.put(skill, level);
    }
    public int getSkillLevel(String skill){
        try { 
            return skills.get(skill) + buffs.get(skill);
        } catch (Exception ex){
            try {
                return skills.get(skill);
            } catch (Exception exx) {
                exx.printStackTrace();
                return 0;
            }
        }
    }
    //Subtract from buff when Timer runs out. 
    private static class Timer{
        public Long timeCreated = System.currentTimeMillis();
        public String skill;
        public int timer;
        public Timer(String skill, int timer){
            this.skill = skill;
            this.timer = timer;
        }
    }
}
