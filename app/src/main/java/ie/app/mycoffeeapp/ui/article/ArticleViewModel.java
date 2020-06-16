/**
 * ViewModel cho activity đọc Article.
 * Cần: String articleContent, String articleTitle, String topicTitle, ArrayList<String> tagNames.
 * Cần viết hàm lấy các data trên từ Firebase theo articleId
 */

package ie.app.mycoffeeapp.ui.article;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import ie.app.mycoffeeapp.model.Article;

public class ArticleViewModel extends ViewModel {
    private static final String TAG = "ArticleViewModel";

    private final String articleId;
    private FirebaseFirestore coffeeAppFirestore;
    private FirebaseStorage coffeeAppStorage;
    private MutableLiveData<Article> article;
    Context context;

    public ArticleViewModel(String articleId, Context context){
        coffeeAppStorage = FirebaseStorage.getInstance();
        coffeeAppFirestore = FirebaseFirestore.getInstance();
        article = new MutableLiveData<>();
        this.articleId = articleId;
        this.context = context;
        getArticleFromDB(articleId);
    }

    public void getArticleFromDB(String id){
        // Create a storage reference from our app
        final StorageReference storageRef = coffeeAppStorage.getReference();

        final CollectionReference articleRef = coffeeAppFirestore.collection("articles");
        Query query = articleRef.whereEqualTo("article_id", id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        final Article art = document.toObject(Article.class);
                        // Create a reference with an initial file path and name
//                        final StorageReference pathReference = storageRef.child("articles/" + art.getArticle_id() + ".txt");
//                        pathReference.getBytes(Utils.ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                            @Override
//                            public void onSuccess(byte[] bytes) {
//                                // Data for "articles/articles_id.txt" is returns, use this as needed
//                                String contentHtml = new String(bytes);
//                                art.setDetail(contentHtml);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                art.setDetail("Nội dung không khả dụng. Hãy thử lại sau.");
//                                exception.printStackTrace();
//                            }
//                        });
                        article.setValue(art);
                        Log.d(TAG, "onComplete: article title " + art.getName());
                    }

                } else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public MutableLiveData<Article> getArticle() {
        return article;
    }

    public String getArticleId() {
        return articleId;
    }
}
