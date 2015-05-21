package mx.example.ruben.stir.app.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.ui.fragments.FaceFragment;
import mx.example.ruben.stir.app.ui.fragments.FaceFragmont;

public class FaceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FaceFragment())
                    .commit();
        }
    }

}