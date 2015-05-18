package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import mx.example.ruben.stir.R;

public class FaceFragmont extends Fragment {

    Context CONTEXT;
    CallbackManager callbackManager;
    //    Button loginButton;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Profile infoProfile;

//    Ahora con login manager
//    Lo primero era arreglar el Git

    public FaceFragmont() {
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

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.i("LoginManager: ", "Login ok");
                        Toast.makeText(CONTEXT, "Ok login", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Log.i("LoginManager: ", "Login cancel");
                        Toast.makeText(CONTEXT, "Cancel login", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.i("LoginManager: ", "Login error");
                        Toast.makeText(CONTEXT, "Error login", Toast.LENGTH_SHORT).show();
                    }
                });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Log.i("AccessTokenTracker", "Haz algo");
            }
        };

//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(
//                    AccessToken oldAccessToken,
//                    AccessToken currentAccessToken) {
//                Log.i("Token: ", String.valueOf(currentAccessToken));
//            }
//        };

//        profileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(
//                    Profile oldProfile,
//                    Profile currentProfile) {
//                Log.i("Profile ", "Updated");
//                if (currentProfile != null) {
//                    infoProfile = Profile.getCurrentProfile();
//                    if (infoProfile != null) {
//                        String firstName = infoProfile.getFirstName();
//                        String lastName = infoProfile.getLastName();
//                        String fbId = infoProfile.getId();
//                        Uri fbImageProfile = infoProfile.getProfilePictureUri(64, 64);
//                        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("fb_user_prefs", CONTEXT.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("first_name", firstName);
//                        editor.putString("last_name", lastName);
//                        editor.putString("fb_id", fbId);
//                        editor.putString("img_profile", fbImageProfile.toString());
//                        editor.putBoolean("is_login", true);
//                        editor.apply();
//                        Toast.makeText(CONTEXT, "Bienvenido " + firstName, Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent("mx.example.ruben.stir.MAINACTIVITY");
//                        startActivity(i);
//                    } else {
//                        Toast.makeText(CONTEXT, "ERROR: No existe un perfil", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_face, container, false);

        Button loginButton = (Button) rootView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends"));
                Log.i("LOGIN ", "Clicked");
            }
        });

//        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList("public_profile, user_friends"));
//
//        loginButton.setFragment(this);
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", "Here");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

}
