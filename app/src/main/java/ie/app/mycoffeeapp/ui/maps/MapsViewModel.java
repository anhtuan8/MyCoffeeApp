package ie.app.mycoffeeapp.ui.maps;

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

import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.model.Store;

public class MapsViewModel extends ViewModel {
    private static final String TAG = "MapsViewModel";
    FirebaseFirestore coffeeAppFirestore;
    MutableLiveData<ArrayList<Store>> stores;

    public MapsViewModel(){
        stores = new MutableLiveData<>();
        coffeeAppFirestore = FirebaseFirestore.getInstance();
        getStoresFromDB();
    }

    private void getStoresFromDB(){
        Log.d(TAG, "getStores: called");
        final CollectionReference storesRef = coffeeAppFirestore.collection("stores");
        storesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Store> queryStore = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Store store = document.toObject(Store.class);
                        queryStore.add(store);
                        Log.d(TAG, "Successfully getting documents: " + document.getId());
                    }
                    stores.setValue(queryStore);
                }
                else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public MutableLiveData<ArrayList<Store>> getStores() {
        return stores;
    }
}
