package com.sks.amago;

import com.google.gson.annotations.SerializedName;
import java.util.Calendar;

public class AmagoItem {
    @SerializedName("uniqueID")
    private int uniqueID;
    @SerializedName("itemID")
    private int itemID;
    @SerializedName("dateHarvested")
    private String dateHarvested;
    @SerializedName("itemPrice")
    private int itemPrice;
    @SerializedName("itemAmount")
    private int itemAmount;

    @SerializedName("sellerName")
    private String sellerName;
    @SerializedName("itemStatus")
    private int itemStatus;

    public AmagoItem(int id, int n, int w){
        uniqueID = id;
        itemID = n;
//        itemPrice = p;
        itemAmount = w;
        itemStatus = 1;
        dateHarvested = GetDateTaken();
    }

    public AmagoItem(int id, int n, int w, int p){
        uniqueID = id;
        itemID = n;
        itemPrice = p;
        itemAmount = w;

        itemStatus = 2;
        dateHarvested = GetDateTaken();
    }

    public AmagoItem(int id, int n, int w, int p, String sn, int is){
        uniqueID = id;
        itemID = n;
        itemPrice = p;
        itemAmount = w;
        sellerName = sn;
        itemStatus = is;
        dateHarvested = GetDateTaken();
    }

    public int getUniqueID() {return uniqueID;}
    public int getItemType() {return itemID;}
//    public int getItemName() {return getResources().getStringArray(R.array.Produce)[itemID];}
    public int getItemPrice() {return itemPrice;}
    public int getItemAmount() {return itemAmount;}

    public String getSellerName() {return sellerName;}
    public int getItemStatus() {return itemStatus;}

    public void setUniqueID(int uniqueID) {this.uniqueID = uniqueID;}
    public void setItemName(int itemName) {this.itemID = itemName;}
    public void setItemPrice(int itemPrice) {this.itemPrice = itemPrice;}
    public void setItemAmount(int itemAmount) {this.itemAmount = itemAmount;}

    public void setSellerName(String sName) {this.sellerName = sName;}
    public void setItemStatus(int status) {this.itemStatus = status;}

    public static String GetDateTaken(){
        return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }

    public String showString(){
        return uniqueID+": "+itemID+" "+itemAmount+"kg "+itemPrice+"tk "+itemStatus+"\n";
    }
}
