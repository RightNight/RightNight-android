package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import mx.example.ruben.stir.R;

public class SettingsFragment extends Fragment {

    Context CONTEXT;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    TextView nameProfile;
    SimpleDraweeView imgProfile;
    LoginButton loginButton;

//    SUbe a git
//    Que subas te digo

    public SettingsFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        CONTEXT = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(CONTEXT.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = CONTEXT.getSharedPreferences("fb_user_prefs", CONTEXT.MODE_PRIVATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        nameProfile = (TextView) rootView.findViewById(R.id.txt_profile_name);
        String userName = sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "");
        nameProfile.setText(userName);
        imgProfile = (SimpleDraweeView) rootView.findViewById(R.id.img_profile);
        Uri profileImage = Uri.parse(sharedPreferences.getString("img_profile", ""));
        imgProfile.setImageURI(profileImage);

        loginButton = (LoginButton) rootView.findViewById(R.id.btn_close);
        loginButton.setReadPermissions(Arrays.asList("public_profile, user_friends"));

        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
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

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
