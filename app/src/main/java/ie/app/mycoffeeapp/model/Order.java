package ie.app.mycoffeeapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Order {
    private HashMap<Product, Integer> order;
    private int price, amount;

    public Order(){
        order = new HashMap<>();
        price = 0;
        amount = 0;
    }
    public boolean addProduct(Product product){
        try {
            if(order.containsKey(product)) {
                order.put(product, order.get(product) + 1);
            }
            else{
                order.put(product,1);
            }
            price += product.getPriceInt();
            amount++;
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removeProduct(Product product){
        try{
            if(order.containsKey(product)) {
                if(Objects.requireNonNull(order.get(product)).compareTo(1) > 0){
                    order.put(product,order.get(product) -1);
                }
                else if(Objects.requireNonNull(order.get(product)).compareTo(1) == 0){
                    order.remove(product);
                }
                price-=product.getPriceInt();
                amount--;
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public String getPriceString(){
        return price + " đ";
    }

    public int getPriceInt(){return price;}

    public int getAmount() {
        return amount;
    }
}
