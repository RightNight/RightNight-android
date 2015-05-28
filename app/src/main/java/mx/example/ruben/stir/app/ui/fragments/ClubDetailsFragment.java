package mx.example.ruben.stir.app.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Objects;

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
    TextView venueName;

    @InjectView(R.id.txt_location)
    TextView venueLocation;
    @InjectView(R.id.detaillSchedule)
    TextView venueHours;
    @InjectView(R.id.detaillCost)
    TextView venueCost;
    @InjectView(R.id.detaillDescription)
    TextView venueDetails;
    @InjectView(R.id.linkSite)
    ImageView linkSite;
    @InjectView(R.id.linkFB)
    ImageView linkGB;
    @InjectView(R.id.linkTwitter)
    ImageView linkTwitter;

    String mVenueHours;
    String mVenueSite;
    String mVenueLocation;
    String mVenueCost;
    String mVenueFacebook;
    String mVenueTwitter;


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

        clubImage.setImageURI(Uri.parse(getArguments().getString(Constants.CLUB_URL_IMAGE)));
        venueName.setText(getArguments().getString(Constants.CLUB_NAME));


        Log.i("OK ", "go link " + getArguments().getString(Constants.VENUE_LINK).trim());
        mVenueHours = Objects.equals(getArguments().getString(Constants.VENUE_HOURS).trim(), "") ? Constants.EMPTY_STRING : getArguments().getString(Constants.VENUE_HOURS);
        mVenueLocation = Objects.equals(getArguments().getString(Constants.VENUE_LOCATION).trim(), "") ? Constants.EMPTY_STRING : getArguments().getString(Constants.VENUE_LOCATION);
        mVenueCost = Objects.equals(getArguments().getString(Constants.VENUE_COST).trim(), "") ? Constants.EMPTY_STRING : getArguments().getString(Constants.VENUE_COST);
        mVenueSite = Objects.equals(getArguments().getString(Constants.VENUE_LINK).trim(), "") ? Constants.EMPTY_STRING : getArguments().getString(Constants.VENUE_LINK);
        mVenueFacebook = Objects.equals(getArguments().getString(Constants.VENUE_FB).trim(), "") ? Constants.EMPTY_STRING : getArguments().getString(Constants.VENUE_FB);
        mVenueTwitter = Objects.equals(getArguments().getString(Constants.VENUE_TWITTER).trim(), "") ? Constants.EMPTY_STRING : getArguments().getString(Constants.VENUE_TWITTER);

        venueHours.setText(mVenueHours);
        venueLocation.setText(mVenueLocation);
        venueCost.setText(mVenueCost);
        venueHours.setText(mVenueHours);

        Log.i("OK ", "go link " + mVenueSite);
        if (!Objects.equals(mVenueSite, Constants.EMPTY_STRING)){
            linkSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Uri uLinkSite = Uri.parse(mVenueSite);
                        Intent goSite = new Intent(Intent.ACTION_VIEW, uLinkSite);
                        startActivity(goSite);
                }
            });
        } else {
            linkSite.setAlpha((float) .50);
        }

//        venueLocation.setText(getArguments().getString(Constants.VENUE_LOCATION));
//        venueCost.setText(getArguments().getString(Constants.VENUE_COST));

        venueDetails.setText("Description");

//        venueLinks.setText(getArguments().getString(Constants.VENUE_LINK)+"\n"+
//                           getArguments().getString(Constants.VENUE_TWITTER)+"\n"+
//                           getArguments().getString(Constants.VENUE_FB));
        //FALTA PHONE Y MAPS LOCATION ASI COMO ESTRELLAS
    }
}
