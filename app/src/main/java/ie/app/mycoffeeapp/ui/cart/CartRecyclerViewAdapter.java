package ie.app.mycoffeeapp.ui.cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Order;
import ie.app.mycoffeeapp.model.Product;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartViewHolder>{
    private static final String TAG = "HomeRecyclerViewAdapter";

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Integer> amounts = new ArrayList<>();
    Context context;

    public CartRecyclerViewAdapter(Context context, Order order){
        this.context = context;
        for(Product product: order.getOrder().keySet()){
            products.add(product);
            amounts.add(order.getOrder().get(product));
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cartitem,parent,false);
        CartViewHolder holder = new CartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        holder.getPrice().setText(product.getPrice());
        holder.getAmount().setText(amounts.get(position).toString());
        holder.getItemName().setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
