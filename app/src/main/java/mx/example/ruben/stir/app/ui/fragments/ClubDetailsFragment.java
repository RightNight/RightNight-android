package mx.example.ruben.stir.app.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;

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
//WE HAVE A BUNDLE WITH THE NAME AND LOCATION
//NOW WE NEED TO START A FRAGMENT OF MAP WITH THIS INFORMATION

    Bundle mapBundle;

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

    @InjectView(R.id.rating_star)
    RatingBar ratingStar;

    public ClubDetailsFragment() {}

    public static ClubDetailsFragment getInstance(Bundle bundle)
    {
        ClubDetailsFragment mClubDetailsFragment = new ClubDetailsFragment();
        mClubDetailsFragment.setArguments(bundle);
        return mClubDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_club_details, container, false);

        ButterKnife.inject(this, rootView);
        initView();

        ratingStar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        ratingStar.setFocusable(false);

        Drawable progress = ratingStar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.WHITE);

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

        doRatingStars(getArguments().getDouble(Constants.VENUE_RATING));

        venueDetails.setText(Constants.EMPTY_STRING);
    }
    public void doRatingStars(Double rating)
    {
        float num_stars = new Float(rating) / 2;
        Log.i("Stars ", String.valueOf(num_stars));
        ratingStar.setRating(num_stars);
    }
    public void doStringDetail(String detail, TextView textField)
    {
        if(detail != null){

            Log.i("Deatil ", detail);
            String venue_detail = Objects.equals(detail.trim(), "") ? Constants.EMPTY_STRING : detail;
            textField.setText(venue_detail);
        } else {
            textField.setText(Constants.EMPTY_STRING);
        }

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
