/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

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
    
    public Map(){}
    public Map(Game g){
        this.g=g;
    }
    
    public Map(Game g, boolean b){
        this.g=g;
        this.generate(64, 64);
    }
    
    public void generate(int width, int height){
        this.width=width;
        this.height=height;
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++){
            for (int b = 0; b < height; b++){
                tiles[b][i] = new Tile("images/defaultTile.png", b, i);
            }
        }
        chunks = new Chunk[height / 8][width / 8];
        
        for (int i = 0; i < 6; i++){
            System.out.println(i+"/6");
            generateOcean(tiles);
        }
        for (int i = 0; i < 10; i++){
            System.out.println(i+"/10");
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
        System.out.println("DONE SPLITTING MAP");
        
        currentChunk = chunks[0][0];
        currentChunk.passGame(g);
    }
    
    public void update(){
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < 8; i++){
            for (int b = 0; b < 8; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[y][x] = tiles[((i*8)+y)][((b*8)+x)];
                    }
                }
                chunks[i][b] = new Chunk(chunkTiles);
            }
        }
    }
    
    public void generateMountain(Tile[][] input){
        int locX = rand.nextInt((width*7)/8);
        int locY = rand.nextInt((height*7)/8);
        Tile[][] mountainBounds = new Tile[height / 8][width / 8];
        for (int i = 0; i < height / 8; i++){
            for (int b = 0; b < width / 8; b++){
                mountainBounds[b][i] = new Tile("images/defaultTile.png", locX+b, locY+i);
            }
        }
        
        int ax = rand.nextInt(width     / 8);
        int ay = rand.nextInt(height    / 8);
        int r = rand.nextInt(16);
        
        while (true){
            //System.out.println(ax + "" + ay);
            if (ax >= width / 8 - 2 || ay >= height / 8 - 2 || ax < 2 || ay < 2) break;
            mountainBounds[ax][ay]      = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax+1][ay]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax+2][ay]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax-1][ay]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax-2][ay]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay+1]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay+2]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay-1]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay-2]    = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            r = rand.nextInt(16);
            if (r <= 8) ax++;
            if (r <= 7) ax-=2;
            if (r <= 12) ay++;
            if (r == 13) ay-=2;
        }
        for (int i = 0; i < height / 8; i++){
            for (int b = 0; b < width / 8; b++){
                if (input[i+locY][b+locX] != null && input[i+locY][b+locX].imagePath.startsWith("images/default")) input[i+locY][b+locX] = mountainBounds[i][b];
            }
        }
        
        //On outer mountain tile generate one Dungeon Entrance
        Boolean breaker = false;
        for (int i = 0; i < width / 8; i++){
            for (int b = 0; b < height / 8; b++){
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
                
                //Places Entrance Tiles Once then Exits Function
                //System.out.println(d + " " + m + " test");
                if (d != 0 && m != 0){
                    //System.out.println("\nDING DING\n");
                    input[i+locY][b+locX] = new EntranceTile(b+locX, i+locY, this, g, new Dungeon(g));
                    System.out.println("\n\nFound");
                    breaker = true;
                    break;
                }
            }
            if (breaker) break;
        }
        System.out.println(breaker);
    }
    
    public void generateOcean(Tile[][] input){
        int locX = rand.nextInt((width*6)/8);
        int locY = rand.nextInt((height*6)/8);
        Tile[][] oceanBounds = new Tile[height / 4][width / 4];
        for (int i = 0; i < width / 4; i++){
            for (int b = 0; b < height / 4; b++){
                oceanBounds[i][b] = new Tile("images/defaultTile.png", locX+i, locY+b);
            }
        }
        int ax = rand.nextInt(width / 4);
        int ay = rand.nextInt(height / 4);
        int r = rand.nextInt(16);
        while (true){
            //System.out.println(ax + "" + ay);
            if (ax >= width / 4 - 1 || ay >= height / 4 - 1 || ax < 1 || ay < 1) break;
            oceanBounds[ax][ay] = new WaterTile("images/waterTile.png", locX+ax, locY+ay);
            oceanBounds[ax+1][ay] = new WaterTile("images/waterTile.png", locX+ax+1, locY+ay);
            oceanBounds[ax-1][ay] = new WaterTile("images/waterTile.png", locX+ax-1, locY+ay);
            oceanBounds[ax][ay+1] = new WaterTile("images/waterTile.png", locX+ax, locY+ay+1);
            oceanBounds[ax][ay-1] = new WaterTile("images/waterTile.png", locX+ax, locY+ay-1);
            r = rand.nextInt(16);
            if (r <= 8) ax++;
            if (r <= 5) ax-=3;
            if (r <= 12) ay++;
            if (r == 13) ay-=2;
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
        s = s.substring(1, s.length()-1) + "_\n";
        
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
                    tiles[a][b] = new WaterTile("images/waterTile.png", a, b);
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
                        //if (tiles[((i*8)+y)][((j*8)+x)] == null ) System.out.println((i*8)+y + " " + (j*8)+x + " null");
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
