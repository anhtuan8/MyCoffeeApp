package ie.app.mycoffeeapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Article;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;

    private static final String TAG = "HomeFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new HomeViewModel(getContext());
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "onCreateView: started");

        recyclerView = root.findViewById(R.id.home_article_list);

        homeViewModel.getmArticlesTopic1().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                initRecyclerView(root);
            }
        });

//        homeViewModel.getmArticlesIsFavorite().observe(getViewLifecycleOwner(), new Observer<ArrayList<Boolean>>() {
//            @Override
//            public void onChanged(final ArrayList<Boolean> articleIsFavorites) {
//                for(int i=0 ; i<articleIsFavorites.size() ; i++){
//                    final int finalI = i;
//                    HomeViewHolder viewHolder = (HomeViewHolder)recyclerView.findViewHolderForLayoutPosition(finalI);
//                    assert viewHolder != null;
//                    final ImageButton favoriteButton = viewHolder.getFavoriteButton();
//                    //article at position i is favorite
//                    if(articleIsFavorites.get(finalI)){
//                        favoriteButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //Click to remove from favorite
//                                favoriteButton.setImageResource(R.drawable.ic_star_border_black_48dp);
//                                homeViewModel.setmArticlesFavorite(false,finalI);
//                                EasyArtistApplication myApp = (EasyArtistApplication) getActivity().getApplication();
//                                try {
//                                    myApp.removeFromFavoriteList(homeViewModel.getmArticles().getValue().get(finalI).getArticle_id());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                    //article is not favorite
//                    else {
//                        favoriteButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //Click to add to favorite
//                                favoriteButton.setImageResource(R.drawable.ic_star_black_48dp);
//                                homeViewModel.setmArticlesFavorite(true,finalI);
//                                EasyArtistApplication myApp = (EasyArtistApplication) getActivity().getApplication();
//                                try {
//                                    myApp.addFavoriteList(homeViewModel.getmArticles().getValue().get(finalI).getArticle_id());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });

        return root;
    }

    /**
     * Next version: implement an Article class, store in HomeViewModel and get Images, Titles, Descriptions from ViewModel
     * Next next version: popular and recommend RecyclerList
     * @param view
     */
    private void initArticleList(View view, ArrayList<Article> articles){
        Log.d(TAG, "initArticleList: started");
        initRecyclerView(view);
    }

    private void initRecyclerView(View view){
        Log.d(TAG, "initRecycleView: started");
        Log.v(TAG, getActivity().getPackageName());
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(),this, homeViewModel.getmArticlesTopic1().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }
}
