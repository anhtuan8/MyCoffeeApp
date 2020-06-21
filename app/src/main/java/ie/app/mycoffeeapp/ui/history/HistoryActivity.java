package ie.app.mycoffeeapp.ui.history;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Bill;

public class HistoryActivity extends AppCompatActivity {
    HistoryViewModel historyViewModel;
    RecyclerView billList;
    private static final String TAG = "HistoryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(this);
        historyViewModel = provider.get(HistoryViewModel.class);
        setContentView(R.layout.activity_history);
        billList = findViewById(R.id.bill_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        historyViewModel.getBills().observe(this, new Observer<ArrayList<Bill>>() {
            @Override
            public void onChanged(ArrayList<Bill> bills) {
                if(bills.size()>0) {
                    initRecyclerView(bills);
                }
                else{
                    setContentView(R.layout.activity_history_empty);
                    Toolbar toolbar = findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);
                    ActionBar actionBar = getSupportActionBar();
                    if(actionBar != null){
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
                    }
                }
            }
        });
//        initRecyclerView();
    }

    public void initRecyclerView(ArrayList<Bill> bills){
        Log.d(TAG, "initRecyclerView: started");
        HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(this,bills);
        billList.setAdapter(adapter);
        billList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
