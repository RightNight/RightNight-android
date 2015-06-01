package mx.example.ruben.stir.app.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.fragments.SearchListFragment;

/**
 * Created by Ruben on 5/27/15.
 */
public class SearchActivity extends ActionBarActivity
{

    Bundle bundle;
    SearchView mSearchView;
    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        int linlayId = getResources().getIdentifier("android:id/search_plate", null, null);
        View view = mSearchView.findViewById(linlayId);
        Drawable drawColor = getResources().getDrawable(R.drawable.background_item_search);
        view.setBackground(drawColor);




        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("search", mSearchView.getQuery().toString());

                bundle.putString(Constants.QUERY_SEARCH, mSearchView.getQuery().toString());
                Fragment fragment = SearchListFragment.getInstance(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.search_main_container, fragment).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mSearchView.getQuery().length() > 4) {
                    Log.d("search", mSearchView.getQuery().toString());
                    bundle.putString(Constants.QUERY_SEARCH, mSearchView.getQuery().toString());
                    Fragment fragment = SearchListFragment.getInstance(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.search_main_container, fragment).commit();
                }
                return false;
            }
        });
    }

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
