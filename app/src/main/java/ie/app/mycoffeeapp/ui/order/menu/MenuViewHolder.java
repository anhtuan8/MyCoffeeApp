package ie.app.mycoffeeapp.ui.order.menu;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.mycoffeeapp.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private ConstraintLayout item;
    private ImageView image;
    private TextView title, price;
    private ImageButton addButton;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        item = itemView.findViewById(R.id.item);
        image = itemView.findViewById(R.id.menuItemImage);
        title = itemView.findViewById(R.id.menuItemName);
        price = itemView.findViewById(R.id.menuItemPrice);
        addButton = itemView.findViewById(R.id.menuItemAddButton);
    }

    public CardView getCardView() {
        return cardView;
    }

    public ConstraintLayout getItem() {
        return item;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getPrice() {
        return price;
    }

    public ImageButton getAddButton() {
        return addButton;
    }

    public TextView getTitle() {
        return title;
    }

}
