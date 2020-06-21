package ie.app.mycoffeeapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.ui.article.ArticleActivity;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    private static final String TAG = "HomeRecyclerViewAdapter";

    private ArrayList<Article> articles = new ArrayList<>();
    HomeFragment fragment;
    Context context;

    public HomeRecyclerViewAdapter(Context context, HomeFragment fragment, ArrayList<Article> articles){
        this.context = context;
        this.articles = articles;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listitem,parent,false);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder called");
        //load image to position
        Glide.with(context).asBitmap().load(articles.get(position).getImage_link()).into(holder.getImage());
        //bind topic title and description
        holder.getTitle().setText(articles.get(position).getName());
        holder.getDescription().setText(articles.get(position).getDescription());
        holder.getItem().setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Call article list in corresponding topic title, or article activity with corresponding article title
             */
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked " + articles.get(position).getName());
                Toast.makeText(context, articles.get(position).getName(), Toast.LENGTH_SHORT).show();
                //call HomeArticleFragment
                Log.d(TAG, "calling Article Activity");
                Activity activityFromContext = (Activity) context;
                Intent intent = new Intent(activityFromContext, ArticleActivity.class);
                Bundle articleBundle = new Bundle();
                articleBundle.putString("articleId", articles.get(position).getArticle_id());
                intent.putExtras(articleBundle);
                activityFromContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}
