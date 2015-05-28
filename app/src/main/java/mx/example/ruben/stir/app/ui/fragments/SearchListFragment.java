package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import mx.example.ruben.stir.app.RightNightApplication;
import mx.example.ruben.stir.app.model.Items;
import mx.example.ruben.stir.app.model.Venue;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.adapters.ClubsListAdapter;
import mx.example.ruben.stir.app.ui.interfaces.EndlessRecyclerOnScrollListener;

/**
 * Created by Ruben on 5/27/15.
 */
public class SearchListFragment extends android.support.v4.app.Fragment
{
    @InjectView(R.id.list_clubs)
    RecyclerView mSearchListClubs;

    Context CONTEXT;
    ClubsListAdapter adapter;
    String query = " ";

    public SearchListFragment() {}

    public static SearchListFragment getInstance(Bundle bundle)
    {
        SearchListFragment mSearchListFragment = new SearchListFragment();
        mSearchListFragment.setArguments(bundle);
        return mSearchListFragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        CONTEXT = activity;
        adapter = new ClubsListAdapter(CONTEXT);
        query = getArguments().getString(Constants.QUERY_SEARCH); //Obtiene lo del bundle
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
        mSearchListClubs.setLayoutManager(lm);
        mSearchListClubs.setAdapter(adapter);
        mSearchListClubs.setOnScrollListener(new EndlessRecyclerOnScrollListener(lm) {
            @Override
            public void onLoadMore(int current_page) {
                requestMoreNearbyClubs(adapter.getVenuesItemsCount());
            }
        });
    }

    private void requestMoreNearbyClubs(int offset)
    {
        adapter.showOnLoadViewHolder();

        final String uri = (Constants.API_URL_VENUES+
                            Constants.SEARCH+
                            Constants.API_OB_PARAMS+
                            Constants.NIGHTLIFE_FILTER_PARAM+
                            Constants.VENUE_PHOTOS+
                            Constants.SORT_BY_DISTANCE+
                            Constants.LIMIT_PARAM+"20"+
                            Constants.OFFSET_PARAM+offset+
                            Constants.NEAR_MEXICO_CITY_PARAM+
                            Constants.QUERY_PARAM+query);

        JsonObjectRequest request = new JsonObjectRequest(uri, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.d("search", "request starts with "+query);

                try
                {
                    List<Venue> venueList;
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    int returnCode = response.getJSONObject("meta").getInt("code");

                    if (returnCode == 200)
                    {
                        JSONArray items = response.getJSONObject("response").getJSONArray("venues");

                        Gson gson = new Gson();
                        Type tipoListaItems = new TypeToken<List<Venue>>(){}.getType();
                        venueList = gson.fromJson(items.toString(), tipoListaItems);

                        List<Venue> venues = new ArrayList<>();
                        for (int i = 0; i < venueList.size(); i++)
                        {
                            venues.add(venueList.get(i));
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
