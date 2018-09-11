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
    
    public Dungeon(Game g) {
        super(g);
        width = 32;
        height = 32;
        tiles = new Tile[32][32];
        for (int i = 0; i < 32; i++){
            for (int b = 0; b < 32; b++){
                tiles[b][i] = new WallTile("images/wallTile.png", b, i);
            }
        }
        chunks = new Chunk[2][2];
        
        
        
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
        
        System.out.println("\nDUNGEON");
        
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < 2; i++){
            for (int b = 0; b < 2; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[y][x] = tiles[((i*8)+y)][((b*8)+x)];
                    }
                }
                chunks[i][b] = new Chunk(chunkTiles);
            }
        }
        
        currentChunk = chunks[0][0];
        currentChunk.passGame(g);
    }
    //TODO: Place Treasure at Treasure Location
    public void treasureGeneration(){
        //Find a walkable Tile
        int startX = (int) (r.nextDouble()*31);
        int startY = (int) (r.nextDouble()*31);
        while (true){
            if (tiles[startX][startY].imagePath.equals("images/defaultTile.png")) break;
            startX = (int) (r.nextDouble()*31);
            startY = (int) (r.nextDouble()*31);
        }
        
        int treasureX = (int) (r.nextDouble()*31);
        int treasureY = (int) (r.nextDouble()*31);
        
        while (Math.sqrt( Math.pow((startX-treasureX), 2) + Math.pow(startY-treasureY, 2)) < 12){
            treasureX = (int) (r.nextDouble()*31);
            treasureX = (int) (r.nextDouble()*31);
        }
        
        generatePath(startX, startY, treasureX, treasureY);
    }
    //TODO: Make and place "Entrance" and "Exit" Tiles.
    public void initialGeneration(){
        int entranceX = (int) (r.nextDouble()*32);
        int entranceY = (int) (r.nextDouble()*32);
        int goalX = (int) (r.nextDouble()*32);
        int goalY = (int) (r.nextDouble()*32);
        
        //Make Dungeons Have Some Length
        while (Math.sqrt( Math.pow((entranceX-goalX), 2) + Math.pow(entranceY-goalY, 2)) < 24){
            entranceX = (int) (r.nextDouble()*31);
            entranceY = (int) (r.nextDouble()*31);
            goalX = (int) (r.nextDouble()*31);
            goalY = (int) (r.nextDouble()*31);
        }
        
        int currentX = entranceX;
        int currentY = entranceY;
        
        Boolean flipflop = true;
        
        generatePath(goalX, goalY, entranceX, entranceY);
    }
    
    public void generatePath(int goalX, int goalY, int startX, int startY){
        int currentX = startX;
        int currentY = startY;
        
        Boolean flipflop = true;
        
        while (true){
            System.out.println("goal\t" + goalX + " \t" + goalY);
            System.out.println("here\t" + currentX + " \t" + currentY);
            tiles[currentX][currentY] = new Tile("images/defaultTile.png", currentX, currentY);
            //Make a path randomly
            if (r.nextDouble() < 0.5){
                //X
                if (r.nextDouble() < 0.5){
                    //+
                    if (r.nextDouble() < 0.5){
                        currentX++;
                    }
                    //-
                    else {
                        currentX--;
                    }
                }
                //Y
                else {
                    //+
                    if (r.nextDouble() < 0.5){
                        currentY++;
                    }
                    //-
                    else {
                        currentX++;
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
