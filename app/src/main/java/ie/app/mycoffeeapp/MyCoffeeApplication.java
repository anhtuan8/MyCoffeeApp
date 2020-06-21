package ie.app.mycoffeeapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import ie.app.mycoffeeapp.model.Order;
import ie.app.mycoffeeapp.model.Product;
import ie.app.mycoffeeapp.ui.order.OrderViewModel;

@SuppressLint("Registered")
public class MyCoffeeApplication extends Application {
    private static final String TAG = "MyCoffeeApplication";
    private static Order order;
    private static FirebaseUser user;
    private static OrderViewModel orderViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        orderViewModel = new OrderViewModel();
        order = new Order();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void setupToolbar(Toolbar toolbar, final AppCompatActivity activity){
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setHideOnContentScrollEnabled(true);
        }
        Log.v("Toolbar", String.valueOf(toolbar.getMenu().size()));
    }

    public static FirebaseUser updateUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }

    public static boolean addProduct(Product product){
        order.addProduct(product);
        return true;
    }

    public static boolean removeProduct(Product product){
        order.removeProduct(product);
        return true;
    }

    public static Order getOrder() {
        return order;
    }

    public static void updateOrderViewModelData(){
        orderViewModel.setOrder(order);
    }

    public static void setOrderViewModel(OrderViewModel orderViewModel) {
        MyCoffeeApplication.orderViewModel = orderViewModel;
    }

    public static boolean haveOrderViewModel(){
        return orderViewModel!=null;
    }

    //    public void updateToolbar(View toolbar){
//        AppCompatButton signInButton = toolbar.findViewById(R.id.button_sign_in);
//        AppCompatButton logOutButton = toolbar.findViewById(R.id.button_log_out);
//        signInButton.setVisibility(View.GONE);
//        logOutButton.setVisibility(View.VISIBLE);
//    }

}
