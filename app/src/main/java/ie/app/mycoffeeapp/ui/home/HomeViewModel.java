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

//import ie.app.mycoffeeapp.EasyArtistApplication;

import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.model.Product;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> mArticlesTopic1, mArticlesTopic2, mArticlesTopic3;
    private ArrayList<Article> articles1 = new ArrayList<>();
    private ArrayList<Article> articles2= new ArrayList<>();
    private ArrayList<Article> articles3 = new ArrayList<>();

    private FirebaseFirestore easyArtistDb;
    private Context context;

    public HomeViewModel(Context context) {
        mArticlesTopic1 = new MutableLiveData<>();
        mArticlesTopic2 = new MutableLiveData<>();
        mArticlesTopic3 = new MutableLiveData<>();

        this.context = context;
        generateArticleList();
//        getHomeViewArticlesFromDB();
    }

    private void generateArticleList(){
        articles1.add(new Article("1",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/americano_copy_92414ac8e0634fb48ea72b21bc496b43_master.jpg",
                "Name",
                "created date",
                "Coffee"));
        articles1.add(new Article("2",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/bac_siu_099c749a7d6044bc9f7f0eac7f57808f_master.jpg",
                "Name",
                "created date",
                "Coffee"));
        articles1.add(new Article("3",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/cf_da_copy_a33d6e77f8da405bba9da541744dcea9_large.jpg",
                "Name",
                "created date",
                "Coffee"));

        articles2.add(new Article("1",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/americano_copy_92414ac8e0634fb48ea72b21bc496b43_master.jpg",
                "Name",
                "created date",
                "Topic 2"));
        articles2.add(new Article("2",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/bac_siu_099c749a7d6044bc9f7f0eac7f57808f_master.jpg",
                "Name",
                "created date",
                "Topic 2"));
        articles2.add(new Article("3",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/cf_da_copy_a33d6e77f8da405bba9da541744dcea9_large.jpg",
                "Name",
                "created date",
                "Topic 2"));

        articles3.add(new Article("1",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/americano_copy_92414ac8e0634fb48ea72b21bc496b43_master.jpg",
                "Name",
                "created date",
                "Topic 3"));
        articles3.add(new Article("2",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/bac_siu_099c749a7d6044bc9f7f0eac7f57808f_master.jpg",
                "Name",
                "created date",
                "Topic 3"));
        articles3.add(new Article("3",
                "Detail DetailDetailDetail",
                "https://product.hstatic.net/1000075078/product/cf_da_copy_a33d6e77f8da405bba9da541744dcea9_large.jpg",
                "Name",
                "created date",
                "Topic 3"));

        mArticlesTopic1.setValue(articles1);
        mArticlesTopic2.setValue(articles2);
        mArticlesTopic3.setValue(articles3);
    }

    /**
     * Get all list articles for home view
     * @param
     */
//    public void getHomeViewArticlesFromDB(){
//        Log.d(TAG, "getArticlesFromDB: called");
//        easyArtistDb = FirebaseFirestore.getInstance();
//        final CollectionReference artistRef = easyArtistDb.collection("articles");
//        artistRef.orderBy("article_id").limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Article art = document.toObject(Article.class);
//                        Activity activity = (Activity) context;
//                        EasyArtistApplication myApp = (EasyArtistApplication) activity.getApplication();
//                        if(myApp.getFavoriteList().contains(art.getArticle_id())){
//                            art.setFavorite(true);
//                            articlesIsFavorite.add(Boolean.TRUE);
//                        }
//                        else{
//                            art.setFavorite(false);
//                            articlesIsFavorite.add(Boolean.FALSE);
//                        }
//                        articles.add(art);
//                    }
//                    mArticles.setValue(articles);
//                    mArticlesIsFavorite.setValue(articlesIsFavorite);
//                    Log.d(TAG, "onComplete: mArticles size " + articles.size());
//                } else {
//                    Log.d("data", "Error getting documents: ", task.getException());
//                }
//            }
//        });
//    }

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