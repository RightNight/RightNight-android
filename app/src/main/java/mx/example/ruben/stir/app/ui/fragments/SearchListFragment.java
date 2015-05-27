package mx.example.ruben.stir.app.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import mx.example.ruben.stir.R;

/**
 * Created by Ruben on 5/27/15.
 */
public class SearchListFragment extends android.support.v4.app.Fragment
{
    @InjectView(R.id.search_list_clubs)
    RecyclerView mSearchListClubs;

    public SearchListFragment() {}

    public static SearchListFragment getInstance(Bundle bundle)
    {
        SearchListFragment mSearchListFragment = new SearchListFragment();
        mSearchListFragment.setArguments(bundle);
        return mSearchListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_search_list, container, false);
        initView();


        return rootView;
    }
    private void initView()
    {
        //AQUI LLENAR LA LIST (TextViews y asi)
    }

/*
    private static final String LOG_TAG = ClubsFragment.class.getCanonicalName();

    public Context CONTEXT;
    Bundle mBundle;
    LatLng location;

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
        location = new LatLng(mBundle.getDouble("latitude"),mBundle.getDouble("longitude"));
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

        final String uri = (Constants.API_URL_VENUES+Constants.EXPLORE+Constants.API_OB_PARAMS+Constants.NIGHTLIFE_FILTER_PARAM+
                Constants.VENUE_PHOTOS+Constants.SORT_BY_DISTANCE+"&limit=20"+"&offset="+offset+
                "&ll="+location.latitude+","+location.longitude);

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
                            venues.add(itemList.get(i).getVenue());
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
    }*/

}
