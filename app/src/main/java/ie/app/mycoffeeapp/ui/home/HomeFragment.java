package ie.app.mycoffeeapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import ie.app.mycoffeeapp.FirebaseAuthActivity;
import ie.app.mycoffeeapp.MainActivity;
import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Article;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;
    private HashMap<String, ArrayList<Article>> categorizedArticle;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.appbar_menu,menu);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel = new HomeViewModel();
        categorizedArticle = new HashMap<>();
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        final Toolbar toolbar = root.findViewById(R.id.appbar);
        MainActivity activityFromContext = (MainActivity) getContext();
        assert activityFromContext != null;
        MyCoffeeApplication.setupToolbar(toolbar,activityFromContext);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        updateToolbar(user, toolbar);

        homeViewModel.getCategorizedArticles().observe(getViewLifecycleOwner(), new Observer<HashMap<String, ArrayList<Article>>>() {
            @Override
            public void onChanged(HashMap<String, ArrayList<Article>> stringArrayListHashMap) {
                categorizedArticle = stringArrayListHashMap;
                initRecyclerView(root,categorizedArticle);
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

        LinearLayout parent = view.findViewById(R.id.menuContainer);

        for(String category: categorizedArticle.keySet()){
            Log.d(TAG, "initRecyclerView: " + category);
            TextView categoryName = new TextView(requireContext());
            categoryName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            categoryName.setText(category);
            categoryName.setTextAppearance(R.style.MenuTopic);
            categoryName.setPadding(24,24,16,12);
            parent.addView(categoryName);

            RecyclerView recyclerView = new RecyclerView(requireContext());
            ContextThemeWrapper themeContext = new ContextThemeWrapper(getContext(), R.style.myStyle);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            recyclerView.setPadding(0,24,0,24);

            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(), this, categorizedArticle.get(category));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

            parent.addView(recyclerView);
        }
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        getActivity().finish();
                        Toast.makeText(getContext(),"Signed out", Toast.LENGTH_SHORT).show();
                        getContext().startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().overridePendingTransition(0, 0);
                    }
                });

        // [END auth_fui_signout]
    }

    public void updateToolbar(FirebaseUser user, View root){
        AppCompatButton signInButton = root.findViewById(R.id.button_sign_in);
        AppCompatButton logOutButton = root.findViewById(R.id.button_log_out);
        CircleImageView profilePicture = root.findViewById(R.id.profile_image);
        AppCompatTextView profileName = root.findViewById(R.id.profile_name);

        //Chua dang nhap
        if(user == null) {
            logOutButton.setVisibility(View.GONE);

            profileName.setVisibility(View.GONE);

            profilePicture.setVisibility(View.VISIBLE);
            profilePicture.setImageResource(R.drawable.ic_person_grey_24dp);

            signInButton.setVisibility(View.VISIBLE);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to Sign In Activity
                    Intent intent = new Intent(getContext(), FirebaseAuthActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }
        //Da dang nhap
        else{
            signInButton.setVisibility(View.GONE);

            profileName.setVisibility(View.VISIBLE);
            profileName.setText(user.getDisplayName());

            profilePicture.setVisibility(View.VISIBLE);
            Glide.with(this).asBitmap().load(user.getPhotoUrl()).fitCenter().into(profilePicture);
            logOutButton.setVisibility(View.VISIBLE);
            logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sign out and reload activity
                    signOut();
                }
            });
        }
//        toolbar.findViewById(R.id.toolbar_container)
//        startActivity(new Intent(getContext(), MainActivity.class));
//        getActivity().overridePendingTransition(0, 0);
    }

}
