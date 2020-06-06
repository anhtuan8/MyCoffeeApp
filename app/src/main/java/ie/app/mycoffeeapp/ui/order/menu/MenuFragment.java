package ie.app.mycoffeeapp.ui.order.menu;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ArrayList<Product> menuItems = new ArrayList<>();
    private HashMap<String, ArrayList<Product>> categorizedItems = new HashMap<>();
    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        orderViewModel =
//                ViewModelProviders.of(this).get(OrderViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_order_menu, container, false);
//                Log.v("Menu type", String.valueOf(args.getInt(MENU_TYPE)));
        Bundle args = getArguments();
        assert args != null;
        menuViewModel = new MenuViewModel(args.getInt(MENU_TYPE));
        menuViewModel.getCategorizedMenu().observe(getViewLifecycleOwner(), new Observer<HashMap<String, ArrayList<Product>>>() {
            @Override
            public void onChanged(HashMap<String, ArrayList<Product>> stringArrayListHashMap) {
                categorizedItems = stringArrayListHashMap;
                initRecyclerView(root,categorizedItems);
                Log.d("MenuViewModel", "onChanged: " + stringArrayListHashMap.values());
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
//        initRecyclerView(view,categorizedItems);
//        menuViewModel.getCategorizedMenu().observe(getViewLifecycleOwner(), new Observer<HashMap<String, ArrayList<Product>>>() {
//            @Override
//            public void onChanged(HashMap<String, ArrayList<Product>> stringArrayListHashMap) {
//                categorizedItems = stringArrayListHashMap;
//                initRecyclerView(view,categorizedItems);
//            }
//        });
    }

    public void initRecyclerView(View view, HashMap<String,ArrayList<Product>> categorizedMenu){
//        RecyclerView recyclerView = view.findViewById(R.id.order_menu);
        LinearLayout parent = view.findViewById(R.id.menuContainer);

        for(String category: categorizedMenu.keySet()){
            TextView categoryName = new TextView(requireContext());
            categoryName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            categoryName.setText(category);
            categoryName.setTextAppearance(R.style.MenuTopic);
            categoryName.setPadding(24,24,16,12);
            parent.addView(categoryName);

            RecyclerView recyclerView = new RecyclerView(requireContext());
            ContextThemeWrapper themeContext = new ContextThemeWrapper(getContext(), R.style.myStyle);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(themeContext, null);
            lp.height = (int) ((int) (getResources().getDimension(R.dimen.menu_item_height)
                                + getResources().getDimension(R.dimen.menu_item_margin)*2 )
                                * Math.ceil(categorizedMenu.get(category).size() / 2.0));
            recyclerView.setLayoutParams(lp);

//            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(getContext(),categorizedMenu.get(category));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
            recyclerView.setNestedScrollingEnabled(false);

            parent.addView(recyclerView);
        }
    }

}
