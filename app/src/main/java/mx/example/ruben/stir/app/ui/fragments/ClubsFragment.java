package mx.example.ruben.stir.app.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.ui.adapters.ClubsListAdapter;


public class ClubsFragment extends Fragment {

    private static final String LOG_TAG = ClubsFragment.class.getCanonicalName();
    public Context CONTEXT;

    @InjectView(R.id.list_clubs)
    RecyclerView mListClubs;

    ClubsListAdapter adapter;

    public ClubsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        CONTEXT = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onResume() {
        super.onResume();
    }

    private void initListClubs()
    {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new ClubsListAdapter(CONTEXT);
        adapter.DummyContent();
        mListClubs.setLayoutManager(lm);
        mListClubs.setAdapter(adapter);

    }
}