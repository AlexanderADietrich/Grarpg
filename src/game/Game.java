package game;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

/**
 *
 * @author voice
 */
public class Game extends Applet implements Runnable{
    long framerate = 34;
    private Image i;
    private Graphics gP;
    private URL url;
    private Map m = new Map(8, 8, 384, 384);
    private TextField t = new TextField("", 20);
    private ActionListener k = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            t.setText("Ahhhhhh!");
        }
        
    };
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        setSize(584, 384);
        setLayout(null);
        
        t.addActionListener(k);
        add(t);
        
        
        try {
            url = getDocumentBase();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
     
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }
    
     // For the thread above.
    public void run() {
        while (2<3){
            repaint();
            tSleep();
        }
    }
    
    
    public void update(Graphics g){//Double Buffer.
        if (i == null){
            i = createImage(this.getSize().width, this.getSize().height);
            gP = i.getGraphics();
        }
        gP.setColor(getBackground());
        gP.fillRect(0, 0, this.getSize().width, this.getSize().height);
        gP.setColor(getForeground());
        paint(gP);
        g.drawImage(i, 0, 0, this);
    }
    
    public void tSleep(){
        try {
            Thread.sleep(framerate);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Tile[] tList1 : m.tiles){
            for (Tile t : tList1){
                g.drawImage(getImage(url, t.imagePath), t.x, t.y, this);
            }
        }
        t.setLocation(384, 0);
        t.setSize(200, 384);
        super.paint(g);
    }

    
    

    
    /*public void checkWalls(double[] d, Player p){
        double[] d2 = new double[2];
        if (d[0] + d[2] < 0 ){
            System.out.println("1");
            d2[0] = 0;
        } else if (this.getSize().width - p.size*2  < d[0] + d[2]){
            System.out.println("2");
            d2[0] = this.getSize().width - p.size*2-1;
        } else {
            d2[0] = d[0] + d[2];
        }
            
        if (d[1] + d[3] < 0){
            System.out.println("3");
            d2[1] =  0;
        } else if (this.getSize().height - p.size*2 < d[1] + d[3]){
            System.out.println("4");
            d2[1] = this.getSize().height - p.size*2-1;
        } else {
            d2[1] = d[1] + d[3];
        }
        p.x = d2[0];
        p.y = d2[1];
    }*/
}
