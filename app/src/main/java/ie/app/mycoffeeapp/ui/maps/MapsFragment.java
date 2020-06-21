package ie.app.mycoffeeapp.ui.maps;

import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

import ie.app.mycoffeeapp.MainActivity;
import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Store;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapsViewModel mapsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps,container,false);
        ViewModelProvider provider = new ViewModelProvider(this);
        mapsViewModel = provider.get(MapsViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mapsViewModel.getStores().observe(getViewLifecycleOwner(), new Observer<ArrayList<Store>>() {
            @Override
            public void onChanged(ArrayList<Store> stores) {
                LatLngBounds HADONG = new LatLngBounds(new LatLng(20.954190, 105.744603), new LatLng(20.988504, 105.797901));
                for(Store store: stores){
                    // Add a marker in Sydney and move the camera
                    LatLng storeLocation = new LatLng(store.getLat(), store.getLon());
                    mMap.addMarker(new MarkerOptions().position(storeLocation).title(store.getStoreName()));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(HADONG,0));
            }
        });
    }

}
