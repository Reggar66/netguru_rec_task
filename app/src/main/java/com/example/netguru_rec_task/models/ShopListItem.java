package com.example.netguru_rec_task.models;

public class ShopListItem {

    public int id;

    private String listName;
    private long timestamp;
    private int groceriesNumber;
    private int groceriesDone;


    public ShopListItem(String listName, long timestamp) {
        this.listName = listName;
        this.timestamp = timestamp;
        groceriesNumber = 0;
        groceriesDone = 0;
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
}
