package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.drawee.view.SimpleDraweeView;

import mx.example.ruben.stir.R;

public class SettingsFragment extends Fragment {

    Context CONTEXT;
    LoginButton btnClose;

    public SettingsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        CONTEXT = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(CONTEXT.getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        btnClose = (LoginButton) rootView.findViewById(R.id.btn_close);
        btnClose.setFragment(this);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("fb_user_prefs", CONTEXT.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("first_name", "");
                editor.putString("last_name", "");
                editor.putString("fb_id", "");
                editor.putString("img_profile", "");
                editor.putBoolean("is_login", false);
                editor.apply();

                Intent i = new Intent("mx.example.ruben.stir.FACEACTIVITY");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        getProfile(rootView);

        return rootView;
    }

    private void getProfile(View v) {
        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("fb_user_prefs", CONTEXT.MODE_PRIVATE);

        TextView nameProfile = (TextView) v.findViewById(R.id.txt_profile_name);
        SimpleDraweeView imageProfile = (SimpleDraweeView) v.findViewById(R.id.img_profile);
        String fbName = sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "");
        Uri fbImageProfile = Uri.parse(sharedPreferences.getString("img_profile", ""));
        nameProfile.setText(fbName);
        imageProfile.setImageURI(fbImageProfile);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
