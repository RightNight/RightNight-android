package mx.example.ruben.stir.app.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.fragments.SearchListFragment;

/**
 * Created by Ruben on 5/27/15.
 */
public class SearchActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    //SEARCH ACTIVITY TAMBIEN PEDIRA LOCATION, Se la pasa a la lista de Search y ella calcula DIstancia User-Venue

    String TAG = "Search-Location";
    Bundle bundle;
    SearchView mSearchView;
    int id;
    GoogleApiClient mGoogleApiClient;
    LatLng userPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        bundle = new Bundle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initSearchView();
    }

    private void initSearchView()
    {
        // Associate search configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("¿Qué buscaremos hoy?");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                StartFragment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mSearchView.getQuery().length() > 5)
                {
                    StartFragment();
                }
                return false;
            }
        });
    }
    void SetBundle()
    {
        bundle.putString(Constants.QUERY_SEARCH, mSearchView.getQuery().toString());
        bundle.putDouble("latitude", userPosition.latitude);
        bundle.putDouble("longitude", userPosition.longitude);
    }
    void StartFragment()
    {
        SetBundle();
        Fragment fragment = SearchListFragment.getInstance(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.search_main_container, fragment).commit();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation == null)
        {
            Toast.makeText(this, "Turn GPS On ", Toast.LENGTH_LONG).show();
        }
        if (mLastLocation != null)
        {
            userPosition = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            StartFragment();
        }

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    } //LEER API PARA MAS INFORMACION DE ESTO

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
