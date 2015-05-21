package mx.example.ruben.stir.app.ui.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.ui.adapters.NavigationDrawerAdapter;
import mx.example.ruben.stir.app.model.ItemOptionNavDra;

public class NavigationDrawerFragment extends Fragment {

    private NavigationDrawerCallbacks mCallbacks;

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private View header;

    private int mCurrentSelectedPosition = 0;
    private String firstName;
    private String lastName;
    private Uri profileImage;
    TextView nameProfile;
    SimpleDraweeView imgProfile;


    public NavigationDrawerFragment()
    {
    }

    public interface NavigationDrawerCallbacks
    {
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        mCallbacks.onNavigationDrawerItemSelected(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        header = getLayoutInflater(savedInstanceState).inflate(R.layout.header_profile_drawer, null);
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.addHeaderView(header);
        getProfile();

        return mDrawerListView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mDrawerListView.setAdapter(new NavigationDrawerAdapter(getActivity(), loadArrayOptions()));

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

    }

    public List<ItemOptionNavDra> loadArrayOptions() {

        List<ItemOptionNavDra> itemOptionNavDras = new ArrayList<>();

        itemOptionNavDras.add(new ItemOptionNavDra(R.drawable.ic_local_bar_black_48dp, R.string.title_section1));
        itemOptionNavDras.add(new ItemOptionNavDra(R.drawable.ic_action_location_found, R.string.title_section2));
        itemOptionNavDras.add(new ItemOptionNavDra(R.drawable.ic_action_settings, R.string.title_section3));

        return itemOptionNavDras;

    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        mCallbacks.onNavigationDrawerItemSelected(position);
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }


    public void setUp(View mFragmentContainerView, DrawerLayout drawerLayout, Toolbar toolbar)
    {
        this.mFragmentContainerView = mFragmentContainerView;
        this.mDrawerLayout = drawerLayout;

        enableHomeButton();

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void getProfile(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("fb_user_prefs", getActivity().MODE_PRIVATE);
        firstName = sharedPreferences.getString("first_name", "");
        lastName = sharedPreferences.getString("last_name", "");
        profileImage = Uri.parse(sharedPreferences.getString("img_profile", ""));

        nameProfile = (TextView) header.findViewById(R.id.txt_profile_name);
        nameProfile.setText(firstName + " " + lastName);

        imgProfile = (SimpleDraweeView) header.findViewById(R.id.img_profile);
        imgProfile.setImageURI(profileImage);

    }

    private void enableHomeButton ()
    {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallbacks = null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }


}
