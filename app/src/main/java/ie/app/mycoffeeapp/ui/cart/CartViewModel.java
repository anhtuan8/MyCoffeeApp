package ie.app.mycoffeeapp.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.model.Order;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Order> order;

    public CartViewModel() {
        order = new MutableLiveData<>();
        order.setValue(MyCoffeeApplication.getOrder());
    }

    public void setOrder(Order order){
        this.order.setValue(order);
    }

    public MutableLiveData<Order> getOrder() {
        return order;
    }
}
