/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author voice
 */
public class Building extends Dungeon{
    
    public Building(Game g, int initx, int inity, int width, int height) {
        super(g);
        
        this.width =    ((width)  / 8)*8 + 8;
        this.height =   ((height) / 8)*8 + 8;
        
        //System.out.println("HERE " + this.width + " " +this.height);
        
        tiles = new Tile[this.width][this.height];
        
        for (int i = 0; i < this.height; i++){
            for (int b = 0; b < this.width; b++){
                tiles[i][b] = new WallTile("images/blackTile.png", b, i);
            }
        }
        chunks = new Chunk[this.width/8][this.height/8];
        
        int currentX =    1+(width/2);
        int currentY =    2;
        int flop =      0;
        boolean first = true;
        
        //Generate
        while (true){
            if (currentX >= 1 && currentY >= 1 && currentX < this.width-1 && currentY < this.height-1){
                squareGen(currentY, currentX, inity, initx, g);
            } else {
                break;
            }
            
            switch (flop) {
                case 0:
                    currentX+= 3;
                    break;
                case 1:
                    currentX-= 3;
                    break;
                case 2:
                    currentY+= 3;
                    break;
                case 3:
                    currentY-= 3;
                    break;
                default:
                    flop = 0;
                    break;
            }
            
            if (r.nextInt(8) == 1) flop++;
            if (r.nextInt(2) == 1) flop++;
            if (r.nextInt(8) == 7) break;
        }
        boolean breaker = false;
        for (int i = 0; i < this.height; i++){
            for (int b = 0; b < this.width; b++){
                //Scan Nearby Tiles for a mix of Default and Brick
                int d = 0;
                int m = 0;
                
                try {
                if (g.m.tiles[i+inity+1][b+initx] != null && g.m.tiles[i+inity+1][b+initx].imagePath.startsWith("images/default"))
                    d++;
                if (g.m.tiles[i+inity-1][b+initx] != null && g.m.tiles[i+inity-1][b+initx].imagePath.startsWith("images/default"))
                    d++;
                if (g.m.tiles[i+inity][b+initx+1] != null && g.m.tiles[i+inity][b+initx+1].imagePath.startsWith("images/default"))
                    d++;
                if (g.m.tiles[i+inity][b+initx-1] != null && g.m.tiles[i+inity][b+initx-1].imagePath.startsWith("images/default"))
                    d++;
                if (g.m.tiles[i+inity+1][b+initx] != null && g.m.tiles[i+inity+1][b+initx].imagePath.startsWith("images/brick"))
                    m++;
                if (g.m.tiles[i+inity-1][b+initx] != null && g.m.tiles[i+inity-1][b+initx].imagePath.startsWith("images/brick"))
                    m++;
                if (g.m.tiles[i+inity][b+initx+1] != null && g.m.tiles[i+inity][b+initx+1].imagePath.startsWith("images/brick"))
                    m++;
                if (g.m.tiles[i+inity][b+initx-1] != null && g.m.tiles[i+inity][b+initx-1].imagePath.startsWith("images/brick"))
                    m++;
                } catch (Exception ex) {}
                
                //System.out.println(d + " " + m + "test");
                //Places Entrance Tiles Once then Exits Function
                if (d == 1 && m == 3){
                    //System.out.println("\nDING DING\n");
                    this.entranceY = i;
                    this.entranceX = b;
                    EntranceTile temp = new EntranceTile(b+initx, i+inity, g.m, g, this);
                    g.m.tiles[i+inity][b+initx] = temp;
                    tiles[i][b] = (EntranceTile) temp.reverse;
                    breaker = true;
                    break;
                }
            }
            if (breaker) break;
        }
        
        g.m.update();
        
        //Display
        
        boolean startChunk = false;
        Tile[][] chunkTiles = new Tile[8][8];
        for (int i = 0; i < this.height/8; i++){
            for (int b = 0; b < this.width/8; b++){
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x < 8; x++){
                        chunkTiles[y][x] = tiles[((i*8)+y)][((b*8)+x)];
                        if (((i*8)+y) == entranceY && ((b*8)+x) == entranceX){
                            //System.out.println("\n\n HERE \n\n");
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
    
    
    
    public void squareGen(int currentY, int currentX, int inity, int initx, Game g){
            //Generate a 3x3 square.
            
            //Center
            this.tiles[currentY][currentX] = new Tile("images/defaultTile.png", currentX, currentY);
            g.m.tiles[inity+currentY][initx+currentX] = new WallTile("images/brickTile.png", initx+currentX, inity+currentY);
            
            //Horizontal Adjacents
            this.tiles[currentY+1][currentX] = new Tile("images/defaultTile.png", currentX, currentY+1);
            g.m.tiles[inity+currentY+1][initx+currentX] = new WallTile("images/brickTile.png", initx+currentX, inity+currentY+1);
            this.tiles[currentY-1][currentX] = new Tile("images/defaultTile.png", currentX, currentY-1);
            g.m.tiles[inity+currentY-1][initx+currentX] = new WallTile("images/brickTile.png", initx+currentX, inity+currentY-1);
            
            this.tiles[currentY][currentX+1] = new Tile("images/defaultTile.png", currentX+1, currentY);
            g.m.tiles[inity+currentY][initx+currentX+1] = new WallTile("images/brickTile.png", initx+currentX+1, inity+currentY);
            this.tiles[currentY][currentX-1] = new Tile("images/defaultTile.png", currentX-1, currentY);
            g.m.tiles[inity+currentY][initx+currentX-1] = new WallTile("images/brickTile.png", initx+currentX-1, inity+currentY);
            
            //System.out.println("THE GRARGLESNTHACH");
            
            //Diagonals
            this.tiles[currentY+1][currentX+1] = new Tile("images/defaultTile.png", currentX+1, currentY+1);
            g.m.tiles[inity+currentY+1][initx+currentX+1] = new WallTile("images/brickTile.png", initx+currentX+1, inity+currentY+1);
            this.tiles[currentY-1][currentX-1] = new Tile("images/defaultTile.png", currentX-1, currentY-1);
            g.m.tiles[inity+currentY-1][initx+currentX-1] = new WallTile("images/brickTile.png", initx+currentX-1, inity+currentY-1);
            
            this.tiles[currentY-1][currentX+1] = new Tile("images/defaultTile.png", currentX+1, currentY-1);
            g.m.tiles[inity+currentY-1][initx+currentX+1] = new WallTile("images/brickTile.png", initx+currentX+1, inity+currentY-1);
            this.tiles[currentY+1][currentX-1] = new Tile("images/defaultTile.png", currentX-1, currentY+1);
            g.m.tiles[inity+currentY+1][initx+currentX-1] = new WallTile("images/brickTile.png", initx+currentX-1, inity+currentY+1);
    }
}
