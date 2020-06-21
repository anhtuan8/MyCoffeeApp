package ie.app.mycoffeeapp.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import ie.app.mycoffeeapp.MainActivity;
import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.ui.history.HistoryActivity;
import ie.app.mycoffeeapp.ui.profile.ProfileActivity;

public class PersonalFragment extends Fragment {

    private PersonalViewModel personalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalViewModel =
                ViewModelProviders.of(this).get(PersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MaterialButton profileButton = root.findViewById(R.id.button_info);
        MaterialButton historyButton = root.findViewById(R.id.button_history);
        MaterialButton signOutButton = root.findViewById(R.id.button_sign_out);
        CardView profileContainer = root.findViewById(R.id.profile_container);

        TextView profileName = root.findViewById(R.id.profile_name);
        CircleImageView profileImage = root.findViewById(R.id.profile_image);
        if(user != null) {
            Glide.with(getContext()).asBitmap().load(user.getPhotoUrl()).fitCenter().into(profileImage);

            profileName.setText(user.getDisplayName());

            profileContainer.setOnClickListener(viewPersonalInfo);
            profileButton.setOnClickListener(viewPersonalInfo);
            signOutButton.setOnClickListener(signOut);
            historyButton.setOnClickListener(transactionHistory);
        }
        else{
            Glide.with(getContext()).asBitmap().load(R.drawable.ic_error_black_24dp).fitCenter().into(profileImage);
            profileName.setText("Chưa đăng nhập");
            profileContainer.setOnClickListener(noUser);
            profileButton.setOnClickListener(noUser);
            signOutButton.setOnClickListener(noUser);
        }
        return root;
    }

    private View.OnClickListener viewPersonalInfo = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            getActivity().startActivity(intent);
        }
    };

    private View.OnClickListener signOut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };

    private View.OnClickListener noUser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener transactionHistory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            getActivity().startActivity(intent);
        }
    };

}
