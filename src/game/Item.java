package game;

/**
 *
 * @author voice
 */
public class Item {

    public int gp;
    public String buff;
    public int amountBuff;

    //TODO: Interesting items that on the average aid the player.
    public Item(int gp, String buff, int amountBuff) {
        this.gp = gp;
        this.buff = buff;
        this.amountBuff = amountBuff;
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
