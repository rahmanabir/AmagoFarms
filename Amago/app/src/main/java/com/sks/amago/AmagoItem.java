package com.sks.amago;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class AmagoItem {
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("dateHarvested")
    private String dateHarvested;
    @SerializedName("itemPrice")
    private float itemPrice;
    @SerializedName("itemAmount")
    private float itemAmount;
    @SerializedName("isForSale")
    private boolean isForSale;
    @SerializedName("uniqueID")
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
