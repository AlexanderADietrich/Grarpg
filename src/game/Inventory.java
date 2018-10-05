/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 *
 * @author Nathan Geddis
 */
public class Inventory {
    private HashMap<String, Item> inventory = new HashMap<>();
    private Button[] iButtons;
    private int iSize; // # should be perfect square
    private int row = 0;
    private int columns;
    private boolean clicked = false;
    private Game g;
    
    private class InventoryActionListener implements ActionListener{
        private String label;
        private Item i;
        public InventoryActionListener(String label, Item i){
            this.label = label;
            this.i = i;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            clicked = !clicked;
            //System.out.println(clicked);
            //if (clicked){
            g.append(label + "\n");
            if (i instanceof OneUseItem){
                g.append("Type commamd \"Use " + label + "\" \nto use this item." + "\n");
            }
            if (i instanceof EquipItem){
                g.append("Type commamd \"Equip " + label + "\" \nto equip this item." + "\n");
            }
        }
    }
    
    public Inventory(int size, Game g){
        this.iSize = size;
        this.columns = (int) Math.sqrt(iSize);
        this.g = g;
        this.iButtons = new Button[iSize];
        for (int i = 0; i < iButtons.length; i++){
            if (i >= (int) Math.sqrt(iSize)*(row+1) ) row++;
            this.iButtons[i] = new Button("Test");
            this.iButtons[i].setBackground(Color.gray);
            this.iButtons[i].setForeground(Color.green);
            
            /*System.out.println("test " + (i - (columns * row) ) * (g.areaWidth / columns));
            System.out.println(row * g.areaHeight / columns);
            System.out.println(g.areaWidth / columns);
            System.out.println(g.areaHeight / columns);
            System.out.println(iSize);
            System.out.println(columns);
            System.out.println(g.areaWidth);
            System.out.println(g.areaHeight);*/
            this.iButtons[i].setBounds(
                    (i - (columns * row) ) * (g.areaWidth / columns) +1,
                    row * g.areaHeight / columns +1,
                    2+(g.areaWidth / columns) ,
                    2+(g.areaHeight / columns));
        }
        int count = 0;
        for (String s : inventory.keySet()){
            iButtons[count].addActionListener(new InventoryActionListener(s, inventory.get(s)));
            iButtons[count++].setName(s);
        }
    }
    
    public void updateInventory(){
        row = 0;
        for (int i = 0; i < iButtons.length; i++){
            if (i >= (int) Math.sqrt(iSize)*(row+1) ) row++;
            this.iButtons[i].setBounds(
                    (i - (columns * row) ) * (g.areaWidth / columns) +1,
                    row * g.areaHeight / columns +1,
                    2+(g.areaWidth / columns) ,
                    2+(g.areaHeight / columns));
            
        }
        int count = 0;
        for (String s : inventory.keySet()){
            if (iButtons[count].getActionListeners().length == 0 ){
                iButtons[count].addActionListener(new InventoryActionListener(s, inventory.get(s)));
            } else {
                for (ActionListener a : iButtons[count].getActionListeners()){
                    iButtons[count].removeActionListener(a);
                }
                iButtons[count].addActionListener(new InventoryActionListener(s, inventory.get(s)));
            }      
            
            iButtons[count++].setLabel(s);
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
