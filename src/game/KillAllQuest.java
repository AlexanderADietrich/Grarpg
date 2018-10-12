/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.HashSet;
import java.util.Iterator;


/**
 *
 * @author voice
 */
public class KillAllQuest extends Quest{
    public HashSet<Trigger> triggers = new HashSet<>();
    public Enemy etemp;
    public Game g;
    
    public String message = "";
    public String getMessage(){
        String temp;
        temp = message + "";
        message = "";
        return temp;
    }
    
    public KillAllQuest(Game g){this.g=g;}
    
    @Override
    public void specialGen(Map m){
        int sentinel = 0;
        //Add Triggers to all monsters in m.
        for (Chunk[] clist : m.chunks){
            for (Chunk c : clist){
                
                for (Entity e : c.getAllEntities()){
                    if (Enemy.class.isAssignableFrom(e.getClass())){
                        Trigger ttemp = new OnKillTrigger(e);
                        triggers.add(ttemp);
                        e.tSlot = ttemp;
                    }
                }
            }
        }
        HashSet<FireSkeleton> set = new HashSet<>();
        Iterator iter = set.iterator();
        for (int i = 0; i < 12; i++){
            set.add(new FireSkeleton(0, 0, "FireSkeleton" + Math.random(), 40, g.p, null, (int) (Math.random()*4)));
        }
        m.addDungeonMonsters(set);
    }
    public void doTick(){
        Iterator iter = triggers.iterator();
        while (iter.hasNext()){
            ((Trigger) iter.next()).doTick();
        }
    }
    public Trigger ttemp;
    public boolean check(){
        Iterator iter = triggers.iterator();
        boolean retVal = true;
        while (iter.hasNext()){
            ttemp = (Trigger) iter.next();
            retVal = retVal && ttemp.check();
        }
        return retVal;
    }
}
