/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Button;
import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author natha
 */
public class Inventory {
    private HashMap<String, Item> inventory = new HashMap<>();
    private Button[] iButtons;
    private int iSize; // # should be perfect square
    private int row = 0;
    private int columns;
    private Game g;
    
    public Inventory(int size, Game g){
        this.iSize = size;
        this.columns = (int) Math.sqrt(iSize);
        this.g = g;
        this.iButtons = new Button[iSize];
        for (int i = 0; i < iButtons.length; i++){
            if (row > (int) Math.sqrt(iSize)) row++;
            this.iButtons[i] = new Button("Test");
            this.iButtons[i].setBackground(Color.gray);
            this.iButtons[i].setForeground(Color.green);
            
            System.out.println("test " + (i - (columns * row) ) * (g.areaWidth / columns));
            System.out.println(row * g.areaHeight / columns);
            System.out.println(g.areaWidth / columns);
            System.out.println(g.areaHeight / columns);
            System.out.println(iSize);
            System.out.println(columns);
            System.out.println(g.areaWidth);
            System.out.println(g.areaHeight);
            
            this.iButtons[i].setBounds(
                    (i - (columns * row) ) * (g.areaWidth / columns),
                    row * g.areaHeight / columns ,
                    g.areaWidth / columns ,
                    g.areaHeight / columns);
        }
    }
    
    public void addInventory(String s, Item i){
        inventory.put(s, i);
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Item> inventory) {
        this.inventory = inventory;
    }

    public Button[] getiButtons() {
        return iButtons;
    }

    public void setiButtons(Button[] iButtons) {
        this.iButtons = iButtons;
    }

    public int getiSize() {
        return iSize;
    }

    public void setiSize(int iSize) {
        this.iSize = iSize;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Game getG() {
        return g;
    }

    public void setG(Game g) {
        this.g = g;
    }
}
