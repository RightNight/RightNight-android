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
import android.support.v4.view.ViewPager;
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
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.adapters.FragmentViewPagerAdapter;

public class FaceFragment extends Fragment {

    Context CONTEXT;
    CallbackManager callbackManager;
    LoginButton loginButton;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    FacebookCallback<LoginResult> mFacebookCallback;
    Profile infoProfile;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentViewPagerAdapter pagerAdapter;


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

                        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences(Constants.SHARED_FB_PREFS, CONTEXT.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(Constants.FB_FIRST_NAME, firstName);
                        editor.putString(Constants.FB_LAST_NAME, lastName);
                        editor.putString(Constants.FB_ID, fbId);
                        editor.putString(Constants.FB_IMAGE_PROFILE, fbImageProfile.toString());
                        editor.putInt(Constants.SETTINGS_RADIO, 400);
                        editor.putBoolean(Constants.FB_LOGIN, true);
                        editor.apply();
                        Toast.makeText(CONTEXT, "Bienvenido " + firstName, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent("mx.example.ruben.stir.MAINACTIVITY");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(CONTEXT, "ERROR: No existe un perfil", Toast.LENGTH_SHORT).show();
                    }

                    Intent i = new Intent("mx.example.ruben.stir.MAINACTIVITY");
                    startActivity(i);
                }
            };
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_face, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.pager);

        fragments.add(FragmentOne.getInstance(savedInstanceState));
        fragments.add(FragmentTwo.getInstance(savedInstanceState));
        fragments.add(FragmentThree.getInstance(savedInstanceState));

        pagerAdapter = new FragmentViewPagerAdapter(getActivity().getSupportFragmentManager(), CONTEXT, fragments);
        viewPager.setAdapter(pagerAdapter);

        CirclePageIndicator circles = (CirclePageIndicator)rootView.findViewById(R.id.circles);
        circles.setViewPager(viewPager);

        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, user_friends"));

        loginButton.setFragment(this);

        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginButton.setCompoundDrawablePadding(0);
        loginButton.setPadding(24, 14, 24, 14);
        loginButton.setText("Log in with Facebook");

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
