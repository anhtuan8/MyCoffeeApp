package ie.app.mycoffeeapp.ui.order.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Product;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private static final String TAG = "MenuRecyclerViewAdapter";

    private ArrayList<Product> menuItems = new ArrayList<>();
    Context context;

    public MenuRecyclerViewAdapter(Context context, ArrayList<Product> menuItems){
        this.context = context;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_menu_item,parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder called");
        //load image to position
        Glide.with(context).asBitmap().load(menuItems.get(position).getImage()).into(holder.getImage());
        //bind topic title and description
        holder.getTitle().setText(menuItems.get(position).getName());
        holder.getPrice().setText(menuItems.get(position).getPrice());
        holder.getItem().setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Call article list in corresponding topic title, or article activity with corresponding article title
             */
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked " + menuItems.get(position).getName());
                Toast.makeText(context, menuItems.get(position).getName(), Toast.LENGTH_SHORT).show();
                //Add size + amount picker later
            }
        });
        holder.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Adding " + menuItems.get(position).getName());
                Toast.makeText(context,"Adding" + menuItems.get(position).getName() +"to order", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

}
