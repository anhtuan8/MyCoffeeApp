package ie.app.mycoffeeapp.ui.cart;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;

public class CartActivity extends AppCompatActivity {
    private MaterialButton confirmButton;
    private TextInputLayout nameInput, addressInput, phoneInput, noteInput;
    private TextInputEditText nameInputEdit, addressInputEdit, phoneInputEdit, noteInputEdit;
    private RecyclerView cartItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        confirmButton = findViewById(R.id.button_confirm);
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
        initRecyclerView();
    }

    private void initRecyclerView(){

        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(this, MyCoffeeApplication.getOrder());
        cartItemList.setAdapter(adapter);
        cartItemList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

}
