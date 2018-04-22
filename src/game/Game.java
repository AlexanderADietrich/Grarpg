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
    private long            framerate = 34;
    private Commands        commandHandler = new Commands(this);
    private Image           image;
    private Image           basicTile;
    private Graphics        graphicsBuffer;
    private URL             mainURL;
    private Chunk             mainMap = new Chunk(8, 8);
    private TextField       textInput = new TextField("", 10);
    public TextArea         textOutput = new TextArea(10, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Font            mainFont = new Font(Font.MONOSPACED, 10, 15);
    public int              activeXPOS = 0;
    public int              activeYPOS = 0;
    public Player           p = new Player(0, 0, "");
    public Enemy            e = new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png" );
    public int areaWidth;  //Width of Map Area (pixels).
    public int areaHeight; //Height of Map Area (pixels).
    
    private ActionListener  textInputListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textInput.setText(" ");
            System.out.println(e.getActionCommand());
            commandHandler.parsePlayerCommand(e.getActionCommand(), p);
        }
    };
    
    public void setActive (int x, int y){
        activeXPOS = activeXPOS + x;
        activeYPOS = activeYPOS + y;
        p.setXPOS(activeXPOS);
        p.setYPOS(activeYPOS);
        e.getAi().nextMove(); // Moves enemy after player moves.
    }
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        setSize(584, 384);
        areaWidth = this.getHeight();
        areaHeight = this.getHeight();
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
        basicTile = getImage(mainURL, "images/defaultTile.png");
    }
     
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
        textOutput.append("Who are you?\n");
    }
    
     // For the thread above.
    public void run() {
        while (2<3){ // WHY ALEX WHY??????
            repaint();
            threadSleep();
        }
    }
    
    private int prevWid     = this.getWidth();
    private int prevHeight  = this.getHeight();
    
    //Double Buffer. If something graphically bugs, try here.
    ///*
    public void update(Graphics mainGraphics){
        if (image == null){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
        }
        if (this.getWidth() != prevWid || this.getHeight() != prevHeight){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
            prevWid = this.getWidth();
            prevHeight = this.getHeight();
        }
        graphicsBuffer.setColor(getBackground());
        graphicsBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
        graphicsBuffer.setColor(getForeground());
        paint(graphicsBuffer);
        mainGraphics.drawImage(image, 0, 0, this);
    }
    //*/
    
    
    public void threadSleep(){
        try {
            Thread.sleep(framerate);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void paint(Graphics mainGraphics){
        mainGraphics.drawImage(basicTile, Integer.MAX_VALUE, Integer.MAX_VALUE, this);
        mainGraphics.setColor(Color.black);
        mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        areaHeight = this.getHeight();
        areaWidth = this.getHeight();
        for (int b = 0; b < mainMap.tiles.length; b++){
            for (int c = 0; c < mainMap.tiles[b].length; c++){
                if (c == activeXPOS && b == activeYPOS){
                    mainGraphics.drawImage(getImage(mainURL, 
                            mainMap.tiles[b][c].imagePath), 
                            c*areaWidth/mainMap.chunkWidth-1, 
                            b*areaHeight/mainMap.chunkHeight-1,
                            areaWidth/mainMap.chunkWidth+2, areaHeight/mainMap.chunkHeight+2,
                            new Color(200, 0, 0), 
                            this);
                }
                // Moves Enemy image 
                else if (c == e.getXPOS() && b == e.getYPOS()){
                    mainGraphics.drawImage(getImage(mainURL, 
                            e.getImagePath()), 
                            c*areaWidth/mainMap.chunkWidth-1, 
                            b*areaHeight/mainMap.chunkHeight-1,
                            areaWidth/mainMap.chunkWidth+2, areaHeight/mainMap.chunkHeight+2,
                            new Color(200, 0, 0), 
                            this);
                }
                else{ 
                    mainGraphics.drawImage(getImage(mainURL, 
                            mainMap.tiles[b][c].imagePath), 
                            c*areaWidth/mainMap.chunkWidth-1, 
                            b*areaHeight/mainMap.chunkHeight-1, 
                            areaWidth/mainMap.chunkWidth+2, areaHeight/mainMap.chunkHeight+2,
                            this);
                }
            }
        }
        textOutput.setLocation(areaWidth, 26);
        textOutput.setSize(this.getWidth()-areaWidth, this.getHeight()-25);
        textInput.setLocation(areaWidth, 0);
        textInput.setSize(this.getWidth()-areaWidth, 25);
        super.paint(mainGraphics);
    }
}
