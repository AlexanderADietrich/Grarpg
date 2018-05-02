package game;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
//TEST COMMENT PLEASE WORK
/**
 *
 * @author voice
 */
public class Game extends Applet implements Runnable{
    private long                    framerate = 34;
    private Commands                commandHandler = new Commands(this);
    public  HashMap<String, Image>  images;
    private Image                   image;
    private Image                   basicTile;
    private Graphics                graphicsBuffer;
    public URL                      mainURL;
    public Map                      m = new Map();
    private TextField               textInput = new TextField("", 10);
    public TextArea                 textOutput = new TextArea(10, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Font                    mainFont = new Font(Font.MONOSPACED, 10, 15);
    public int                      activeXPOS = 0;
    public int                      activeYPOS = 0;
    public Player                   p = new Player(0, 0, "", "images/GoodGuy.png");
    public Enemy                    e = new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png", m.currentChunk);
    
    public int areaWidth;  //Width of Map Area (pixels).
    public int areaHeight; //Height of Map Area (pixels).
    
    public void passImages(HashMap<String, Image> imageInput){
        images = imageInput;
    }
    
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
        for(Entity[] elist : m.currentChunk.entities){
            for(Entity e : elist){
                if (e != null) e.doTick();
                
            }
        }
    }
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        m.currentChunk.entities[0][0] = p;
        m.currentChunk.entities[7][7] = e;
        for (Tile[] tlist : m.tiles){
            for (Tile t : tlist){
                if (t.imagePath.substring(7, 8).equals("d")) System.out.print("_");
                else System.out.print(t.imagePath.substring(7, 8));
            }
            System.out.println();
        }
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
        
        if (images == null){
            //Load in the "base tile" in order to gauge size for scaling.
            basicTile = getImage(mainURL, "images/defaultTile.png");
        }
    }
     
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
        
        textOutput.append("Who are you?\n");
        //Initialize Player and Enemy. TODO; Improve this.
        if (m.currentChunk == null) System.out.println("Chunk");
        if (p == null) System.out.println("Player");
        
        m.currentChunk.updateLoc(p, 0, 0);
        m.currentChunk.updateLoc(e, 0, 0);
    }
    
     // For the thread above.
    public void run() {
        while (3<5){ // Slightly but it can still be improved upon.
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
        if (images == null){ 
            mainGraphics.drawImage(basicTile, 
                    Integer.MAX_VALUE, 
                    Integer.MAX_VALUE, 
                    this);
        } else {
            mainGraphics.drawImage(images.get("images/defaultTile.png"), 
                    Integer.MAX_VALUE, 
                    Integer.MAX_VALUE, 
                    this);
            
        }
        
        //Sets background.
        mainGraphics.setColor(Color.black);
        mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //Sets the area that the map generates. 
        areaHeight = this.getHeight();
        areaWidth = this.getHeight();
        
        //Main rendering of the map. TODO: Make based on Chunks.
        for (int b = 0; b < m.currentChunk.tiles.length; b++) {
            for (int c = 0; c < m.currentChunk.tiles[b].length; c++) {
                if (images == null) {
                    mainGraphics.drawImage(getImage(mainURL,
                            m.currentChunk.tiles[b][c].imagePath),
                            c * areaWidth / m.currentChunk.chunkWidth - 1,
                            b * areaHeight / m.currentChunk.chunkHeight - 1,
                            areaWidth / m.currentChunk.chunkWidth + 2, areaHeight / m.currentChunk.chunkHeight + 2,
                            new Color(0, 0, 50),//This line could be used for day/night
                            this);
                } else {
                    mainGraphics.drawImage(images.get(m.currentChunk.tiles[b][c].imagePath),
                            c * areaWidth / m.currentChunk.chunkWidth - 1,
                            b * areaHeight / m.currentChunk.chunkHeight - 1,
                            areaWidth / m.currentChunk.chunkWidth + 2, areaHeight / m.currentChunk.chunkHeight + 2,
                            new Color(0, 0, 50),//This line could be used for day/night
                            this);
                }
                if (m.currentChunk.entities[b][c] != null) {
                    if (images == null) {
                        mainGraphics.drawImage(getImage(mainURL,
                                m.currentChunk.entities[b][c].getImagePath()),
                                c * areaWidth / m.currentChunk.chunkWidth - 1,
                                b * areaHeight / m.currentChunk.chunkHeight - 1,
                                areaWidth / m.currentChunk.chunkWidth + 2, areaHeight / m.currentChunk.chunkHeight + 2,
                                //new Color(0, 0, 50),//This line could be used for day/night
                                this);
                    } else {
                        mainGraphics.drawImage(images.get(m.currentChunk.entities[b][c].getImagePath()),
                                c * areaWidth / m.currentChunk.chunkWidth - 1,
                                b * areaHeight / m.currentChunk.chunkHeight - 1,
                                areaWidth / m.currentChunk.chunkWidth + 2, areaHeight / m.currentChunk.chunkHeight + 2,
                                //new Color(0, 0, 50),//This line could be used for day/night
                                this);
                    }
                }
            }
        }
        
        /* 
        Renders entire map for testing;
        
        for (int b = 0; b < m.tiles.length; b++){
            for (int c = 0; c < m.tiles[b].length; c++){
                mainGraphics.drawImage(getImage(mainURL, 
                    m.tiles[b][c].imagePath), 
                    c*areaWidth/m.tiles[0].length-1, 
                    b*areaHeight/m.tiles[0].length-1,
                    areaWidth/m.tiles[0].length+2, areaHeight/m.tiles[0].length+2,
                    new Color(0, 0, 50),
                    this);
            }
        }
        
        */
        
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
