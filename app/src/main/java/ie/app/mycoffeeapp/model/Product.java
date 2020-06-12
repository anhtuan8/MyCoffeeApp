package ie.app.mycoffeeapp.model;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Product {
    private String id, name, image, price, category;
    private int productType; //Type 0: Food, Type 1: Beverage
    private boolean isOrdered;

    public Product(){

    }

    public Product(String id, String name, String image, String price, String category, int productType, boolean isOrdered ){
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.productType = productType;
        this.category = category;
        this.isOrdered = isOrdered;
    }

    public String getPrice() {
        return price;
    }

    public int getPriceInt(){
        StringBuilder digits = new StringBuilder();
        for(int i=0 ; i<price.length() ; i++){
            if( '0' <= price.charAt(i) && price.charAt(i) <= '9' ){
                digits.append(price.charAt(i));
            }
        }
        return Integer.parseInt(digits.toString());
    }

    public int getProductType() {
        return productType;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Product){
            return ((Product) obj).getId().equals(this.id);
        }
        else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "{"+ id + ", " + name +", " + category + "}";
    }
}
