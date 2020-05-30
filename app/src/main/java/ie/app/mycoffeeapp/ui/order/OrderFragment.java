package ie.app.mycoffeeapp.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.ui.order.menu.DemoMenuFragment;

public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";

    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;
    private ViewPager2 viewPager;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.appbar_menu,menu);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        MyCoffeeApplication.setupToolbar((Toolbar)root.findViewById(R.id.appbar),(AppCompatActivity) requireContext());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        orderAdapter = new OrderAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(orderAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("Thức uống");
                        break;
                    }
                    case 1:{
                        tab.setText("Đồ ăn");
                        break;
                    }
                    default:{
                        tab.setText("OBJECT" + (position + 1));
                        break;
                    }
                }
            }
        }).attach();
    }
}
