package game;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
//TEST COMMENT PLEASE WORK
/**
 *
 * @author voice
 */
public class Game extends Applet implements Runnable{
    private long                    framerate = 34;
    private Commands        commandHandler = new Commands(this);
    private Image           image;
    private Graphics        graphicsBuffer;
    private URL             mainURL;
    private Map             mainMap = new Map(8, 8, 384, 384);
    private TextField       textInput = new TextField("", 10);
    public TextArea         textOutput = new TextArea(10, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Font            mainFont = new Font(Font.MONOSPACED, 10, 15);
    public int              activeXPOS = 0;
    public int              activeYPOS = 0;
    private ActionListener  textInputListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            commandHandler.parseCommand(e.getActionCommand());
        }
    };
    public void setActive (int x, int y){
        activeXPOS = activeXPOS + x;
        activeYPOS = activeYPOS + y;
    }
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        setSize(584, 384);
        setLayout(null);
        textInput.addActionListener(textInputListener);
        textOutput.setEditable(false);
        textInput.setFont(mainFont);
        textOutput.setFont(mainFont);
        add(textInput);
        add(textOutput);
        
        
        try {
            mainURL = getDocumentBase();
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
            threadSleep();
        }
    }
    
    
    public void update(Graphics mainGraphics){//Double Buffer.
        if (image == null){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
        }
        graphicsBuffer.setColor(getBackground());
        graphicsBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
        graphicsBuffer.setColor(getForeground());
        paint(graphicsBuffer);
        mainGraphics.drawImage(image, 0, 0, this);
    }
    
    public void threadSleep(){
        try {
            Thread.sleep(framerate);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void paint(Graphics mainGraphics){
        mainGraphics.setColor(Color.black);
        mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (int b = 0; b < mainMap.tiles.length; b++){
            for (int c = 0; c < mainMap.tiles[b].length; c++){
                if (c == activeXPOS && b == activeYPOS){
                    //System.out.println(1 + " " + b + " " + c);
                    System.out.println(mainURL);
                    mainGraphics.drawImage(getImage(mainURL, 
                            mainMap.tiles[b][c].imagePath), 
                            mainMap.tiles[b][c].x, mainMap.tiles[b][c].y, 
                            new Color(200, 0, 0), 
                            this);
                }
                else {
                    //System.out.println(2 + " " + b + " " + c);
                    mainGraphics.drawImage(getImage(mainURL, 
                            mainMap.tiles[b][c].imagePath), 
                            mainMap.tiles[b][c].x, 
                            mainMap.tiles[b][c].y, 
                            this);
                }
            }
        }
        textOutput.setLocation(384, 21);
        textOutput.setSize(200, 363);
        textInput.setLocation(384, 0);
        textInput.setSize(200, 20);
        super.paint(mainGraphics);
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
