package com.example.hobbyking.fragment;

public class WishListItem {

    public String title;
    public String price;
    public int resId;

    public WishListItem(String title, String price, int resId){
        this.title = title;
        this.price = price;
        this.resId = resId;
    }

    public String getClass_title() {
        return title;
    }

    public void setClass_title(String title) {
       this.title = title;
    }

    public String getClass_price() {
        return price;
    }

    public void setClass_price(String price) {
        this.price = price;
    }

    public int getClass_image_id() {
        return resId;
    }

    public void setClass_image_id(int resId) {
        this.resId = resId;
    }
}
