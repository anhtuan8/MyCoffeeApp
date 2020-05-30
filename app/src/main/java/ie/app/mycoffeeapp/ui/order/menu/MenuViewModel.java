package ie.app.mycoffeeapp.ui.order.menu;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ie.app.mycoffeeapp.model.Product;

public class MenuViewModel extends ViewModel {
    private static final String TAG = "MenuViewModel" ;
    private MutableLiveData<ArrayList<Product>> menu;
    private MutableLiveData<HashMap<String,ArrayList<Product>>> categorizedMenu;
    private FirebaseFirestore coffeeAppFirestore;
    int type;

    public MenuViewModel(int type) {
        menu = new MutableLiveData<>();
        categorizedMenu = new MutableLiveData<>();
//        FirestoreDataRetriever retriever = new FirestoreDataRetriever();
        coffeeAppFirestore = FirebaseFirestore.getInstance();
        this.type = type;
        getProductList();
    }

    /**
     * get all product from menu
     */
    private void getProductList(){
        final HashMap<String,ArrayList<Product>> beverage = new HashMap<>();
        final HashMap<String,ArrayList<Product>> food = new HashMap<>();
        Log.d(TAG, "getArticlesFromDB: called");
        final CollectionReference artistRef = coffeeAppFirestore.collection("menu");
        artistRef.orderBy("id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        product.setOrdered(false);
                        if(product.getCategory().equalsIgnoreCase("BÁNH & SNACK")){
                            product.setProductType(0);
                            if(!food.containsKey(product.getCategory())){
                                food.put(product.getCategory(),new ArrayList<Product>());
                            }
                            Objects.requireNonNull(food.get(product.getCategory())).add(product);
                        }
                        else {
                            product.setProductType(1);
                            if(!beverage.containsKey(product.getCategory())){
                                beverage.put(product.getCategory(),new ArrayList<Product>());
                            }
                            Objects.requireNonNull(beverage.get(product.getCategory())).add(product);
                        }
                    }
                    switch (type){
                        case 0:
                            categorizedMenu.setValue(food);
                            Log.d(TAG, "onComplete: food size " + food.get("BÁNH & SNACK").size());
                            break;
                        case 1:
                            categorizedMenu.setValue(beverage);
                            Log.d(TAG, "onComplete: beverage size " + beverage.size());
                            break;
                    }
                }else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public LiveData<ArrayList<Product>> getMenu() {
        return menu;
    }

    public MutableLiveData<HashMap<String, ArrayList<Product>>> getCategorizedMenu() {
        return categorizedMenu;
    }
}
