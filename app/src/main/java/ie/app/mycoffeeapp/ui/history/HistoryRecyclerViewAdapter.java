package ie.app.mycoffeeapp.ui.history;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Bill;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewHolder> {
    private static final String TAG = "HistoryRecyclerViewAdap";
    private ArrayList<Bill> bills = new ArrayList<>();
    private Context context;

    public HistoryRecyclerViewAdapter(Context context, ArrayList<Bill> bills){
        this.context = context;
        this.bills = bills;
    }
    @NonNull
    @Override
    public HistoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_item,parent,false);
        HistoryRecyclerViewHolder holder = new HistoryRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.getCreatedDate().setText(bill.getCreatedDate());
        holder.getPrice().setText(String.valueOf(bill.getPriceString()));
        holder.getStoreName().setText(bill.getStoreName());
        switch (bill.getStatusCode()){
            case 0:{
                holder.getStatus().setText(R.string.status_cancel);
                holder.getStatus().setTextAppearance(R.style.StatusCancel);
                break;
            }
            case 1:{
                holder.getStatus().setText(R.string.status_processing);
                holder.getStatus().setTextAppearance(R.style.StatusProcessing);
                break;
            }
            case 2:{
                holder.getStatus().setTextAppearance(R.style.StatusComplete);
                holder.getStatus().setText(R.string.status_complete);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }
}
