package game;

/**
 *
 * @author voice
 */
public class Item {

    public int gp;
    public String buff;
    public int amountBuff;
    public String name;

    //TODO: Interesting items that on the average aid the player.
    public Item(int gp, String buff, int amountBuff, String name) {
        this.gp = gp;
        this.buff = buff;
        this.amountBuff = amountBuff;
        this.name=name;
    }
    public Item(){
        this.gp=0;
        this.buff="";
        this.amountBuff=0;
        this.name="";
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public String getBuff() {
        return buff;
    }

    public void setBuff(String buff) {
        this.buff = buff;
    }

    public int getAmountBuff() {
        return amountBuff;
    }

    public void setAmountBuff(int amountBuff) {
        this.amountBuff = amountBuff;
    }
}
