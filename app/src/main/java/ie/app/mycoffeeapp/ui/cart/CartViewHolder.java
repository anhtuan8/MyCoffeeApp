package ie.app.mycoffeeapp.ui.cart;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.mycoffeeapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView price, amount, itemName;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        price = itemView.findViewById(R.id.price);
        amount = itemView.findViewById(R.id.amount);
        itemName = itemView.findViewById(R.id.item_name);
    }

    public TextView getAmount() {
        return amount;
    }

    public TextView getItemName() {
        return itemName;
    }

    public TextView getPrice() {
        return price;
    }
}
