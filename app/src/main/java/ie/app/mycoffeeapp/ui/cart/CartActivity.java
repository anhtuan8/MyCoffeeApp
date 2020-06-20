package ie.app.mycoffeeapp.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Bill;
import ie.app.mycoffeeapp.model.Order;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";
    
    private MaterialButton confirmButton;
    private TextInputLayout nameInput, addressInput, phoneInput, noteInput;
    private TextInputEditText nameInputEdit, addressInputEdit, phoneInputEdit, noteInputEdit;
    private AppCompatTextView shippingFee, totalPrice, totalPriceInConfirmButton;
    private RecyclerView cartItemList;
    private FirebaseFirestore mFirestore;
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        shippingFee = findViewById(R.id.shipping_fee);
        totalPrice = findViewById(R.id.total_amount);

        confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(sendBill);
        totalPriceInConfirmButton = findViewById(R.id.price);

        nameInput = findViewById(R.id.name_input);
        addressInput = findViewById(R.id.address_input);
        phoneInput = findViewById(R.id.phone_input);
        noteInput = findViewById(R.id.note_input);
        nameInputEdit = findViewById(R.id.name_input_edittext);
        addressInputEdit = findViewById(R.id.address_input_edittext);
        phoneInputEdit = findViewById(R.id.phone_input_edittext);
        noteInputEdit = findViewById(R.id.note_input_edittext);
        cartItemList = findViewById(R.id.cart_item_list);
        cartItemList.setNestedScrollingEnabled(false);
        mFirestore = FirebaseFirestore.getInstance();
        order = MyCoffeeApplication.getOrder();
        initRecyclerView();
        fillInformation();
    }

    private void fillInformation(){
        //fill text view: name, phone number, total price, shipping fee, total price in confirm button

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            nameInputEdit.setText(user.getDisplayName());
            phoneInputEdit.setText(user.getPhoneNumber());
        }
        totalPrice.setText(order.getPriceString());
        totalPriceInConfirmButton.setText(order.getPriceString());
        shippingFee.setText("Miễn phí");
    }

    private void initRecyclerView(){
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(this, order);
        cartItemList.setAdapter(adapter);
        cartItemList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    public View.OnClickListener sendBill = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isValid = true;
            String requiredError = "Hãy nhập đúng thông tin bạn nhé!";
            //write bill to database and send to server.
            String sNameInput = nameInputEdit.getText().toString();
            String sPhoneInput = phoneInputEdit.getText().toString();
            String sAddressInput = addressInputEdit.getText().toString();
            String sNoteInput = noteInputEdit.getText().toString();

            if(!sNameInput.matches("[\\D]+")){
                nameInput.setErrorEnabled(true);
                nameInput.setError(requiredError);
                isValid = false;
            }
            else {
                nameInput.setError(null);
            }
            if(!sPhoneInput.matches("[0-9]+")){
                phoneInput.setErrorEnabled(true);
                phoneInput.setError(requiredError);
                isValid = false;
            }
            else {
                phoneInput.setError(null);
            }
            if(sAddressInput.matches("")){
                addressInput.setErrorEnabled(true);
                addressInput.setError(requiredError);
                isValid = false;
            }
            else {
                addressInput.setError(null);
            }
            if(isValid){
                try {
                    if (writeBilltoDB(sNameInput,sPhoneInput,sAddressInput,sNoteInput) && sendBilltoServer()) {
                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Lỗi đặt hàng", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Lỗi đặt hàng",Toast.LENGTH_SHORT).show();
                }
            }

            //

            Log.d(TAG, "onClick: ");
        }
    };

    private boolean writeBilltoDB(String nameInput, String phoneInput, String addressInput, String noteInput){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return false;
        }
        else {
            Bill bill = new Bill(order,user.getUid(),nameInput,addressInput,phoneInput,noteInput);
            mFirestore.collection("bills").add(bill).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if(task.isSuccessful()) {
                        DocumentReference addedDocRef = task.getResult();
                        Log.d(TAG, "Added document with ID: " + addedDocRef.getId());
                    }
                    else {
                        Log.d("data", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
        return true;
    }

    private boolean sendBilltoServer(){
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
