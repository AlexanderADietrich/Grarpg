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
    public int width;
    public int height;
    public Tile[][] tiles;
    public Chunk[][] chunks;
    private Random rand = new Random();
    public Map(){
        width = 256;
        height = 256;
        tiles = new Tile[256][256];
        for (int i = 0; i < 256; i++){
            for (int b = 0; b < 256; b++){
                tiles[i][b] = new Tile("images/defaultTile.png", b, i);
            }
        }
        chunks = new Chunk[32][32];
        for (int i = 0; i < 32; i++){
            for (int b = 0; b < 32; b++){
                chunks[b][i] = new Chunk(8, 8);
            }
        }
        for (int i = 0; i < 10; i++){
            tiles = generateMountain(tiles, rand.nextInt(width-24), rand.nextInt(height-24));
        }
        for (int i = 0; i < 6; i++){
            tiles = generateOcean(tiles, rand.nextInt(width-48), rand.nextInt(height-48));
        }
        
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < 32; i++){
            for (int b = 0; b < 32; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[x][y] = tiles[b*8+x][i*8+y];
                    }
                }
                chunks[b][i] = new Chunk(chunkTiles);
            }
        }
    }
    public Tile[][] generateMountain(Tile[][] input, int locX, int locY){
        Tile[][] mountainBounds = new Tile[24][24];
        for (int i = 0; i < 24; i++){
            for (int b = 0; b < 24; b++){
                mountainBounds[b][i] = new Tile("images/defaultTile.png", locX+b, locY+i);
            }
        }
        int ax = rand.nextInt(24);
        int ay = rand.nextInt(24);
        int r = rand.nextInt(16);
        while (true){
            System.out.println(ax + "" + ay);
            if (ax >= 22 || ay >= 22 || ax < 2 || ay < 2) break;
            mountainBounds[ax][ay] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax+1][ay] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax+2][ay] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax-1][ay] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax-2][ay] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay+1] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay+2] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay-1] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            mountainBounds[ax][ay-2] = new MountainTile("images/mountainTile.png", locX+ax, locY+ay);
            r = rand.nextInt(16);
            if (r <= 8) ax++;
            if (r <= 7) ax-=2;
            if (r <= 12) ay++;
            if (r == 13) ay-=2;
        }
        for (int i = 0; i < 24; i++){
            for (int b = 0; b < 24; b++){
                input[b+locX][i+locY] = mountainBounds[b][i];
            }
        }
        return input;
    }
    public Tile[][] generateOcean(Tile[][] input, int locX, int locY){
        Tile[][] oceanBounds = new Tile[48][48];
        for (int i = 0; i < 48; i++){
            for (int b = 0; b < 48; b++){
                oceanBounds[i][b] = new Tile("images/defaultTile.png", locX+i, locY+b);
            }
        }
        int ax = rand.nextInt(24);
        int ay = rand.nextInt(24);
        int r = rand.nextInt(16);
        while (true){
            System.out.println(ax + "" + ay);
            if (ax >= 47 || ay >= 47 || ax < 1 || ay < 1) break;
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
        for (int i = 0; i < 48; i++){
            for (int b = 0; b < 48; b++){
                input[i+locX][b+locY] = oceanBounds[i][b];
            }
        }
        return input;
    }
}
