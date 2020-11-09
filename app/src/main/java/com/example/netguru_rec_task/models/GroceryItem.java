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
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @ColumnInfo(name = "quantity")
    private int quantity;
    @ColumnInfo(name = "completed")
    private boolean completed;
    @ColumnInfo(name = "shopping_list_id")
    private int shoppingListId;


    public GroceryItem(String itemName, long timestamp, int quantity, int shoppingListId) {
        this.itemName = itemName;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.shoppingListId = shoppingListId;
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

    public int getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
