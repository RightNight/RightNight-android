package mx.example.ruben.stir.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.example.ruben.stir.R;


/**
 * Created by hugo on 4/24/15.
 */
public class FragmentThree extends Fragment {


    public FragmentThree() {
    }

    public static FragmentThree getInstance(Bundle bundle){
        FragmentThree mFragmentThree = new FragmentThree();

        mFragmentThree.setArguments(bundle);

        return mFragmentThree;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_three_layout, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
