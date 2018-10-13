/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author voice
 */
public class Map {
    public int              ID = -1;
    public int              width;
    public int              height;
    public Tile[][]         tiles;
    public Chunk[][]        chunks;
    public int              chunkX = 0;
    public int              chunkY = 0;
    public Chunk            currentChunk;
    private Random          rand = new Random();
    private Game            g;
    public HashSet<Enemy>   allChildEntities = new HashSet<>();//Should Not Include Player
    
    
    public Map(){}
    public Map(Game g){
        this.g=g;
    }
    
    public Map(Game g, boolean b){
        this.g=g;
        this.generate(128, 128);
    }
    
    public void generate(int width, int height){
        this.width=width;
        this.height=height;
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++){
            for (int b = 0; b < height; b++){
                if (rand.nextBoolean()) tiles[i][b] = new Tile("images/defaultTile.png", b, i);
                else tiles[i][b] = new Tile("images/defaultTileR90.png", b, i);
            }
        }
        chunks = new Chunk[height / 8][width / 8];
        
        for (int i = 0; i < this.width/8; i++){
            //System.out.println((i+1)+"/" +((this.width/8)));
            generateOcean(tiles);
        }
        //for (int i = 0 ; i < 1; i++){
        for (int i = 0; i < this.width/4; i++){
            //System.out.println((i+1)+"/"+((this.width/4)));
            generateMountain(tiles);
        }
        
        
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < height / 8; i++){
            for (int b = 0; b < width / 8; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[y][x] = tiles[((i*8)+y)][((b*8)+x)];
                    }
                }
                chunks[i][b] = new Chunk(chunkTiles);
            }
        }
        //System.out.println("DONE SPLITTING MAP");
        
        currentChunk = chunks[0][0];
        currentChunk.passGame(g);
    }
    
    Entity[][] temp;
    HashSet<Entity> temp2;
    public void update(){
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < this.width/8; i++){
            for (int b = 0; b < this.width/8; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[y][x] = tiles[((i*8)+y)][((b*8)+x)];
                    }
                }
                for (int a = 0; a < 8; a++){
                    for (int r = 0; r < 8; r++){
                        chunks[i][b].tiles[a][r] = chunkTiles[a][r];
                    }
                }
            }
        }
    }
    
    public void generateMountain(Tile[][] input){
        int locX = rand.nextInt((width*3)/4);
        int locY = rand.nextInt((height*3)/4);
        //System.out.println(locX + "locX " + locY + "locY ");
        Tile[][] mountainBounds = new Tile[height / 4][width / 4];
        for (int i = 0; i < height / 4; i++){
            for (int b = 0; b < width / 4; b++){
                if (rand.nextBoolean()) mountainBounds[i][b] = new Tile("images/defaultTile.png", b, i);
                else mountainBounds[i][b] = new Tile("images/defaultTileR90.png", b, i);
            }
        }
        
        int ax = rand.nextInt(width     / 4);
        int ay = rand.nextInt(height    / 4);
        int r = rand.nextInt(16);
        
        while (true){
            //System.out.println(ax + "" + ay);
            if (ax >= width / 4 - 2 || ay >= height / 4 - 2 || ax < 2 || ay < 2) break;
            mountainBounds[ax][ay]      = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax+1][ay]    = new MountainTile("images/mountainTile.png", locX+ax+1, locY+ay);
            mountainBounds[ax+2][ay]    = new MountainTile("images/mountainTile.png", locX+ax+2, locY+ay);
            mountainBounds[ax-1][ay]    = new MountainTile("images/mountainTile.png", locX+ax-1, locY+ay);
            mountainBounds[ax-2][ay]    = new MountainTile("images/mountainTile.png", locX+ax-2, locY+ay);
            mountainBounds[ax][ay+1]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay+1);
            mountainBounds[ax][ay+2]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay+2);
            mountainBounds[ax][ay-1]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay-1);
            mountainBounds[ax][ay-2]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay-2);
            
            mountainBounds[ax+1][ay+1] = new MountainTile("images/mountainTile.png", locX+ax+1, locY+ay+1);
            mountainBounds[ax-1][ay+1] = new MountainTile("images/mountainTile.png", locX+ax-1, locY+ay+1);
            mountainBounds[ax-1][ay-1] = new MountainTile("images/mountainTile.png", locX+ax-1, locY+ay-1);
            mountainBounds[ax+1][ay-1] = new MountainTile("images/mountainTile.png", locX+ax-1, locY+ay-1);
                    
            r = rand.nextInt(16);
            if (r <= 3) ax++;
            else if (r <= 7) ax--;
            else if (r <= 11) ay++;
            else if (r <= 15) ay--;
        }
        for (int i = 0; i < height / 4; i++){
            for (int b = 0; b < width / 4; b++){
                if (input[i+locY][b+locX] != null && input[i+locY][b+locX].imagePath.startsWith("images/default")) input[i+locY][b+locX] = mountainBounds[i][b];
            }
        }
        
        //On outer mountain tile generate one Dungeon Entrance
        Boolean breaker = false;
        for (int i = 0; i < width / 4; i++){
            for (int b = 0; b < height / 4; b++){
                //Scan Nearby Tiles for a mix of Default and Mountain
                int d = 0;
                int m = 0;
                
                try {
                if (input[i+locY+1][b+locX] != null && input[i+locY+1][b+locX].imagePath.startsWith("images/default"))
                    d++;
                if (input[i+locY-1][b+locX] != null && input[i+locY-1][b+locX].imagePath.startsWith("images/default"))
                    d++;
                if (input[i+locY][b+locX+1] != null && input[i+locY][b+locX+1].imagePath.startsWith("images/default"))
                    d++;
                if (input[i+locY][b+locX-1] != null && input[i+locY][b+locX-1].imagePath.startsWith("images/default"))
                    d++;
                if (input[i+locY+1][b+locX] != null && input[i+locY+1][b+locX].imagePath.startsWith("images/mountain"))
                    m++;
                if (input[i+locY-1][b+locX] != null && input[i+locY-1][b+locX].imagePath.startsWith("images/mountain"))
                    m++;
                if (input[i+locY][b+locX+1] != null && input[i+locY][b+locX+1].imagePath.startsWith("images/mountain"))
                    m++;
                if (input[i+locY][b+locX-1] != null && input[i+locY][b+locX-1].imagePath.startsWith("images/mountain"))
                    m++;
                } catch (Exception ex) {}
                
                //Places Entrance Tile Once then Exits Function
                //System.out.println(d + " " + m + " test");
                if (d != 0 && m != 0){
                    //System.out.println("\nDING DING\n");
                    input[i+locY][b+locX] = new EntranceTile(b+locX, i+locY, this, g, new Dungeon(g));
                    //System.out.println("\n\nFound");
                    breaker = true;
                    break;
                }
            }
            if (breaker) break;
        }
        //System.out.println(breaker);
    }
    
    /**
     * Adds one enemy of a passed in type to all dungeons on map.
     * Enemy will necessarily have been created w/o accurate chunk, x, and y 
     * values, set them.
     * @param <E>
     * @param enemy 
     */
    public <E extends Enemy> void addDungeonMonsters(HashSet<E> enemies){
        E enemy;
        Iterator iter = enemies.iterator();
        boolean breaker = false;
        for (Tile[] tlist : tiles){
            for (Tile t : tlist){
                if (EntranceTile.class.isInstance(t)){
                    EntranceTile temp = (EntranceTile) t;
                    temp = temp.reverse;
                    if (Dungeon.class.isInstance(temp.Map)){//After here we've found a dungeon.
                        for (Tile[] tlist2 : temp.Map.tiles){
                            for (Tile t2 : tlist2){
                                if (t2.skillTraverse.equals("go")){
                                    if (Math.random() < 0.1){
                                        if (iter.hasNext()){
                                            enemy = (E) iter.next();
                                        } else {
                                            return;
                                        }
                                        temp.Map.chunks[t2.y / 8][t2.x / 8].addEntity(enemy, t2.x % 8, t2.y % 8);
                                        
                                        
                                        allChildEntities.add(enemy);
                                        System.out.println((t2.y % 8) + " " + (t2.x % 8));
                                        
                                        //Set up Enemy
                                        enemy.setYPOS(t2.y % 8);
                                        enemy.setXPOS(t2.x % 8);
                                        enemy.setChunk(temp.Map.chunks[t2.y / 8][t2.x / 8]);
                                        
                                        breaker = true;
                                    }
                                }
                                if (breaker) break;
                            }
                            if (breaker){
                                breaker = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public String[] waterAnimation = new String[] {"images/waterTile.png", "images/waterTileF1.5.png", "images/waterTileF2.png","images/waterTileF1.5.png"};
    public void generateOcean(Tile[][] input){
        int locX = rand.nextInt((width*3)/4);
        int locY = rand.nextInt((height*3)/4);
        Tile[][] oceanBounds = new Tile[height / 4][width / 4];
        for (int i = 0; i < width / 4; i++){
            for (int b = 0; b < height / 4; b++){
                if (rand.nextBoolean()) oceanBounds[i][b] = new Tile("images/defaultTile.png", b, i);
                else oceanBounds[i][b] = new Tile("images/defaultTileR90.png", b, i);
            }
        }
        int ax = rand.nextInt(width / 4);
        int ay = rand.nextInt(height / 4);
        int r = rand.nextInt(16);
        while (true){
            //System.out.println(ax + "" + ay);
            if (ax >= width / 4 - 1 || ay >= height / 4 - 1 || ax < 1 || ay < 1) break;
            oceanBounds[ax][ay] = new WaterTile(waterAnimation, locX+ax, locY+ay, 24);
            oceanBounds[ax+1][ay] = new WaterTile(waterAnimation, locX+ax+1, locY+ay, 24);
            oceanBounds[ax-1][ay] = new WaterTile(waterAnimation, locX+ax-1, locY+ay, 24);
            oceanBounds[ax][ay+1] = new WaterTile(waterAnimation, locX+ax, locY+ay+1, 24);
            oceanBounds[ax][ay-1] = new WaterTile(waterAnimation, locX+ax, locY+ay-1, 24);
            r = rand.nextInt(16);
            if (r <= 3) ax+=2;
            else if (r <= 7) ax-=2;
            else if (r <= 11) ay++;
            else if (r <= 15) ay--;
        }
        for (int i = 0; i < width / 4; i++){
            for (int b = 0; b < height / 4; b++){
                if (input[i+locY][b+locX] != null && input[i+locY][b+locX].imagePath.startsWith("images/default"))input[i+locY][b+locX] = oceanBounds[i][b];
            }
        }
    }
    
    public Map(String s, Game g){
        this.g=g;
        
        width = 64;
        height = 64;
        int a = 0;
        int b = 0;
        tiles = new Tile[width][height];
        
        //First character is newline, last is not found. TODO:fix
        s = s.substring(1, s.length()-1) + "_\n"; //INDEX OUTOF BOUNDS -2 WHEN TRYING TO LOAD GAME WAS THIS CHANGED????
        
        String[] list = s.split("\\n");
        for (String ss : list){
            //System.out.println(ss.length());
        }
        
        for (int i = 0; i < s.length(); i++){
            if (a == 64) break;
            String c = s.charAt(i) + "";
            //System.out.println(a + " " + b + " " + c.intern());
            switch (c) {
                case "_":
                    tiles[a][b] = new Tile("images/defaultTile.png", a, b);
                    //System.out.print("_");
                    b++;
                    break;
                case "m":
                    tiles[a][b] = new MountainTile("images/mountainTile.png", a, b);
                    //System.out.print("m");
                    b++;
                    break;
                case "w":
                    tiles[a][b] = tiles[a][b] = new WaterTile(waterAnimation, a, b, 24);
                    //System.out.print("w");
                    b++;
                    break;
                case "\n": 
                    //System.out.println("HGERE");
                    a++;
                    b=0;
                    break;
                default:
                    break;
            }
        }
        chunks = new Chunk[8][8];
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        //if (tiles[((i*8)+y)][((j*8)+x)] == null ) //System.out.println((i*8)+y + " " + (j*8)+x + " null");
                        chunkTiles[y][x] = tiles[((i*8)+y)][((j*8)+x)];
                    }
                }
                chunks[i][j] = new Chunk(chunkTiles);
            }
        }
        currentChunk = chunks[0][0];
        currentChunk.passGame(g);
        
    }
}
