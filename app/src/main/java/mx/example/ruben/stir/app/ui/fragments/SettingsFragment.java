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
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.drawee.view.SimpleDraweeView;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;

public class SettingsFragment extends Fragment {

    Context CONTEXT;
    LoginButton btnClose;
    TextView nameProfile;
    SimpleDraweeView imageProfile;
    EditText inputRadio;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
        sharedPreferences = CONTEXT.getSharedPreferences(Constants.SHARED_FB_PREFS, CONTEXT.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        btnClose = (LoginButton) rootView.findViewById(R.id.btn_close);
        nameProfile = (TextView) rootView.findViewById(R.id.txt_profile_name);
        imageProfile = (SimpleDraweeView) rootView.findViewById(R.id.img_profile);
        inputRadio = (EditText) rootView.findViewById(R.id.txt_input_radio);
        editor = sharedPreferences.edit();

        btnClose.setFragment(this);
        btnClose.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        btnClose.setCompoundDrawablePadding(0);
        btnClose.setPadding(20, 14, 20, 14);
        btnClose.setText("CERRAR SESIÓN");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                editor.putString(Constants.FB_FIRST_NAME, "");
                editor.putString(Constants.FB_LAST_NAME, "");
                editor.putString(Constants.FB_ID, "");
                editor.putString(Constants.FB_IMAGE_PROFILE, "");
                editor.putBoolean(Constants.FB_LOGIN, false);
                editor.apply();
                Intent i = new Intent("mx.example.ruben.stir.FACEACTIVITY");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        getProfile();

        return rootView;
    }

    private void getProfile() {
        String fbName = sharedPreferences.getString(Constants.FB_FIRST_NAME, "") + " " + sharedPreferences.getString(Constants.FB_LAST_NAME, "");
        Uri fbImageProfile = Uri.parse(sharedPreferences.getString(Constants.FB_IMAGE_PROFILE, ""));
        int radio = sharedPreferences.getInt(Constants.SETTINGS_RADIO, 400);

        nameProfile.setText(fbName);
        imageProfile.setImageURI(fbImageProfile);
        inputRadio.setText(String.valueOf(radio));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        int radioValue = Integer.parseInt(inputRadio.getText().toString());
        editor.putInt(Constants.SETTINGS_RADIO, radioValue);
        editor.apply();
    }
}