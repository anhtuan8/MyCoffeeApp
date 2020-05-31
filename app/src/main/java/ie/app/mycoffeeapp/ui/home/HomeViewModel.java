package ie.app.mycoffeeapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
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

//import ie.app.mycoffeeapp.EasyArtistApplication;

import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.model.Product;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<HashMap<String,ArrayList<Article>>> categorizedArticles;
    private FirebaseFirestore coffeeAppFirestore;

    private FirebaseFirestore easyArtistDb;
//    private Context context;

    public HomeViewModel() {
        categorizedArticles = new MutableLiveData<>();
//        this.context = context;
//        getHomeViewArticlesFromDB();
    }

    /**
     * Get all list articles for home view
     * @param
     */
    /**
     * get all product from menu
     */
    private void getArticles(){
        final HashMap<String,ArrayList<Article>> articles = new HashMap<>();
        Log.d(TAG, "getArticlesFromDB: called");
        final CollectionReference coffeeAppRef = coffeeAppFirestore.collection("articles");
        coffeeAppRef.orderBy("id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    public MutableLiveData<ArrayList<Article>> getmArticlesTopic1() {
        return mArticlesTopic1;
    }

    public MutableLiveData<ArrayList<Article>> getmArticlesTopic2() {
        return mArticlesTopic2;
    }

    public MutableLiveData<ArrayList<Article>> getmArticlesTopic3() {
        return mArticlesTopic3;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}