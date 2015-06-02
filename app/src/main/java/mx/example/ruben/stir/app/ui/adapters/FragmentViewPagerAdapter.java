package mx.example.ruben.stir.app.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by hugo on 4/24/15.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private ArrayList<Fragment> fragments;

    public FragmentViewPagerAdapter(
            FragmentManager fm, Context context, ArrayList<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
