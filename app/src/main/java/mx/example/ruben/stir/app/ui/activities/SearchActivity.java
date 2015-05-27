package mx.example.ruben.stir.app.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.fragments.SearchListFragment;

/**
 * Created by Ruben on 5/27/15.
 */
public class SearchActivity extends ActionBarActivity
{
    @InjectView(R.id.searchView)
    SearchView mSearchView;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this); bundle = new Bundle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView.setOnSearchClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bundle.putString(Constants.QUERY_SEARCH,mSearchView.getQuery().toString());
                Fragment fragment = SearchListFragment.getInstance(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.search_main_container, fragment).commit();
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
