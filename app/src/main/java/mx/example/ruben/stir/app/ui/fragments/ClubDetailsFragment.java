package mx.example.ruben.stir.app.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
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
    ImageView linkFB;
    @InjectView(R.id.linkTwitter)
    ImageView linkTwitter;

    @InjectView(R.id.detaillPhone)
    ImageView venuePhone;
    @InjectView(R.id.mapButton)
    ImageView mapButton;
    @InjectView(R.id.relativeLayout)
    RelativeLayout detailRaiting;
    @InjectView(R.id.rating_star_1)
    ImageButton star1;
    @InjectView(R.id.rating_star_2)
    ImageButton star2;
    @InjectView(R.id.rating_star_3)
    ImageButton star3;
    @InjectView(R.id.rating_star_4)
    ImageButton star4;
    @InjectView(R.id.rating_star_5)
    ImageButton star5;


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

        doStringDetail(getArguments().getString(Constants.CLUB_NAME), venueName);
        doStringDetail(getArguments().getString(Constants.VENUE_LOCATION), venueLocation);
        doStringDetail(getArguments().getString(Constants.VENUE_HOURS), venueHours);
        doStringDetail(getArguments().getString(Constants.VENUE_COST), venueCost);

        doLinkButton(getArguments().getString(Constants.VENUE_LINK), linkSite);
        doLinkButton(getArguments().getString(Constants.VENUE_FB), linkFB);
        doLinkButton(getArguments().getString(Constants.VENUE_TWITTER), linkTwitter);

        doPhoneButton(getArguments().getString(Constants.VENUE_PHONE), venuePhone);
        doMapsbutton(getArguments().getDouble(Constants.VENUE_LAT), getArguments().getDouble(Constants.VENUE_LNG), mapButton);
        doRatingStars(getArguments().getDouble(Constants.VENUE_RATING));
        venueDetails.setText(Constants.EMPTY_STRING);

        //Y MAPS LOCATION ASI COMO ESTRELLAS
    }
    public void doRatingStars(Double rating)
    {
        int numStar = (int) Math.round(rating/2);
        Log.i("stars ", String.valueOf(numStar));
        ArrayList<ImageButton> allStars = new ArrayList<>();

        allStars.add(star1);
        allStars.add(star2);
        allStars.add(star3);
        allStars.add(star4);
        allStars.add(star5);
        Log.i("Lista ", String.valueOf(allStars.size()));

        for (int i = numStar; i < allStars.size(); i++) {
            allStars.get(i).setAlpha((float) .1);
        }

    }
    public void doStringDetail(String detail, TextView textField)
    {
        String venue_detail = Objects.equals(detail.trim(), "") ? Constants.EMPTY_STRING : detail;
        textField.setText(venue_detail);

    }
    public void doLinkButton(final String venue_string, ImageView image)
    {
        final String venue_link = Objects.equals(venue_string.trim(), "") ? Constants.EMPTY_STRING : venue_string;
        if (!Objects.equals(venue_link, Constants.EMPTY_STRING)){
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uLinkSite = Uri.parse(venue_link);
                    Intent goSite = new Intent(Intent.ACTION_VIEW, uLinkSite);
                    startActivity(goSite);
                }
            });
        } else image.setAlpha((float) .30);
    }
    public void doMapsbutton(final Double lat, final Double lng, ImageView image)
    {
        if (lat != null && lng != null)
        {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Go to " + lat.toString() + ", " + lng.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else image.setAlpha((float) .0);
    }
    public void doPhoneButton(final String venue_string, ImageView image)
    {
        final String venue_phone = Objects.equals(venue_string.trim(), "") ? Constants.EMPTY_STRING : venue_string;
        if (!Objects.equals(venue_phone, Constants.EMPTY_STRING)){
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri callNumber = Uri.parse("tel:" + venue_phone);
                    Log.i("Number ", callNumber.toString());
                    Intent goCall = new Intent(Intent.ACTION_CALL, callNumber);
                    startActivity(goCall);
                }
            });
        } else image.setAlpha((float) .0);

    }
}
