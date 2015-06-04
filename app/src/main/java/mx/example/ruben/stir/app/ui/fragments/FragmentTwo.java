package mx.example.ruben.stir.app.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mx.example.ruben.stir.R;


/**
 * Created by hugo on 4/24/15.
 */
public class FragmentTwo extends Fragment {

    public FragmentTwo() {
    }

    public static FragmentTwo getInstance(Bundle bundle){
        FragmentTwo mFragmentTwo = new FragmentTwo();

        mFragmentTwo.setArguments(bundle);

        return mFragmentTwo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two_layout, container, false);
        TextView mtext = (TextView)rootView.findViewById(R.id.second_text);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
        mtext.setTypeface(font);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
