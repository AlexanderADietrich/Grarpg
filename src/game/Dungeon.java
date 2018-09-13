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
public class Dungeon extends Map{
    public Random r = new Random();
    public int entranceX;
    public int entranceY;
    
    
    public Dungeon(Game g) {
        width = 32;
        height = 32;
        tiles = new Tile[32][32];
        for (int i = 0; i < 32; i++){
            for (int b = 0; b < 32; b++){
                tiles[i][b] = new WallTile("images/wallTile.png", b, i);
            }
        }
        chunks = new Chunk[4][4];
        
        
        
        initialGeneration();
        treasureGeneration();
        
        //Display
        
        System.out.println("DUNGEON\n");
        
        for (int i = 0; i < tiles.length; i++){
            for (int b = 0; b < tiles[i].length; b++){
                System.out.print(tiles[i][b].imagePath.charAt(7));
            }
            System.out.println();
        }
        
        System.out.println("\n\n");
        
        boolean startChunk = false;
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < 4; i++){
            for (int b = 0; b < 4; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[y][x] = tiles[((i*8)+y)][((b*8)+x)];
                        if (((i*8)+y) == entranceY && ((b*8)+x) == entranceX){
                            startChunk = true;
                        }
                    }
                }
                chunks[i][b] = new Chunk(chunkTiles);
                if (startChunk) {
                    currentChunk = chunks[i][b];
                    chunkX = b;
                    chunkY = i;
                    startChunk = false;
                }
            }
        }
        
        
        currentChunk.passGame(g);
    }
    //TODO: Place Treasure at Treasure Location
    public void treasureGeneration(){
        //Find a walkable Tile
        int startX = (int) (r.nextDouble()*31);
        int startY = (int) (r.nextDouble()*31);
        while (true){
            if (tiles[startY][startX].imagePath.equals("images/defaultTile.png")) break;
            startX = (int) (r.nextDouble()*31);
            startY = (int) (r.nextDouble()*31);
        }
        
        int treasureX = (int) (r.nextDouble()*31);
        int treasureY = (int) (r.nextDouble()*31);
        
        while (Math.sqrt( Math.pow((startX-treasureX), 2) + Math.pow(startY-treasureY, 2)) < 12){
            treasureX = (int) (r.nextDouble()*31);
            treasureY = (int) (r.nextDouble()*31);
        }
        
        generatePath(startX, startY, treasureX, treasureY);
        this.tiles[treasureY][treasureX] = new TreasureTile(treasureX, treasureY, 4);
    }
    //TODO: Make and place "Entrance" and "Exit" Tiles.
    public void initialGeneration(){
        int initX = (int) (r.nextDouble()*31);
        int initY = (int) (r.nextDouble()*31);
        int goalX =     (int) (r.nextDouble()*31);
        int goalY =     (int) (r.nextDouble()*31);
        
        //Make Dungeons Have Some Length
        while (Math.sqrt( Math.pow((initX-goalX), 2) + Math.pow(initY-goalY, 2)) < 24){
            initX =     (int) (r.nextDouble()*31);
            initY =     (int) (r.nextDouble()*31);
            goalX =     (int) (r.nextDouble()*31);
            goalY =     (int) (r.nextDouble()*31);
        }
        
        this.entranceX = initX;
        this.entranceY = initY;
        
        generatePath(goalX, goalY, initX, initY);
    }
    
    public void generatePath(int goalX, int goalY, int startX, int startY){
        int currentX = startX;
        int currentY = startY;
        
        Boolean flipflop = true;
        
        while (true){
            System.out.println("goal\t" + goalX + " \t" + goalY);
            System.out.println("here\t" + currentX + " \t" + currentY);
            tiles[currentY][currentX] = new Tile("images/defaultTile.png", currentX, currentY);
            //Make a path randomly
            if (r.nextDouble() < 0.5){
                //X
                if (r.nextDouble() < 0.5){
                    //+
                    if (r.nextDouble() < 0.5 && currentX < tiles[currentY].length-1){
                        currentX++;
                    }
                    //-
                    else {
                        if (currentX > 0) currentX--;
                    }
                }
                //Y
                else {
                    //+
                    if (r.nextDouble() < 0.5 && currentY < tiles.length-1){
                        currentY++;
                    }
                    //-
                    else {
                        if (currentY > 0) currentY--;
                    }
                }
            } 
            //Make a path toward goal
            else {
                if (flipflop){
                    if (currentX < goalX){
                        currentX++;
                    }
                    else if (currentX > goalX){
                        currentX--;
                    }
                    else if (currentX == goalX){
                        if (currentY < goalY){
                            currentY++;
                        }
                        else if (currentY > goalY){
                            currentY--;
                        }
                    }
                } else {
                    if (currentY < goalY){
                        currentY++;
                    }
                    else if (currentY > goalY){
                        currentY--;
                    }
                    else if (currentY == goalY){
                        if (currentX < goalX){
                            currentX++;
                        }
                        else if (currentX > goalX){
                            currentX--;
                        }
                    }
                }
                flipflop = !flipflop;
            }
            if (currentX == goalX && currentY == goalY){
                break;
            }
        }
    }
    
}
