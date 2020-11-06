package com.example.netguru_rec_task.models;

public class GroceryItem {

    public int id;

    private String itemName;
    private int quantity;
    //Check if it works with SQLite. If not change to int: 1 = true, 0 = false
    private boolean completed;


    GroceryItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        completed = false;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
