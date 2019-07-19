package com.app.sample.model;

public class MaterialItem {

    String ItemName = "";

    String ItemQty = "";

    String ItemCost = "";

    String ItemTax = "";

    String ItemTotal = "";

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    String ItemID = "";

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    String ItemDesc = "";

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemQty() {
        return ItemQty;
    }

    public void setItemQty(String itemQty) {
        ItemQty = itemQty;
    }

    public String getItemCost() {
        return ItemCost;
    }

    public void setItemCost(String itemCost) {
        ItemCost = itemCost;
    }

    public String getItemTax() {
        return ItemTax;
    }

    public void setItemTax(String itemTax) {
        ItemTax = itemTax;
    }

    public String getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(String itemTotal) {
        ItemTotal = itemTotal;
    }
}
