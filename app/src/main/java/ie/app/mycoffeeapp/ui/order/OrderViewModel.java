package ie.app.mycoffeeapp.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.model.Order;

public class OrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Order> order;

    public OrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        order = new MutableLiveData<>();
        order.setValue(MyCoffeeApplication.getOrder());
    }

    public void setOrder(Order order){
        this.order.setValue(order);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Order> getOrder() {
        return order;
    }
}