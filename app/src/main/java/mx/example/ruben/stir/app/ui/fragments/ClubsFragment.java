package mx.example.ruben.stir.app.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
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
import mx.example.ruben.stir.app.DistanceHelper;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.RightNightApplication;
import mx.example.ruben.stir.app.model.Items;
import mx.example.ruben.stir.app.model.Venue;
import mx.example.ruben.stir.app.ui.adapters.ClubsListAdapter;
import mx.example.ruben.stir.app.ui.interfaces.EndlessRecyclerOnScrollListener;


public class ClubsFragment extends Fragment
    {//Implementar progress bar

        private static final String LOG_TAG = ClubsFragment.class.getCanonicalName();

        public Context CONTEXT;
        Bundle mBundle;
        LatLng userLocation;

        @InjectView(R.id.list_clubs)
        RecyclerView mListClubs;

        ClubsListAdapter adapter;

        public ClubsFragment()
        {}

        public static ClubsFragment getInstance(Bundle bundle)
        {
            ClubsFragment club = new ClubsFragment();
            club.setArguments(bundle);
            return club;
        }

        @Override
        public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            CONTEXT = activity;
            adapter = new ClubsListAdapter(CONTEXT);
            mBundle = getArguments();
            userLocation = new LatLng(mBundle.getDouble("latitude"),mBundle.getDouble("longitude"));
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_directory, container, false);
            ButterKnife.inject(this, rootView);
            initListClubs();
            return rootView;
        }

        @Override
        public void onResume()
        {
            super.onResume();
            requestMoreNearbyClubs(0);
        }

        private void initListClubs()
        {
            LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mListClubs.setLayoutManager(lm);
            mListClubs.setAdapter(adapter);
            mListClubs.setOnScrollListener(new EndlessRecyclerOnScrollListener(lm)
            {
                @Override
                public void onLoadMore(int current_page)
                {
                    requestMoreNearbyClubs(adapter.getVenuesItemsCount());
                }
            });

        }
        private void requestMoreNearbyClubs(int offset)
        {
            adapter.showOnLoadViewHolder();

            final String uri = (Constants.API_URL_VENUES
                                +Constants.EXPLORE
                                +Constants.API_OB_PARAMS
                                +Constants.NIGHTLIFE_FILTER_PARAM
                                +Constants.VENUE_PHOTOS
                                +Constants.SORT_BY_DISTANCE
                                +Constants.LIMIT_PARAM+"20"
                                +Constants.OFFSET_PARAM+String.valueOf(offset)
                                +Constants.LOCATION_PARAM
                                +String.valueOf(userLocation.latitude) +","
                                +String.valueOf(userLocation.longitude));

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
                            List<Venue> venues = new ArrayList<>();

                            for (int i = 0; i < itemList.size(); i++)
                            {
                                Venue current = itemList.get(i).getVenue();
                                current.setDistance(new DistanceHelper().getDistanceBetween(userLocation.latitude, userLocation.longitude, current.getLocation().getLat(), current.getLocation().getLng()));
                                venues.add(current);
                            }
                            adapter.RemoveProgressView();
                            adapter.updateList(venues);
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    VolleyLog.e("Error: ", error.getMessage());
                    adapter.RemoveProgressView();
                }
            });
            RightNightApplication.getInstance().addToRequestQueue(request);
        }
}