package com.example.netguru_rec_task.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop_lists")
public class ShopListItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "list_name")
    private String listName;
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @ColumnInfo(name = "groceries_number")
    private int groceriesNumber;
    @ColumnInfo(name = "groceries_done")
    private int groceriesDone;
    @ColumnInfo(name = "archived")
    private boolean archived;

    @Ignore
    private boolean isSelected = false;


    public ShopListItem(String listName, long timestamp) {
        this.listName = listName;
        this.timestamp = timestamp;
        groceriesNumber = 0;
        groceriesDone = 0;
        archived = false;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getGroceriesNumber() {
        return groceriesNumber;
    }

    public void setGroceriesNumber(int groceriesNumber) {
        this.groceriesNumber = groceriesNumber;
    }

    public int getGroceriesDone() {
        return groceriesDone;
    }

    public void setGroceriesDone(int groceriesDone) {
        this.groceriesDone = groceriesDone;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
