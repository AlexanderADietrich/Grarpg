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
    public Chunk           currentChunk = new Chunk(8, 8);
    private TextField       textInput = new TextField("", 10);
    public TextArea         textOutput = new TextArea(10, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Font            mainFont = new Font(Font.MONOSPACED, 10, 15);
    public int              activeXPOS = 0;
    public int              activeYPOS = 0;
    public Player           p = new Player(0, 0, "", "images/GoodGuy.png");
    public Enemy            e = new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png", currentChunk);
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
    
    //Tick any timers/ AI's.
    public void doTick(){
        for(Entity[] elist : currentChunk.entities){
            for(Entity e : elist){
                if (e != null) e.doTick();
            }
        }
    }
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        setSize(584, 384);
        areaWidth = this.getHeight();
        areaHeight = this.getHeight();
        setLayout(null);
        
        //Setup input box.
        textInput.addActionListener(textInputListener);
        textInput.setFont(mainFont);
        
        //Setup output box.
        textOutput.setEditable(false);
        textOutput.setFont(mainFont);
        
        //Add both to the applet.
        add(textInput);
        add(textOutput);
        
        //Attempt to access build folder.
        try {
            mainURL = getDocumentBase();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        //Load in the "base tile" in order to gauge size for scaling.
        basicTile = getImage(mainURL, "images/defaultTile.png");
    }
     
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
        
        textOutput.append("Who are you?\n");
        //Initialize Player and Enemy. TODO; Improve this.
        if (currentChunk == null) System.out.println("Chunk");
        if (p == null) System.out.println("Player");
        
        currentChunk.updateLoc(p, 0, 0);
        currentChunk.updateLoc(e, 0, 0);
    }
    
     // For the thread above.
    public void run() {
        while (3<5){ // Better? - Alex.
            repaint();
            threadSleep();
        }
    }
    
    private int prevWid     = this.getWidth();
    private int prevHeight  = this.getHeight();
    
    //Double Buffer. If something graphically bugs, try here.
    ///*
    public void update(Graphics mainGraphics){
        
        //If the buffer has not been rendered yet, create it.
        if (image == null){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
        }
        //If the applet has been resized, then recreate the buffer.
        if (this.getWidth() != prevWid || this.getHeight() != prevHeight){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
            prevWid = this.getWidth();
            prevHeight = this.getHeight();
        }
        //Render the buffer.
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
    
    private int currentTick = 0;
    
    public void paint(Graphics mainGraphics){
        if (currentTick == 5){
            doTick();
            currentTick = 0;
        }
        //"Draws" the basic tile. Necessary to get its width/height for scaling.
        mainGraphics.drawImage(basicTile, Integer.MAX_VALUE, Integer.MAX_VALUE, this);
        
        //Sets background.
        mainGraphics.setColor(Color.black);
        mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //Sets the area that the map generates. 
        areaHeight = this.getHeight();
        areaWidth = this.getHeight();
        
        //Main rendering of the map. TODO: Make based on Chunks.
        for (int b = 0; b < currentChunk.tiles.length; b++){
            for (int c = 0; c < currentChunk.tiles[b].length; c++){
                    if (currentChunk.entities[b][c] != null){
                        mainGraphics.drawImage(getImage(mainURL, 
                            currentChunk.entities[b][c].getImagePath()), 
                            c*areaWidth/currentChunk.chunkWidth-1, 
                            b*areaHeight/currentChunk.chunkHeight-1,
                            areaWidth/currentChunk.chunkWidth+2, areaHeight/currentChunk.chunkHeight+2,
                            new Color(0, 0, 50),/*This line could be used for day/night*/
                            this);
                    } else {
                        mainGraphics.drawImage(getImage(mainURL, 
                                currentChunk.tiles[b][c].imagePath), 
                                c*areaWidth/currentChunk.chunkWidth-1, 
                                b*areaHeight/currentChunk.chunkHeight-1,
                                areaWidth/currentChunk.chunkWidth+2, areaHeight/currentChunk.chunkHeight+2,
                                new Color(0, 0, 50),/*This line could be used for day/night*/
                                this);
                    }
            }
        }
        
        //Main player interface.
        textOutput.setLocation(areaWidth, 26);
        textOutput.setSize(this.getWidth()-areaWidth, this.getHeight()-25);
        textInput.setLocation(areaWidth, 0);
        textInput.setSize(this.getWidth()-areaWidth, 25);
        
        //Final line necessary for rendering.
        super.paint(mainGraphics);
        currentTick++;
    }
}
