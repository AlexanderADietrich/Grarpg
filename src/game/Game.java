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
        basicTile = getImage(mainURL, "images/defaultTile.png");
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
    
    //Double Buffer.
    public void update(Graphics mainGraphics){
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
        mainGraphics.drawImage(basicTile, Integer.MAX_VALUE, Integer.MAX_VALUE, this);
        mainGraphics.setColor(Color.black);
        mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        mainMap.areaHeight = this.getHeight();
        mainMap.areaWidth = this.getHeight();
        for (int b = 0; b < mainMap.tiles.length; b++){
            for (int c = 0; c < mainMap.tiles[b].length; c++){
                if (c == activeXPOS && b == activeYPOS){
                    System.out.println(mainURL);
                    mainGraphics.drawImage(getImage(mainURL, 
                            mainMap.tiles[b][c].imagePath), 
                            b*mainMap.areaWidth/mainMap.mapWidth, c*mainMap.areaHeight/mainMap.mapHeight,
                            mainMap.areaWidth/mainMap.mapWidth, mainMap.areaHeight/mainMap.mapHeight,
                            new Color(200, 0, 0), 
                            this);
                }
                else { 
                    mainGraphics.drawImage(getImage(mainURL, 
                            mainMap.tiles[b][c].imagePath), 
                            b*mainMap.areaWidth/mainMap.mapWidth, 
                            c*mainMap.areaHeight/mainMap.mapHeight, 
                            mainMap.areaWidth/mainMap.mapWidth, mainMap.areaHeight/mainMap.mapHeight,
                            this);
                }
            }
        }
        textOutput.setLocation(mainMap.areaWidth, 26);
        textOutput.setSize(this.getWidth()-mainMap.areaWidth, this.getHeight()-25);
        textInput.setLocation(mainMap.areaWidth, 0);
        textInput.setSize(this.getWidth()-mainMap.areaWidth, 25);
        super.paint(mainGraphics);
    }
}
