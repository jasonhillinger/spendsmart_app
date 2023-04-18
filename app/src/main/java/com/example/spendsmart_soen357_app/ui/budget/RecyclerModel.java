package com.example.spendsmart_soen357_app.ui.budget;

public class RecyclerModel {
    private int imageview;
    private String priceview;
    private String nameview;
    private String categoryview;

    RecyclerModel (int imageview, String priceview, String nameview, String categoryview){
        this.imageview = imageview;
        this.priceview = priceview;
        this.nameview = nameview;
        this.categoryview = categoryview;

    }

    public int getImageview() {
        return imageview;
    }

    public String getPriceview() {
        return priceview;
    }

    public String getNameview() {
        return nameview;
    }

    public String getCategoryview() {
        return categoryview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public void setPriceview(String priceview) {
        this.priceview = priceview;
    }

    public void setNameview(String nameview) {
        this.nameview = nameview;
    }

    public void setCategoryview(String categoryview) {
        this.categoryview = categoryview;
    }
}
