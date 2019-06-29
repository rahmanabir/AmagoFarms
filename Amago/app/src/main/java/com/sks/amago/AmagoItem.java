package com.sks.amago;

import java.util.Calendar;

public class AmagoItem {
    private String itemName;
    private String dateHarvested;
    private float itemPrice;
    private float itemAmount;
    private boolean isForSale;
    private String uniqueID;

    public AmagoItem(String n, float w, float p){
        itemName = n;
        itemPrice = p;
        itemAmount = w;
        isForSale = false;
        dateHarvested = GetDateTaken();
    }

    public String getItemName() {return itemName;}
    public float getItemPrice() {return itemPrice;}
    public float getItemAmount() {return itemAmount;}
    public boolean isForSale() {return isForSale;}
    public String getUniqueID() {return uniqueID;}

    public void setItemName(String itemName) {this.itemName = itemName;}
    public void setItemPrice(float itemPrice) {this.itemPrice = itemPrice;}
    public void setItemAmount(float itemAmount) {this.itemAmount = itemAmount;}
    public void setForSale(boolean forSale) {isForSale = forSale;}
    public void setUniqueID(String uniqueID) {this.uniqueID = uniqueID;}

    public static String GetDateTaken(){
        return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
}
