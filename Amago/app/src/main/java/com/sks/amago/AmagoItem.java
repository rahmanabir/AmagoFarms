package com.sks.amago;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class AmagoItem {
    @SerializedName("uniqueID")
    private int uniqueID;
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
    @SerializedName("sellerName")
    private String sellerName;
    @SerializedName("itemStatus")
    private int itemStatus;

    public AmagoItem(int id, String n, float w, float p){
        uniqueID = id;
        itemName = n;
        itemPrice = p;
        itemAmount = w;
        isForSale = false;
        dateHarvested = GetDateTaken();
    }

    public int getUniqueID() {return uniqueID;}
    public String getItemName() {return itemName;}
    public float getItemPrice() {return itemPrice;}
    public float getItemAmount() {return itemAmount;}
    public boolean isForSale() {return isForSale;}
    public String getSellerName() {return sellerName;}
    public int getItemStatus() {return itemStatus;}

    public void setUniqueID(int uniqueID) {this.uniqueID = uniqueID;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    public void setItemPrice(float itemPrice) {this.itemPrice = itemPrice;}
    public void setItemAmount(float itemAmount) {this.itemAmount = itemAmount;}
    public void setForSale(boolean forSale) {isForSale = forSale;}
    public void getSellerName(String sName) {this.sellerName = sName;}
    public void getItemStatus(int status) {this.itemStatus = status;}

    public static String GetDateTaken(){
        return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
}
