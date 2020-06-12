package ie.app.mycoffeeapp.ui.order.menu;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ie.app.mycoffeeapp.MainActivity;
import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Product;
import ie.app.mycoffeeapp.ui.order.OrderViewModel;

public class MenuFragment extends Fragment {
    private static final String TAG = "MenuFragment";

    public static final String MENU_TYPE = "type";
    private HashMap<String, ArrayList<Product>> categorizedItems = new HashMap<>();
    private MenuViewModel menuViewModel;
    private MenuItem searchItem;
    private SearchView searchView;
    private ArrayList<MenuRecyclerViewAdapter> adapters = new ArrayList<>();

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.appbar_menu,menu);

        searchItem = menu.findItem(R.id.appbarbutton_search);
        searchView = (SearchView) searchItem.getActionView();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View root = inflater.inflate(R.layout.fragment_order_menu, container, false);
        Bundle args = getArguments();
        assert args != null;
        menuViewModel = new MenuViewModel(args.getInt(MENU_TYPE));
        menuViewModel.getCategorizedMenu().observe(getViewLifecycleOwner(), new Observer<HashMap<String, ArrayList<Product>>>() {
            @Override
            public void onChanged(HashMap<String, ArrayList<Product>> stringArrayListHashMap) {
                categorizedItems = stringArrayListHashMap;
                initRecyclerView(root,categorizedItems);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.d(TAG, "onQueryTextChange: called");
                        for(MenuRecyclerViewAdapter adapter: adapters) {
                            adapter.getFilter().filter(newText);
                        }
                        return false;
                    }
                });
                Log.d("MenuViewModel", "onChanged: " + stringArrayListHashMap.values());
            }
        });
        return root;
    }

    public void initRecyclerView(View view, HashMap<String,ArrayList<Product>> categorizedMenu){
        LinearLayout parent = view.findViewById(R.id.menuContainer);

        for(String category: categorizedMenu.keySet()){
            TextView categoryName = new TextView(requireContext());
            categoryName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            categoryName.setText(category);
            categoryName.setTextAppearance(R.style.MenuTopic);
            categoryName.setPadding(24,24,16,12);
            parent.addView(categoryName);

            RecyclerView recyclerView = new RecyclerView(requireContext());

            final MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(getContext(),categorizedMenu.get(category),recyclerView,view);
            adapters.add(adapter);
            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
            recyclerView.setNestedScrollingEnabled(false);
            ContextThemeWrapper themeContext = new ContextThemeWrapper(getContext(), R.style.myStyle);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(themeContext, null);
            lp.height = (int) ((int) (getContext().getResources().getDimension(R.dimen.menu_item_height)
                    + getContext().getResources().getDimension(R.dimen.menu_item_margin)*2 )
                    * Math.ceil(adapter.getItemCount() / 2.0));
            recyclerView.setLayoutParams(lp);
            parent.addView(recyclerView);
        }
    }


}
