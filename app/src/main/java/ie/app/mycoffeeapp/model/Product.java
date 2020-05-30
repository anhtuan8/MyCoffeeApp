package ie.app.mycoffeeapp.model;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        return "{"+ id + ", " + name +", " + category + "}";
    }
}
