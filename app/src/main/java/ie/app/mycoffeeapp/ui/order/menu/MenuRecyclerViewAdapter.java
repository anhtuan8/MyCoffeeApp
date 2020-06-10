package ie.app.mycoffeeapp.ui.order.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Product;
import ie.app.mycoffeeapp.ui.profile.ProfileActivity;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuViewHolder> implements Filterable {
    private static final String TAG = "MenuRecyclerViewAdapter";

    private ArrayList<Product> menuItems = new ArrayList<>();
    private ArrayList<Product> menuItemsFull = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;

    public MenuRecyclerViewAdapter(Context context, ArrayList<Product> menuItems, RecyclerView recyclerView){
        this.context = context;
        this.menuItems = menuItems;
        menuItemsFull = new ArrayList<>(menuItems);
        this.recyclerView = recyclerView;
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

        if(MyCoffeeApplication.updateUser() != null) {
            holder.getItem().setOnClickListener(new View.OnClickListener() {
                @Override
                /**
                 * Call article list in corresponding topic title, or article activity with corresponding article title
                 */
                public void onClick(View v) {
                    addToOrder(menuItems.get(position));
                    //Add size + amount picker later
                }
            });
            holder.getAddButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToOrder(menuItems.get(position));
                }
            });
        }
        else {
            holder.getItem().setOnClickListener(new View.OnClickListener() {
                @Override
                /**
                 * Call article list in corresponding topic title, or article activity with corresponding article title
                 */
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Clicked " + menuItems.get(position).getName());
                    Toast.makeText(context,"Đăng nhập để đặt hàng", Toast.LENGTH_SHORT).show();
                }
            });
            holder.getAddButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Adding " + menuItems.get(position).getName());
                    Toast.makeText(context,"Đăng nhập để đặt hàng", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    @Override
    public Filter getFilter() {
        return menuFilter;
    }

    private Filter menuFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filterdList = new ArrayList<>();
            if(constraint == null || constraint.length()==0 ){
                //empty search field
                filterdList.addAll(menuItemsFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Product product: menuItemsFull){
                    if(product.getName().toLowerCase().contains(filterPattern)){
                        filterdList.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterdList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            menuItems.clear();
            menuItems.addAll((ArrayList<Product>)results.values);
            notifyDataSetChanged();
            ContextThemeWrapper themeContext = new ContextThemeWrapper(context, R.style.myStyle);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(themeContext, null);
            lp.height = (int) ((int) (context.getResources().getDimension(R.dimen.menu_item_height)
                    + context.getResources().getDimension(R.dimen.menu_item_margin)*2 )
                    * Math.ceil(menuItems.size() / 2.0));
            recyclerView.setLayoutParams(lp);
        }
    };

    private void addToOrder(Product product){
        Toast.makeText(context,"Đã thêm " + product.getName() +" vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }
}
