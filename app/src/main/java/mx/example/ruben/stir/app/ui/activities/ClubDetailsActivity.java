package mx.example.ruben.stir.app.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.fragments.ClubDetailsFragment;
import mx.example.ruben.stir.app.ui.fragments.MapDetailFragment;

/**
 * Created by Ruben on 5/6/15.
 */
public class ClubDetailsActivity extends ActionBarActivity
{

    Bundle mapBundle;

    public final static int CLUB_DETAIL_FRAGMENT = 0;
    public final static String CLUB_DETAIL_FRAGMENT_TAG = "detailFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mapBundle = new Bundle();
        setContentView(R.layout.activity_club_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StartMapFragment();


        if (savedInstanceState == null)
        {
            savedInstanceState = getIntent().getExtras();
            fragmentSwitcher(savedInstanceState.getInt(CLUB_DETAIL_FRAGMENT_TAG), savedInstanceState);
        }
    }

    private void fragmentSwitcher(int fragmentId, Bundle args)
    {
        switch (fragmentId)
        {
            case CLUB_DETAIL_FRAGMENT:
                setFragment(ClubDetailsFragment.getInstance(args), CLUB_DETAIL_FRAGMENT_TAG);
                break;
        }
    }

    private void setFragment(Fragment wich, String tag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, wich, tag)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
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
    void SetBundle()
    {
        mapBundle.putDouble("latitude", getIntent().getExtras().getDouble(Constants.VENUE_LAT));
        mapBundle.putDouble("longitude", getIntent().getExtras().getDouble(Constants.VENUE_LNG));
        mapBundle.putString(Constants.CLUB_NAME, getIntent().getExtras().getString(Constants.CLUB_NAME));
    }

    void StartMapFragment()
    {
        SetBundle();
        Fragment fragment = MapDetailFragment.getInstance(mapBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.map_detail, fragment).commit();
    }
}
