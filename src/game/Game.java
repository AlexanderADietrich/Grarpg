package game; 
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author voice
 */
public class Game extends Applet implements Runnable{
    public long                     framerate = 15;
    public Commands                 commandHandler = new Commands(this);
    public CombatCommands           combatCommandHandler = new CombatCommands(this);
    public  HashMap<String, Image>  images;
    public HashMap<String, Image>   resized = new HashMap<>();
    public Image                    image;
    public Image                    basicTile;
    public Graphics                 graphicsBuffer;
    public URL                      mainURL;
    public Map                      m = new Map(this, true);
    public Map[]                    maps = new Map[1];
    public int                      idCounter = 0;
    
    public TextField                textInput = new TextField("", 10);
    public TextArea                 textOutput = new TextArea("", 10, 10, TextArea.SCROLLBARS_NONE);
    public KeyListener              k = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //Do Nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("pressed g ");
            //System.out.println(e.getExtendedKeyCode());
            keyHandler.handleKey(e.getExtendedKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //Do Nothing
        }
    };
    
    public String toAppend;
    public void append(String s){
        if (toAppend == null || toAppend == "")
            toAppend = s;
        else{
            toAppend += s;
        }
    }
    /*
    public MouseMotionListener      mml = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            System.out.println(e.getXOnScreen() + ", " + e.getYOnScreen());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            System.out.println(e.getX() + ", " + e.getY() + "__");
        }
    };*/
    
    public Font                     mainFont = new Font(Font.MONOSPACED, 10, 15);
    public Player                   p;
    public Enemy                    e;
    public boolean                  mapActive = false;
    public String                   worldMap = "";
    public Fight                    fight = new Fight(this);
    public boolean                  fighting = false;
    public boolean                  running = false;
    public boolean                  inventory = false;
    public Dungeon                  testDungeon = new Dungeon(this);
    public Namer                    nameGen = new Namer();
    public boolean                  useKeys = true;
    public KeyHandler               keyHandler = new KeyHandler(this);
    //public Button[]                 inventoryButtons = new Button[16];
    
    
    
    public int areaWidth;  //Width of Map Area (pixels).
    public int areaHeight; //Height of Map Area (pixels).
    
    
    /*
    TODO: 
    Add a HashSet to Chunks for High-Speed but inaccurate access for blanket
    statement type functions such as these.
    */
    public Enemy tempE;
    public void makeSound(int s){
        Iterator i = m.currentChunk.fastEntities.iterator();
        Entity e;                
        while (i.hasNext()){                    
            e = (Entity) i.next();
            if (Enemy.class.isInstance(e)){
                tempE = (Enemy) e;
                //Doubles Sound While Using WASD
                if (useKeys == false) tempE.getAi().incomingSound+=s;
                tempE.getAi().incomingSound += s;
            }
        }
    }
    
    public void switchInput(){
        //System.out.println(useKeys + "SWITCH");
        useKeys = !useKeys;
        textInput.setEditable(useKeys);
        textOutput.setEditable(useKeys);
        textInput.setText("");
    }
    
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
            //System.out.println("EXPAND ARRAY");
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
    
    public ActionListener  textInputListener = new ActionListener() {
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
        //System.out.println("running = "+running);
        if (running){
            keyHandler.doTick();
            p.skillChecker.doSkillTick();
            if (fighting) fight.doTick();
            else{
                Iterator i = m.currentChunk.fastEntities.iterator();
                
                //Avoid Concurrent Mod Exception
                Entity[] temparray = new Entity[m.currentChunk.fastEntities.size()];
                int sentinel = 0;
                while (i.hasNext()){
                    temparray[sentinel++] = (Entity) i.next();
                }
                
                for (Entity e : temparray){
                    //if (e != null && Enemy.class.isInstance(e)) //System.out.println(e.getXPOS() + "" + e.getYPOS());
                    if (e != null) e.doTick();
                }
            }
        }
    }
    
    public void mapToString(){
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
    }
    
    /*
    Applet runs init, then start, then paint..
    */
    public void init(){
        //TODO: MOVE TO BETTER LOCATION, LIKE MAP
        Building b = new Building(this, 8, 8, m.width/4, m.width/4);
        
        setSize(584, 384);
        areaWidth = this.getHeight();
        areaHeight = this.getHeight();
        setLayout(null);
        
        //Setup input box.
        textInput.addActionListener(textInputListener);
        textInput.setFont(mainFont);
        textInput.addKeyListener(k);
        textInput.setFocusTraversalKeysEnabled(false);
        
        //Setup output box.
        textOutput.setEditable(false);
        textOutput.setFont(mainFont);
        textOutput.setFocusable(false);
        
        //Add both to the applet.
        add(textInput);
        add(textOutput);
        //this.addMouseMotionListener(mml);
        
        m.currentChunk.passGame(this);
        p = new Player(0, 0, "", "images/GoodGuy.png", 49, this);
        m.currentChunk.addEntity(p, 0, 0);
        e = new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png", m.currentChunk);
        m.currentChunk.addEntity(e, 7, 7);
        m.chunks[0][1].addEntity(new BlindCreep(3, 3, "CREEPO", 1, p, "images/deafCreep.png", m.chunks[0][1]), 3, 3);
    }
    
    public void switchMap(EntranceTile e){
        if (e.reverse.Map.ID < 0) addMap(e.reverse.Map);
        
        //Remove From Current Map
        m.currentChunk.removeEntity(p.getXPOS(), p.getYPOS());
        
        this.m = maps[e.reverse.Map.ID];
        
        //Add To Map That Was Switched To
        m.currentChunk.addEntity(p, e.reverse.x % 8, e.reverse.y % 8);
        
        //System.out.println("TILE AT\t " + e.reverse.x + "," + e.reverse.y);
        //System.out.println("ENTER AT\t " + (e.reverse.x % 8) + "," + (e.reverse.y % 8));
        //System.out.println(e.reverse.x);
        //System.out.println(e.reverse.reverse.reverse.x);

        //p = (Player) m.currentChunk.entities[e.reverse.y % 8][e.reverse.x % 8];
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
        
        m.currentChunk.addEntity(new Player(0, 0, "", "images/GoodGuy.png", 32, this), 0, 0);
        p = (Player) m.currentChunk.entities[0][0];
        m.currentChunk.addEntity(new Enemy (7, 7, "BadGuy", 10, p, "images/BadGuy.png", m.currentChunk), 7, 7);
        e = (Enemy) m.currentChunk.entities[7][7];
        running = true;
    }
    
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
        
        textOutput.append("Type \"Start Game\" in \nthe box above and \npress enter to start.\n");
        //Initialize Player and Enemy. TODO; Improve this.
        //if (m.currentChunk == null) //System.out.println("Chunk");
        //if (p == null) //System.out.println("Player");
        
        m.currentChunk.finalizeMove(p, 0, 0);
        m.currentChunk.finalizeMove(e, 0, 0);
    }
    
     // For the thread above.
    public void run() {
        while (3<5){ // Slightly but it can still be improved upon.
            repaint();
            threadSleep();
        }
    }
    
    public int prevWid     = this.getWidth();
    public int prevHeight  = this.getHeight();
    
    //Double Buffer. If something graphically bugs, try here.
    ///*
    public void update(Graphics mainGraphics){
        
        //If the buffer has not been rendered yet, create it.
        if (image == null){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
        }
        //If the applet has been resized, then recreate the buffer.
        //As of 9/24/18, also update the inventory's size parameters.
        if (this.getWidth() != prevWid || this.getHeight() != prevHeight){
            image = createImage(this.getSize().width, this.getSize().height);
            graphicsBuffer = image.getGraphics();
            prevWid = this.getWidth();
            prevHeight = this.getHeight();
            p.inventory.updateInventory();
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
    Button[] btemplist;
    public void swapInventoryState(){
        //setLayout(new GridLayout(6, 6, 5, 5)); unnecessary
        if (inventory){
            inventory = false;
            btemplist = p.inventory.getiButtons();
            for (Button b: btemplist){     
                remove(b);
            }
        }
        else {
            inventory = true;
            p.inventory.updateInventory();
            btemplist = p.inventory.getiButtons();
            for (Button b: btemplist){     
                b.setFocusable(false);
                add(b);
                //System.out.println("X " + b.getX() + " Y " + b.getY() +
                //" H " + b.getHeight() + " W " + b.getWidth());
                //System.out.println("BUTTON");
            }
        }
    }
    
    long currentTime;
    long prevTime = System.currentTimeMillis();
    Entity etemp;
    double ttemp;
    public AnimatedTile attemp;
    public void paint(Graphics mainGraphics){
        //System.out.println("BIG\t" + m.chunkX + "\t" + m.chunkY + (m.chunks[m.chunkY][m.chunkX].g == null));
        
        //Section: FLUID TEXT BOX~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        if (toAppend != null){
            if (toAppend.length() > 0){
                //Makes it possible to overload the output but very difficult.
                for (int i = 0; i < 1+toAppend.length()/16; i++){
                    textOutput.append("" + toAppend.charAt(0));
                    toAppend = toAppend.substring(1);
                }
            }
        }
        
        //SECTION: TICK CONTROL~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        currentTime = System.currentTimeMillis();
        if ((((currentTime-prevTime)/1000.0)*24) > 1){
            //(milliseconds)/1000ms/s = seconds*24ticks/second
            for (int i = 0; i < (int) (((currentTime-prevTime)/1000.0)*24); i++){
                doTick();
            }
            prevTime = System.currentTimeMillis();
        }
        
        //SECTION: PREPARATORY~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        
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
        
        
        //SECTION: MAIN RENDERING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        
        if (!mapActive && running && !fighting && !inventory){
            //Main rendering of the current section of map.

            for (int b = 0; b < m.currentChunk.tiles.length; b++) {
                for (int c = 0; c < m.currentChunk.tiles[b].length; c++) {
                    mainGraphics.drawImage(images.get(m.currentChunk.tiles[b][c].imagePath),
                            c * areaWidth / m.currentChunk.chunkWidth - 1,
                            b * areaHeight / m.currentChunk.chunkHeight - 1,
                            areaWidth / m.currentChunk.chunkWidth + 2, areaHeight / m.currentChunk.chunkHeight + 2,
                            new Color(0, 0, 50),//This line could be used for day/night
                            this);
                    if (AnimatedTile.class.isInstance(m.currentChunk.tiles[b][c]) && Math.random() < 0.5){
                        attemp = (AnimatedTile) m.currentChunk.tiles[b][c];
                        attemp.doTick();
                    }
                }
            }
            //Ensures Entities are "top level"
            for (int b = 0; b < m.currentChunk.tiles.length; b++) {
                for (int c = 0; c < m.currentChunk.tiles[b].length; c++) {
                    if (m.currentChunk.entities[b][c] != null) {
                        etemp = m.currentChunk.entities[b][c];
                        ttemp = etemp.timer.check();
                        if (ttemp == -1.0){
                            //System.out.println("DONE ANIMATING");
                            m.currentChunk.finalizeMove(etemp, etemp.timer.dx, etemp.timer.dy);
                            ttemp = 1;
                        }
                        
                        mainGraphics.drawImage(images.get(etemp.getImagePath()),
                                    (c * areaWidth / m.currentChunk.chunkWidth - 1)     
                                            //percentage of animation done in direction * width of a tile
                                            +(int)((ttemp*etemp.timer.dx)*(areaWidth/m.currentChunk.chunkWidth)),
                                    (b * areaHeight / m.currentChunk.chunkHeight - 1)   
                                            //percentage of animation done in direction * width of a tile
                                            +(int)((ttemp*etemp.timer.dy)*(areaHeight/m.currentChunk.chunkWidth)),
                                    areaWidth / m.currentChunk.chunkWidth + 2,
                                    areaHeight / m.currentChunk.chunkHeight + 2,
                                    //new Color(0, 0, 50),//This line could be used for day/night
                                    this);
                        
                    }
                }
            }
        } 

        //Render fight graphics.
        else if (fighting){
                mainGraphics.setColor(Color.GREEN);
                mainGraphics.fillRect(0, 2* areaHeight / 4 -20 + 1, (int) fight.entities[0].getHP(), 10);
                mainGraphics.drawImage(images.get(fight.entities[0].getImagePath()),
                                    0 + 1,
                                    2* areaHeight / 4 + 1,
                                    areaWidth / 2 + 2, 
                                    areaHeight / 2 + 2,
                                    this);
                mainGraphics.fillRect(2* areaWidth / 4 + 1, 0 + 1, (int) fight.entities[1].getHP(), 10);
                mainGraphics.drawImage(images.get(fight.entities[1].getImagePath()),
                                    2* areaWidth / 4 + 1,
                                    0 + 20 + 1,
                                    areaWidth / 2 + 2, 
                                    areaHeight / 2 + 2,
                                    this);
                        //x, y, width, height, observer
                
        }
        //Renders entire map.
        else if (mapActive){
            for (int b = 0; b < m.tiles.length; b++){
                for (int c = 0; c < m.tiles[b].length; c++){
                    
                    if (!resized.containsKey((areaWidth/m.tiles[0].length+2) + m.tiles[b][c].imagePath)) 
                        resized.put((areaWidth/m.tiles[0].length+2) + m.tiles[b][c].imagePath,
                            images.get(m.tiles[b][c].imagePath).getScaledInstance(areaWidth/m.tiles[0].length+2, areaWidth/m.tiles[0].length+2, Image.SCALE_FAST));
                    mainGraphics.drawImage(resized.get((areaWidth/m.tiles[0].length+2) + m.tiles[b][c].imagePath),
                        c*areaWidth/m.tiles[0].length-1,
                        b*areaHeight/m.tiles[0].length-1,
                        this);
                }
            }
        }
        else if (inventory){
            mainGraphics.setColor(Color.red);
            mainGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
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
