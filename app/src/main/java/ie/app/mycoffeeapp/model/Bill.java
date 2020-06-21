package ie.app.mycoffeeapp.model;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bill {
    private ArrayList<String> itemNames;
    private ArrayList<String> itemIds;
    private ArrayList<String> amount;
    private int price, statusCode; //0: đã hủy, 1: đang xử lý, 2: đã nhận hàng(hoàn thành)
    private String clientId, clientName, address, clientPhoneNumber, status, storeId, note, storeName;
    private String createdDate;

    public Bill(Order order, String clientId, String clientName, String address, String clientPhoneNumber, String note){
//        StringBuilder ids = new StringBuilder("");
//        StringBuilder names = new StringBuilder("");
//        StringBuilder amounts = new StringBuilder("");
//        HashMap<Product, Integer> orderedProducts = order.getOrder();
//        for(Product product: orderedProducts.keySet()){
//            names.append(product.getName()).append(",");
//            ids.append(product.getId()).append(" ");
////            amounts.append(orderedProducts.get(product)).append(" ");
//        }
//        itemNames = names.toString();
//        itemIds = ids.toString();
//        amount = amounts.toString();

        amount = new ArrayList<>();
        itemIds = new ArrayList<>();
        itemNames = new ArrayList<>();
        HashMap<Product, Integer> orderedProducts = order.getOrder();
        for(Product product: orderedProducts.keySet()){
            amount.add(orderedProducts.get(product).toString());
            itemIds.add(product.getId());
            itemNames.add(product.getName());
        }

        price = order.getPriceInt();

        this.clientId = clientId;
        this.clientName = clientName;
        this.address = address;
        this.clientPhoneNumber = clientPhoneNumber;
        this.status = "Đang xử lý";
        this.statusCode = 1;
        this.storeId = null;
        this.note = note;
        this.storeName = "Test store name";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = dtf.format(now);
    }

    public Bill(){}

//    public ArrayList<String> getItemIds() {
//        return new ArrayList<String>(Arrays.asList(itemIds.trim().split(" ")));
//    }

    public ArrayList<String> getItemIds() {
        return itemIds;
    }

    public int getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

//    public ArrayList<String> getAmount() {
//        return new ArrayList<String>(Arrays.asList(amount.trim().split(" ")));
//    }

    public ArrayList<String> getAmount() {
        return amount;
    }

//    public ArrayList<String> getItemNames() {
//        return new ArrayList<String>(Arrays.asList(itemNames.trim().split(",")));
//    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getPriceString(){
        StringBuilder result = new StringBuilder(" đ");
        String priceInt = String.valueOf(price);
        int counter = 0;
        for(int i=priceInt.length()-1 ; i>=0 ; i--){
            if (counter == 3 ){
                result.insert(0,".");
            }
            result.insert(0, priceInt.charAt(i));
            counter++;
        }
        return result.toString();
    }

    public String getNote() {
        return note;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

//    public void setItems(HashMap<Product, Integer> items) {
//        StringBuilder ids = new StringBuilder("");
//        StringBuilder names = new StringBuilder("");
//        StringBuilder amounts = new StringBuilder("");
//        for(Product product: items.keySet()){
//            names.append(product.getName()).append(" ");
//            ids.append(product.getId()).append(" ");
//            amounts.append(items.get(product)).append(" ");
//        }
//        itemNames = names.toString();
//        itemIds = ids.toString();
//        amount = amounts.toString();
//    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusByCode(int status) {
        this.statusCode = status;
        switch (status){
            case 0:
                this.status = "Đã hủy";
                break;
            case 1:
                this.status = "Đang xử lý";
                break;
            case 2:
                this.status = "Đã hoàn thành";
                break;
        }
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}

