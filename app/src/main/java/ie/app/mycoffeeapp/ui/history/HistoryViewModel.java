package ie.app.mycoffeeapp.ui.history;

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
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.model.Bill;

public class HistoryViewModel extends ViewModel {
    private static final String TAG = "HistoryViewModel";

    MutableLiveData<ArrayList<Bill>> bills;
    FirebaseFirestore firestore;

    public HistoryViewModel(){
        bills = new MutableLiveData<>();
        firestore = FirebaseFirestore.getInstance();
        getBillsByClientId(MyCoffeeApplication.updateUser().getUid());
//        getBillsByClientId("123131");
    }

    private void getBillsByClientId(String id){
        // Create a storage reference from our app

        final CollectionReference billRef = firestore.collection("bills");
        Query query = billRef.whereEqualTo("clientId", id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Bill> queryBills = new ArrayList<>();
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        final Bill bill = document.toObject(Bill.class);
                        queryBills.add(bill);
                        Log.d(TAG, "Completed getting document: " + document.getId());
                    }
                    bills.setValue(queryBills);
                    Log.d(TAG, "onComplete: completed set value for bills: " + bills.getValue());
                } else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public MutableLiveData<ArrayList<Bill>> getBills() {
        return bills;
    }
}
