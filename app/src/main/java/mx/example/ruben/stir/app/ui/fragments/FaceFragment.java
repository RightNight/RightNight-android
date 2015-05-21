package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import mx.example.ruben.stir.R;

public class FaceFragment extends Fragment {

    Context CONTEXT;
    CallbackManager callbackManager;
    LoginButton loginButton;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    FacebookCallback<LoginResult> mFacebookCallback;
    Profile infoProfile;

//    Estoy en git y funciono


    public FaceFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        CONTEXT = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(CONTEXT.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Log.i("Token: ", String.valueOf(currentAccessToken));
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                if (currentProfile != null) {
                    infoProfile = Profile.getCurrentProfile();
                    if (infoProfile != null) {
                        String firstName = infoProfile.getFirstName();
                        String lastName = infoProfile.getLastName();
                        String fbId = infoProfile.getId();
                        Uri fbImageProfile = infoProfile.getProfilePictureUri(200, 200);
                        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("fb_user_prefs", CONTEXT.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("first_name", firstName);
                        editor.putString("last_name", lastName);
                        editor.putString("fb_id", fbId);
                        editor.putString("img_profile", fbImageProfile.toString());
                        editor.putInt("ratio_map", 400);
                        editor.putBoolean("is_login", true);
                        editor.apply();
                        Toast.makeText(CONTEXT, "Bienvenido " + firstName, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent("mx.example.ruben.stir.MAINACTIVITY");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(CONTEXT, "ERROR: No existe un perfil", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_face, container, false);

        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, user_friends"));

        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                loginButton.setAlpha(0);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

}
