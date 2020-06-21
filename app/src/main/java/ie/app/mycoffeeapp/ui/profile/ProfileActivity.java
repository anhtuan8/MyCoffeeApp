package ie.app.mycoffeeapp.ui.profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import ie.app.mycoffeeapp.MyCoffeeApplication;
import ie.app.mycoffeeapp.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        CircleImageView profilePicture = findViewById(R.id.profile_image);
        AppCompatTextView profileName = findViewById(R.id.profile_name);
        TextView infoName = findViewById(R.id.name);
        TextView infoEmail = findViewById(R.id.email);
        TextView infoSomething = findViewById(R.id.something);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Glide.with(this).asBitmap().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).fitCenter().into(profilePicture);
            profileName.setText(user.getDisplayName());
            infoName.setText(user.getDisplayName());
            infoEmail.setText(user.getEmail());
            infoSomething.setText(user.getPhoneNumber());
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
