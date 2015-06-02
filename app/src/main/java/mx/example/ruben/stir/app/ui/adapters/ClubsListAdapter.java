package mx.example.ruben.stir.app.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.model.Venue;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.activities.ClubDetailsActivity;
import mx.example.ruben.stir.app.ui.nav.NavigationHelper;

import static java.util.Collections.EMPTY_LIST;

public class ClubsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    //Calcular la distancia entre el usuario y el venue, esto quiza lo deberia hacer el adapter...

    List<Venue> venues = EMPTY_LIST;

    Context context;
    private int DETAIL_FRAGMENT_ID = 0;
    int prende =0;


    public ClubsListAdapter(Context context)
    {
        this.context = context;
        venues = new ArrayList<>();
    }

    public void setData(int pre) {
        prende = pre;
    }

    @Override
    public int getItemViewType(int position)
    {
        return venues.get(position) != null ? R.layout.item_club : R.layout.item_progress;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if(prende == 1 && (viewType == R.layout.item_club) ||  (viewType == R.layout.item_club_search)){
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_club_search, viewGroup, false);
            return new ClubViewHolder(itemView);
        }

        if (viewType == R.layout.item_club)
        {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_club, viewGroup, false);
            return new ClubViewHolder(itemView);
        }
        else
        {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_progress, viewGroup, false);
            return new ProgressViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        if (viewHolder instanceof ClubViewHolder)
        {
            if(venues.get(position) != null)
            {
                Venue currentVenue = venues.get(position);

                ((ClubViewHolder) viewHolder).setImg(currentVenue.getUrlImage());
                ((ClubViewHolder) viewHolder).setName(currentVenue.getName());
                ((ClubViewHolder) viewHolder).setCategory(currentVenue.getCategories().getName());

                ((ClubViewHolder) viewHolder).setGoingThere("0"); //Gotta do the request
                ((ClubViewHolder) viewHolder).setRightNow(String.valueOf(currentVenue.getHereNow()));

                final Bundle bundle = new Bundle();

                bundle.putInt(ClubDetailsActivity.CLUB_DETAIL_FRAGMENT_TAG, DETAIL_FRAGMENT_ID);

                bundle.putString(Constants.CLUB_NAME, currentVenue.getName());
                bundle.putString(Constants.CLUB_URL_IMAGE, String.valueOf(currentVenue.getUrlImage()));

                bundle.putString(Constants.VENUE_HOURS,currentVenue.getHours().getStatus());
                bundle.putString(Constants.VENUE_LOCATION,currentVenue.getLocation().toString());
                bundle.putString(Constants.VENUE_COST,currentVenue.getPrice().toString());

                bundle.putString(Constants.VENUE_LINK,currentVenue.getUrl());
                bundle.putString(Constants.VENUE_FB,currentVenue.getContact().getFacebook().toString());
                bundle.putString(Constants.VENUE_TWITTER,currentVenue.getContact().getTwitter().toString());
                bundle.putString(Constants.VENUE_PHONE,currentVenue.getContact().getPhone());

                ((ClubViewHolder) viewHolder).item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavigationHelper.startClubDetail(((ActionBarActivity) context), bundle);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount()
    {
        if (venues == null)
            return 0;
        return venues.size();
    }

    public int getVenuesItemsCount()
    {
        if (isProgressViewVisible())
            return venues.size() - 1;

        return venues.size();
    }

    public void updateList(List<Venue> venues)
    {
        this.venues.addAll(venues);
        notifyDataSetChanged();
    }
    public void RemoveProgressView()
    {

        venues.remove(venues.size() - 1);
        notifyDataSetChanged();
    }

    public void showOnLoadViewHolder()
    {
        venues.add(null);
        notifyDataSetChanged();
    }

    public boolean isProgressViewVisible() {
        return venues.contains(null);
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.itemMain)
        RelativeLayout item;

        @InjectView(R.id.img_club)
        SimpleDraweeView venueImage;

        @InjectView(R.id.txt_club_name)
        TextView venueTitle;
        @InjectView(R.id.txt_goingThere)
        TextView goingThere;
        @InjectView(R.id.txt_rightNow)
        TextView rightNow;
        @InjectView(R.id.txt_type_name)
        TextView venueCategory;

        public void setImg(Uri urlImage)
        {
            if (!urlImage.equals(Uri.EMPTY))
                venueImage.setImageURI(urlImage);}

        public void setCategory(String category)
        {venueCategory.setText(category);}

        public void setGoingThere(String peopleGoing)
        {goingThere.setText(peopleGoing+" Quieren ir");}

        public void setRightNow(String peopleThere)
        {rightNow.setText(peopleThere+" Estan Ahi");}

        public void setName(String name) {
            venueTitle.setText(name);
        }

        public ClubViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}

