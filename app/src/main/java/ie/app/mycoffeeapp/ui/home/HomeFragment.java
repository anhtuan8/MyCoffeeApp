package ie.app.mycoffeeapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.mycoffeeapp.MainActivity;
import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Article;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.appbar_menu,menu);
    }

    private static final String TAG = "HomeFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel = new HomeViewModel(getContext());
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "onCreateView: started");

        recyclerView = root.findViewById(R.id.home_article_list);
        Toolbar toolbar = root.findViewById(R.id.appbar);
        MainActivity activityFromContext = (MainActivity) getContext();
        assert activityFromContext != null;
        MyCoffeeApplication.setupToolbar(toolbar,activityFromContext);
//        activityFromContext.setupToolbar(toolbar);

        homeViewModel.getmArticlesTopic1().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                initRecyclerView(root);
            }
        });

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
