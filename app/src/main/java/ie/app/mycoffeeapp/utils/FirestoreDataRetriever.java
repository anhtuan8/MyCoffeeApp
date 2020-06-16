//package ie.app.mycoffeeapp.firebaseutils;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import ie.app.mycoffeeapp.model.Product;
//
//import static androidx.constraintlayout.widget.Constraints.TAG;
//
//public class FirestoreDataRetriever {
//    private static final String TAG = "FirestoreDataRetriever";
//
//    private FirebaseFirestore coffeeAppFirestore;
//    private HashMap<String, Product> menu = new HashMap<>();
//    private ArrayList<Product> products = new ArrayList<>();
//    private ArrayList<Product> food = new ArrayList<>();
//    private ArrayList<Product> beverage = new ArrayList<>();
//
//    public FirestoreDataRetriever(){
//        getProductList();
//    }
//
//    /**
//     * get all product from menu
//     */
//    private void getProductList(){
//        Log.d(TAG, "getArticlesFromDB: called");
//        coffeeAppFirestore = FirebaseFirestore.getInstance();
//        final CollectionReference artistRef = coffeeAppFirestore.collection("menu");
//        artistRef.orderBy("id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Product product = document.toObject(Product.class);
//                        product.setOrdered(false);
//                        if(product.getCategory().equalsIgnoreCase("BÁNH & SNACK")){
//                            product.setProductType(1);
//                            food.add(product);
//                        }
//                        else {
//                            product.setProductType(0);
//                            beverage.add(product);
//                        }
//                        products.add(product);
//                    }
//                    Log.d(TAG, "onComplete: products size " + beverage.size());
//                } else {
//                    Log.d("data", "Error getting documents: ", task.getException());
//                }
//            }
//        });
//    }
//
//    public ArrayList<Product> getBeverage() {
//        return beverage;
//    }
//
//    public ArrayList<Product> getFood() {
//        return food;
//    }
//
//    public ArrayList<Product> getProducts() {
//        return products;
//    }
//
//}
