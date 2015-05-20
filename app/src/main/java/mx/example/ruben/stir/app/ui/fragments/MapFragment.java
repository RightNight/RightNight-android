package mx.example.ruben.stir.app.ui.fragments;

import android.location.Location;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.RightNightApplication;
import mx.example.ruben.stir.app.model.Items;
import mx.example.ruben.stir.app.model.Venue;

public class MapFragment extends Fragment
{
    @InjectView(R.id.moreButton)
    Button mMoreVenuesButton;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public Context CONTEXT;

    int radius = 400;
    int offset = 0;
    LatLng myPosition;
    boolean mRequestDone = true;

    private Bundle mBundle;
    List<Venue> mVenues;

    public MapFragment(){}

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        CONTEXT = activity;
    }
    public static MapFragment getInstance(Bundle bundle)
    {
        MapFragment map = new MapFragment();
        map.setArguments(bundle);
        return map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        mVenues = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.inject(this, rootView);

        setUpMapIfNeeded();
        VenueMarkers();
        mMap.setMyLocationEnabled(true);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
        {
            @Override
            public void onCameraChange(CameraPosition cameraPosition)
            {
                radius = 400;
                offset = 0;
            }
        });

        mMoreVenuesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mRequestDone)
                {
                    if (radius == 400)
                    {
                        mMap.clear();
                        mVenues.clear();
                    }

                    mMap.addCircle(new CircleOptions().center(mMap.getCameraPosition().target).radius(radius));

                    requestClubs(String.valueOf(mMap.getCameraPosition().target.latitude),
                            String.valueOf(mMap.getCameraPosition().target.longitude));

                    radius = radius + 400;
                }
                else
                {
                    Toast.makeText(CONTEXT,"We are getting you more venues",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            setUpPosition();
        }
    }

    private void setUpPosition()
    {
        myPosition = new LatLng(mBundle.getDouble("latitude"),mBundle.getDouble("longitude"));
        MoveCameraTo(myPosition);
    }
    private void MoveCameraTo(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(position, 15)));
    }

    public void addMarker(double latitude, double longitude,String name)
    {
        LatLng newMarkLatLng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(newMarkLatLng).
                title(name));
    }

    private void requestClubs(String lat, String lng)
    {
        mRequestDone = false;

        final String uri = (
                            Constants.API_URL_VENUES
                            +Constants.EXPLORE
                            +Constants.API_OB_PARAMS
                            +Constants.NIGHTLIFE_FILTER_PARAM
                            +Constants.VENUE_PHOTOS
                            +Constants.SORT_BY_DISTANCE
                            +Constants.LIMIT_PARAM+"50"
                            +Constants.LOCATION_PARAM+lat+","+lng
                            +Constants.RADIUS_PARAM+String.valueOf(radius)
                            +Constants.OFFSET_PARAM+String.valueOf(offset));

        JsonObjectRequest request = new JsonObjectRequest(uri, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.d("URL", uri.toString());

                try
                {
                    List<Items> itemList;
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    int returnCode = response.getJSONObject("meta").getInt("code");
                    if (returnCode == 200)
                    {
                        JSONArray items = response.getJSONObject("response").getJSONArray("groups").
                                getJSONObject(0).getJSONArray("items");
                        Gson gson = new Gson();
                        Type tipoListaItems = new TypeToken<List<Items>>(){}.getType();
                        itemList = gson.fromJson(items.toString(), tipoListaItems);

                        for (int i = 0; i < itemList.size(); i++)
                        {
                            mVenues.add(itemList.get(i).getVenue());
                        }

                        VenueMarkers();
                        offset = mVenues.size();
                        mRequestDone = true;
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    mRequestDone = true;
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(CONTEXT,"Volley error",Toast.LENGTH_LONG).show();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        Toast.makeText(CONTEXT,"Loading",Toast.LENGTH_SHORT).show();
        RightNightApplication.getInstance().addToRequestQueue(request);
    }

    public void VenueMarkers()
    {
        if (mVenues!=null)
        {
            for (int i = offset; i < mVenues.size(); ++i)
            {
                Venue current = mVenues.get(i);
                //if (current.isVerified())
                addMarker(current.getLocation().getLat(), current.getLocation().getLng(), current.getName() + " " +
                        String.valueOf(current.getHereNow())+" "+current.getCategories().getName());
            }
        }
    }
}
