package ie.app.mycoffeeapp.ui.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import ie.app.mycoffeeapp.ui.order.menu.DemoMenuFragment;
import ie.app.mycoffeeapp.ui.order.menu.MenuFragment;

public class OrderAdapter extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public OrderAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: {
                Fragment fragment = new MenuFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.MENU_TYPE,1);
                fragment.setArguments(args);
                return fragment;
            }
            case 1: {
                Fragment fragment = new MenuFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.MENU_TYPE,0);
                fragment.setArguments(args);
                return fragment;
            }
            default:{
                Fragment fragment = new DemoMenuFragment();
                Bundle args = new Bundle();
                args.putInt(DemoMenuFragment.ARG_OBJECT, position+1);
                fragment.setArguments(args);
                return fragment;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
