package ie.app.mycoffeeapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import ie.app.mycoffeeapp.model.Order;
import ie.app.mycoffeeapp.model.Product;

@SuppressLint("Registered")
public class MyCoffeeApplication extends Application {
    private static final String TAG = "MyCoffeeApplication";
    private Order order;

    @Override
    public void onCreate() {
        super.onCreate();
        order = new Order();
    }

    public static void setupToolbar(Toolbar toolbar, AppCompatActivity activity){
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setHideOnContentScrollEnabled(true);
        }
        Log.v("Toolbar", String.valueOf(toolbar.getMenu().size()));
    }

}
