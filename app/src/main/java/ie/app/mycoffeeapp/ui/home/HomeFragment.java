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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import ie.app.mycoffeeapp.MainActivity;
import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Article;
import ie.app.mycoffeeapp.ui.order.menu.MenuRecyclerViewAdapter;

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
    private void initRecyclerView(View view, HashMap<String, ArrayList<Article>> categorizedArticle){
        Log.d(TAG, "initRecycleView: started");
//        Log.v(TAG, getActivity().getPackageName());
//        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(),this, homeViewModel.getmArticlesTopic1().getValue());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        LinearLayout parent = view.findViewById(R.id.menuContainer);

        for(String category: categorizedArticle.keySet()){
            TextView categoryName = new TextView(requireContext());
            categoryName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            categoryName.setText(category);
            categoryName.setTextAppearance(R.style.Description);
            categoryName.setPadding(16,12,16,12);
            parent.addView(categoryName);

            RecyclerView recyclerView = new RecyclerView(requireContext());
            ContextThemeWrapper themeContext = new ContextThemeWrapper(getContext(), R.style.myStyle);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(themeContext, null);
//            lp.height = (int) ((int) (getResources().getDimension(R.dimen.menu_item_height)
//                    + getResources().getDimension(R.dimen.menu_item_margin)*2 )
//                    * Math.ceil(categorizedArticle.get(category).size() / 2.0));
//            recyclerView.setLayoutParams(lp);

            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(), this, categorizedArticle.get(category));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

            parent.addView(recyclerView);
        }
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }
}
