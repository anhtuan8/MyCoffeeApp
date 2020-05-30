package ie.app.mycoffeeapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Order {
    private HashMap<String, Integer> order;
    private String price;

    public Order(){
        order = new HashMap<>();
    }
    public void addProduct(String productId){
        if(order.containsKey(productId)) {
            order.put(productId, order.get(productId) + 1);
        }
        else{
            order.put(productId,1);
        }
    }

    public void removeProduct(String productId){
        if(order.containsKey(productId)) {
            if(Objects.requireNonNull(order.get(productId)).compareTo(1) > 0){
                order.put(productId,order.get(productId) -1);
            }
            else if(order.get(productId).compareTo(1) == 0){
                order.remove(productId);
            }
        }
    }

    public void setPrice(int price){
        this.price = price + "đ";
    }

    public void setPrice(String price){
        this.price = price + "đ";
    }

    public String getPrice(){
        return price;
    }

}
