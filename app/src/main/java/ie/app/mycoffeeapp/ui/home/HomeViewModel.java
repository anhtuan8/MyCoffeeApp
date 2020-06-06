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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

//import ie.app.mycoffeeapp.EasyArtistApplication;

import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.model.Product;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeFragment";
    private MutableLiveData<HashMap<String,ArrayList<Article>>> categorizedArticles;
    private FirebaseFirestore coffeeAppFirestore;

//    private Context context;

    public HomeViewModel() {
        categorizedArticles = new MutableLiveData<>();
        coffeeAppFirestore = FirebaseFirestore.getInstance();
        getArticles();
//        this.context = context;
//        getHomeViewArticlesFromDB();
    }

    /**
     * Get all list articles for home view
     * @param
     */
    private void getArticles(){
        final HashMap<String,ArrayList<Article>> articles = new HashMap<>();
        Log.d(TAG, "getArticlesFromDB: called");
        final CollectionReference coffeeAppRef = coffeeAppFirestore.collection("articles");
        coffeeAppRef.orderBy("article_id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Article article = document.toObject(Article.class);
                        if(!articles.containsKey(article.getTopic())){
                                articles.put(article.getTopic(),new ArrayList<Article>());
                        }
                        Objects.requireNonNull(articles.get(article.getTopic())).add(article);
                    }
                    Log.d(TAG, "onComplete: thông báo size " + articles.get("Thông báo").size());
                    categorizedArticles.setValue(articles);
                }
                else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public MutableLiveData<HashMap<String, ArrayList<Article>>> getCategorizedArticles() {
        return categorizedArticles;
    }
}