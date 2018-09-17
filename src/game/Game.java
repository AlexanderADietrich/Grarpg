package game; //TEST
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
    private CombatCommands          combatCommandHandler = new CombatCommands(this);
    public  HashMap<String, Image>  images;
    private Image                   image;
    private Image                   basicTile;
    private Graphics                graphicsBuffer;
    public URL                      mainURL;
    public Map                      m = new Map(this, true);
    public Map[]                    maps = new Map[1];
    public int                      idCounter = 0;
    
    private TextField               textInput = new TextField("", 10);
    public TextArea                 textOutput = new TextArea(10, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Font                    mainFont = new Font(Font.MONOSPACED, 10, 15);
    public Player                   p = new Player(0, 0, "", "images/GoodGuy.png");
    public Enemy                    e = new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png", m.currentChunk);
    public boolean                  mapActive = false;
    public String                   worldMap = "";
    public Fight                    fight = new Fight(this);
    public boolean                  fighting = false;
    public boolean                  running = false;
    public Dungeon                  testDungeon = new Dungeon(this);
    public Namer                    nameGen = new Namer();
    
    
    public int areaWidth;  //Width of Map Area (pixels).
    public int areaHeight; //Height of Map Area (pixels).

    
    public Tile[] getPlayerAdjTiles(){
        Tile[] tiles = new Tile[4];
        int place = 0;
        if (p.getXPOS() < 7) tiles[place++] = m.currentChunk.tiles[p.getYPOS()][p.getXPOS()+1];
        if (p.getXPOS() > 1) tiles[place++] = m.currentChunk.tiles[p.getYPOS()][p.getXPOS()-1];
        if (p.getYPOS() < 7) tiles[place++] = m.currentChunk.tiles[p.getYPOS()+1][p.getXPOS()];
        if (p.getYPOS() > 1) tiles[place++] = m.currentChunk.tiles[p.getYPOS()-1][p.getXPOS()];
        
        if (place < 4){
            Tile[] temp = new Tile[place];
            System.arraycopy(tiles, 0, temp, 0, place);
            tiles = temp;
        }
        
        return tiles;
    }
    
    public Tile getPlayerTile(){
        return m.currentChunk.tiles[p.getYPOS()][p.getXPOS()];
    }
    
    public void addMap(Map m){
        if (idCounter < maps.length) {
            m.ID = idCounter;
            maps[idCounter++] = m;
        } else {
            System.out.println("EXPAND ARRAY");
            Map[] newArray = new Map[maps.length*2];
            for (int i = 0; i < maps.length; i++){
                newArray[i] = maps[i];
            }
            maps = newArray;
            
            m.ID = idCounter;
            maps[idCounter++] = m;
        }
    }
    
    public void passImages(HashMap<String, Image> imageInput){
        images = imageInput;
    }
    
    private ActionListener  textInputListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textInput.setText(" ");
            //System.out.println(e.getActionCommand());
            if (!fighting) commandHandler.parsePlayerCommand(e.getActionCommand(), p);
            else combatCommandHandler.parsePlayerCommand(e.getActionCommand(), p);
        }
    };
    
    public void startFight(Entity e, Entity e2){
        fight.start(new Entity[]{e, e2});
        fighting = true;
    }
    
    //Tick any timers/ AI's.
    public void doTick(){
        if (running){
            p.skillChecker.doSkillTick();
            if (fighting) fight.doTick();
            else{
                for(Entity[] elist : m.currentChunk.entities){
                    for(Entity e : elist){
                        //if (e != null && Enemy.class.isInstance(e)) System.out.println(e.getXPOS() + "" + e.getYPOS());
                        if (e != null) e.doTick();
                    }
                }
            }
        }
    }
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        m.currentChunk.entities[0][0] = p;
        m.currentChunk.entities[7][7] = e;
        Building b = new Building(this, 8, 8, 32, 32);
        for (Tile[] tlist : m.tiles){
            for (Tile t : tlist){
                if (t.imagePath.substring(7, 8).equals("d")){
                    //System.out.print("_");
                    worldMap = worldMap + "_";
                }
                else {
                    //System.out.print(t.imagePath.substring(7, 8));
                    worldMap = worldMap + t.imagePath.substring(7, 8);
                }
            }
            //System.out.println();
            worldMap = worldMap + "\n";
        }
        //System.out.print("TEST MAP SAVE \n" + worldMap );
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
    }
    
    public void switchMap(EntranceTile e){
        if (e.reverse.Map.ID < 0) addMap(e.reverse.Map);
        
        m.currentChunk.entities[p.getYPOS()][p.getXPOS()] = null;
        
        this.m = maps[e.reverse.Map.ID];
        
        m.currentChunk.entities[e.reverse.y % 8][e.reverse.x % 8] = p;
        
        //System.out.println("TILE AT\t " + e.reverse.x + "," + e.reverse.y);
        //System.out.println("ENTER AT\t " + (e.reverse.x % 8) + "," + (e.reverse.y % 8));
        //System.out.println(e.reverse.x);
        //System.out.println(e.reverse.reverse.reverse.x);
        
        p.setXPOS(e.reverse.x % 8);
        p.setYPOS(e.reverse.y % 8);
        p = (Player) m.currentChunk.entities[e.reverse.y % 8][e.reverse.x % 8];
    }
    
    public void reinit(Map m){
        this.m.currentChunk=null;
        this.m=null;
        this.m=m;
        
        worldMap = "";
        for (Tile[] tlist : m.tiles){
            for (Tile t : tlist){
                if (t.imagePath.substring(7, 8).equals("d")){
                    //System.out.print("_");
                    worldMap = worldMap + "_";
                }
                else {
                    //System.out.print(t.imagePath.substring(7, 8));
                    worldMap = worldMap + t.imagePath.substring(7, 8);
                }
            }
            //System.out.println();
            worldMap = worldMap + "\n";
        }
        
        m.currentChunk.entities[0][0] = new Player(0, 0, "", "images/GoodGuy.png");
        p = (Player) m.currentChunk.entities[0][0];
        m.currentChunk.entities[7][7] = new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png", m.currentChunk);
        e = (Enemy) m.currentChunk.entities[7][7];
        running = true;
    }
    
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
        
        textOutput.append("Type \"Start Game\" in \nthe box above and \npress enter to start.\n");
        //Initialize Player and Enemy. TODO; Improve this.
        //if (m.currentChunk == null) System.out.println("Chunk");
        //if (p == null) System.out.println("Player");
        
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
    
    
    public void paint(Graphics mainGraphics){
        doTick();
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
        if(!running){
            mainGraphics.setColor(Color.green);
            mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            mainGraphics.setColor(Color.black);
            mainGraphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20)); //Find better Font
            mainGraphics.drawString("Placeholder start screen Replace with gif \nor animaton at some point.",0 ,this.getHeight()/10 );
        }
        
        //Sets the area that the map generates. 
        areaHeight = this.getHeight();
        areaWidth = this.getHeight();
        
        if (!mapActive && running && !fighting){
            //Main rendering of the current section of map.

            for (int b = 0; b < m.currentChunk.tiles.length; b++) {
                for (int c = 0; c < m.currentChunk.tiles[b].length; c++) {
                    mainGraphics.drawImage(images.get(m.currentChunk.tiles[b][c].imagePath),
                            c * areaWidth / m.currentChunk.chunkWidth - 1,
                            b * areaHeight / m.currentChunk.chunkHeight - 1,
                            areaWidth / m.currentChunk.chunkWidth + 2, areaHeight / m.currentChunk.chunkHeight + 2,
                            new Color(0, 0, 50),//This line could be used for day/night
                            this);
                    if (m.currentChunk.entities[b][c] != null) {
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

        //Render fight graphics.
        else if (fighting){
                mainGraphics.drawImage(images.get(fight.entities[0].getImagePath()),
                                    areaWidth / 4 + 1,
                                    areaHeight / 4 + 1,
                                    areaWidth / 2 + 2, 
                                    areaHeight / 2 + 2,
                                    this);
                mainGraphics.drawImage(images.get(fight.entities[1].getImagePath()),
                                    2* areaWidth / 4 + 1,
                                    2* areaHeight / 4 + 1,
                                    areaWidth / 2 + 2, 
                                    areaHeight / 2 + 2,
                                    this);
                        //x, y, width, height, observer
                
        }
        //Renders entire map.
        else if (mapActive){
            for (int b = 0; b < m.tiles.length; b++){
                for (int c = 0; c < m.tiles[b].length; c++){
                    mainGraphics.drawImage(images.get(m.tiles[b][c].imagePath), 
                        c*areaWidth/m.tiles[0].length-1, 
                        b*areaHeight/m.tiles[0].length-1,
                        areaWidth/m.tiles[0].length+2, areaHeight/m.tiles[0].length+2,
                        new Color(0, 0, 50),
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
    }
}
