package mx.example.ruben.stir.app.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;

/**
 * Created by Ruben on 5/6/15.
 */
public class ClubDetailsFragment extends android.support.v4.app.Fragment
{

    @InjectView(R.id.img_detail_club)
    SimpleDraweeView clubImage;
    @InjectView(R.id.clubName)
    TextView clubName;
    @InjectView(R.id.clubDescription)
    TextView clubDescription;


    public ClubDetailsFragment() {}



    public static ClubDetailsFragment getInstance(Bundle bundle)
    {
        ClubDetailsFragment mClubDetailsFragment = new ClubDetailsFragment();
        mClubDetailsFragment.setArguments(bundle);
        return mClubDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_club_details, container, false);

        ButterKnife.inject(this, rootView);
        initView();


        return rootView;
    }

    private void initView()
    {
        //clubImage.setImageURI(Uri.parse(getArguments().getString(Constants.CLUB_URL_IMAGE)));
        clubName.setText(getArguments().getString(Constants.CLUB_NAME));
        clubDescription.setText(getArguments().getString(Constants.CLUB_DESCRIPTION));
    }

}
