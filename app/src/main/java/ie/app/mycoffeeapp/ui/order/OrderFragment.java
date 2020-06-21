package ie.app.mycoffeeapp.ui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Order;
import ie.app.mycoffeeapp.ui.cart.CartActivity;
import ie.app.mycoffeeapp.ui.order.menu.DemoMenuFragment;

public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";

    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;
    private ViewPager2 viewPager;
    private TextView price, amount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        MyCoffeeApplication.setOrderViewModel(orderViewModel);
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

        final View cartViewButton = view.findViewById(R.id.cart_view_button);

        price = view.findViewById(R.id.price);
        amount = view.findViewById(R.id.amount);
        cartViewButton.findViewById(R.id.button_view_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CartActivity
                Intent intent = new Intent(getContext(), CartActivity.class);
                requireContext().startActivity(intent);
            }
        });
        orderViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onChanged(Order order) {
                if(order.getAmount() > 0) {
                    cartViewButton.setVisibility(View.VISIBLE);
                    price.setText(order.getPriceString());
                    amount.setText(String.format("%d", MyCoffeeApplication.getOrder().getAmount()));
                }
                else {
                    cartViewButton.setVisibility(View.GONE);
                }
            }
        });
    }

    public void changeOrder(Order order){
        orderViewModel.setOrder(order);
    }
}
