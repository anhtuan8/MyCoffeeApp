/**
 * ViewModel cho activity đọc Article.
 * Cần: String articleContent, String articleTitle, String topicTitle, ArrayList<String> tagNames.
 * Cần viết hàm lấy các data trên từ Firebase theo articleId
 */

package ie.app.mycoffeeapp.ui.article;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.model.Article;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ArticleViewModel extends ViewModel {
    private final String articleId;
    private FirebaseFirestore easyArtistDb;
    private MutableLiveData<String> articleContent;
    private MutableLiveData<String> articleTitle;
    private MutableLiveData<String> articleImage;
    Context context;

    public ArticleViewModel(String articleId, Context context){
        articleContent = new MutableLiveData<>();
        articleContent.setValue("<h1>Article content</h1><p>Article content Article content Article content</p><img src=\"https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg\"/>");
        articleTitle = new MutableLiveData<>();
        articleTitle.setValue("My Coffee App presentation");
        articleImage = new MutableLiveData<>();
        articleImage.setValue("https://product.hstatic.net/1000075078/product/americano_copy_92414ac8e0634fb48ea72b21bc496b43_master.jpg");
//        easyArtistDb = FirebaseFirestore.getInstance();

        this.articleId = articleId;
        this.context = context;
//        getArticle();
    }

    public void getArticle(){
        final CollectionReference artistRef = easyArtistDb
                .collection("articles");
        Query query = artistRef.whereEqualTo("article_id", this.articleId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Article art = document.toObject(Article.class);
                        Activity activity = (Activity) context;
                        MyCoffeeApplication application = (MyCoffeeApplication) activity.getApplication();

                        articleContent.setValue(art.getDetail());
                        articleTitle.setValue(art.getName());
                        articleImage.setValue(art.getImage_link());
                        Log.d(TAG, "onComplete: article title " + art.getName());
                    }

                } else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public LiveData<String> getArticleContent() {
        return articleContent;
    }


    public LiveData<String> getArticleTitle() {
        return articleTitle;
    }

    public MutableLiveData<String> getArticleImage() {
        return articleImage;
    }

    public String getArticleId() {
        return articleId;
    }
}
