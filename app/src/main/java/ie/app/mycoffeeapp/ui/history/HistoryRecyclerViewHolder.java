package ie.app.mycoffeeapp.ui.history;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import ie.app.mycoffeeapp.R;

public class HistoryRecyclerViewHolder extends RecyclerView.ViewHolder {
    private MaterialTextView price, storeName, status, createdDate;

    public MaterialTextView getPrice() {
        return price;
    }

    public MaterialTextView getCreatedDate() {
        return createdDate;
    }

    public MaterialTextView getStatus() {
        return status;
    }

    public MaterialTextView getStoreName() {
        return storeName;
    }

    public HistoryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        price = itemView.findViewById(R.id.price);
        storeName = itemView.findViewById(R.id.store_name);
        status = itemView.findViewById(R.id.status);
        createdDate = itemView.findViewById(R.id.created_date);
    }
}
