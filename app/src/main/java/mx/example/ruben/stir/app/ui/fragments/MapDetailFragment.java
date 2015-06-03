package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;


import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;

/**
 * Created by Ruben on 6/3/15.
 */
public class MapDetailFragment extends Fragment
{
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public Context CONTEXT;
    LatLng venueLocation;

    public MapDetailFragment(){}

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        CONTEXT = activity;
    }
    public static MapDetailFragment getInstance(Bundle bundle)
    {
        MapDetailFragment map = new MapDetailFragment();
        map.setArguments(bundle);
        return map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_club_detail,container,false);

        setUpMapIfNeeded();

        return rootView;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded()
    {
        if (mMap == null)
        {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_detail)).getMap();

            if(mMap != null)
            {
                mMap.clear();
                setUpPosition();
            }
        }
    }
    private void setUpPosition()
    {
        venueLocation = new LatLng(getArguments().getDouble("latitude"),getArguments().getDouble("longitude"));

        mMap.addMarker(new MarkerOptions().position(venueLocation).title(getArguments().getString(Constants.CLUB_NAME))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MoveCameraTo(venueLocation);
    }
    private void MoveCameraTo(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(position, 15)));
    }

}
