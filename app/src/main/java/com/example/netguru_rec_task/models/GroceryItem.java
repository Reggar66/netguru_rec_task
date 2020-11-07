package com.example.netguru_rec_task.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groceries_list")
public class GroceryItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "item_name")
    private String itemName;
    @ColumnInfo(name = "quantity")
    private int quantity;
    //Check if it works with SQLite. If not change to int: 1 = true, 0 = false
    @ColumnInfo(name = "completed")
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
