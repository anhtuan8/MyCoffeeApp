package ie.app.mycoffeeapp.model;

public class Product {
    private String productId, productName, productImageLink ;
    private int productPrice, productType; //Type 0: Food, Type 1: Beverage
    private boolean mustTry;

    public Product(String productId, String productName, String productImageLink, int productPrice, int productType, boolean mustTry ){
        this.productId = productId;
        this.productName = productName;
        this.productImageLink = productImageLink;
        this.productPrice = productPrice;
        this.productType = productType;
        this.mustTry = mustTry;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductType() {
        return productType;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public String getProductName() {
        return productName;
    }
}
