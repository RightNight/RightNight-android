package mx.example.ruben.stir.app.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mx.example.ruben.stir.R;
import mx.example.ruben.stir.app.model.Venue;
import mx.example.ruben.stir.app.res.foursquare.Constants;
import mx.example.ruben.stir.app.ui.activities.ClubDetailsActivity;
import mx.example.ruben.stir.app.ui.nav.NavigationHelper;

import static android.content.Context.*;
import static java.util.Collections.EMPTY_LIST;

public class ClubsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    //Calcular la distancia entre el usuario y el venue, esto quiza lo deberia hacer el adapter...

    List<Venue> venues = EMPTY_LIST;

    Context context;
    private int DETAIL_FRAGMENT_ID = 0;
    int prende =0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public ClubsListAdapter(Context context)
    {
        this.context = context;
        venues = new ArrayList<>();
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_FB_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
                ((ClubViewHolder) viewHolder).setDistance(currentVenue.getDistance());
                ((ClubViewHolder) viewHolder).setActionbutton(currentVenue.getId());


                final Bundle bundle = new Bundle();

                bundle.putInt(ClubDetailsActivity.CLUB_DETAIL_FRAGMENT_TAG, DETAIL_FRAGMENT_ID);

                bundle.putString(Constants.CLUB_ID, currentVenue.getId());
                bundle.putString(Constants.CLUB_NAME, currentVenue.getName());
                bundle.putString(Constants.CLUB_URL_IMAGE, String.valueOf(currentVenue.getUrlImage()));

                bundle.putString(Constants.VENUE_HOURS, currentVenue.getHours().getStatus());
                bundle.putString(Constants.VENUE_LOCATION, currentVenue.getLocation().toString());
                bundle.putDouble(Constants.VENUE_LNG, currentVenue.getLocation().getLng());
                bundle.putDouble(Constants.VENUE_LAT, currentVenue.getLocation().getLat());

                String realPrice = currentVenue.getPrice() != null ? currentVenue.getPrice().toString() : "";
                bundle.putString(Constants.VENUE_COST, realPrice);
                bundle.putDouble(Constants.VENUE_RATING, currentVenue.getRating());


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
        @InjectView(R.id.userVenueDistance)
        TextView userVenueDistance;

        @InjectView(R.id.txt_club_name)
        TextView venueTitle;
        @InjectView(R.id.txt_goingThere)
        TextView goingThere;
        @InjectView(R.id.txt_rightNow)
        TextView rightNow;
        @InjectView(R.id.txt_type_name)
        TextView venueCategory;
        @InjectView(R.id.btn_quiero)
        Button quieroIr;

        public void setImg(Uri urlImage)
        {
            if (!urlImage.equals(Uri.EMPTY))
                venueImage.setImageURI(urlImage);}

        public void setDistance(double distance)
        {
            String dist = String.valueOf(distance);
            userVenueDistance.setText(dist.substring(0,4)+"km");
        }

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
        public void setActionbutton(final String venueId)
        {
            // Valor4es al crear el elemento
            String quieroTxt = (String) quieroIr.getText();
            String v1 = sharedPreferences.getString(Constants.QUIERO_VENUE_1, "0");
            String v2 = sharedPreferences.getString(Constants.QUIERO_VENUE_2, "0");
            Log.i("venues ", v1 + " - " + v2);

            if (Objects.equals(v1, venueId)){
                quieroIr.setText(Constants.TXT_NO_QUIERO);
                setGoingThere("1");
            } else if (Objects.equals(v2, venueId)){
                quieroIr.setText(Constants.TXT_NO_QUIERO);
                setGoingThere("1");
            }

            // Click listener
            quieroIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String quieroTxt = (String) quieroIr.getText();
                    String userID = sharedPreferences.getString(Constants.FB_ID, "");
                    String v1 = sharedPreferences.getString(Constants.QUIERO_VENUE_1, "0");
                    String v2 = sharedPreferences.getString(Constants.QUIERO_VENUE_2, "0");

                    if (Objects.equals(quieroTxt, Constants.TXT_QUIERO))
                    {
                        if (Objects.equals(v1, "0")){
                            editor.putString(Constants.QUIERO_VENUE_1, venueId);
                            editor.apply();
                            quieroIr.setText(Constants.TXT_NO_QUIERO);

                            String estanAhi = (String) goingThere.getText();
                            estanAhi = estanAhi.replace(" Quieren ir", "");
                            int estaranAhi = Integer.parseInt(estanAhi) + 1;
                            setGoingThere(String.valueOf(estaranAhi));
                        } else if (Objects.equals(v2, "0")){
                            editor.putString(Constants.QUIERO_VENUE_2, venueId);
                            editor.apply();
                            quieroIr.setText(Constants.TXT_NO_QUIERO);

                            String estanAhi = (String) goingThere.getText();
                            estanAhi = estanAhi.replace(" Quieren ir", "");
                            int estaranAhi = Integer.parseInt(estanAhi) + 1;
                            setGoingThere(String.valueOf(estaranAhi));
                        } else {
                            Toast.makeText(context, "Ya has elegido dos lguares esta noche", Toast.LENGTH_SHORT).show();
                        }
                    } else if (Objects.equals(quieroTxt, Constants.TXT_NO_QUIERO))
                    {
                        if (Objects.equals(v1, venueId)){
                            editor.putString(Constants.QUIERO_VENUE_1, "0");
                            editor.apply();
                            quieroIr.setText(Constants.TXT_QUIERO);

                            String estanAhi = (String) goingThere.getText();
                            estanAhi = estanAhi.replace(" Quieren ir", "");
                            int estaranAhi = Integer.parseInt(estanAhi) - 1;
                            setGoingThere(String.valueOf(estaranAhi));

                        } else if (Objects.equals(v2, venueId)){
                            editor.putString(Constants.QUIERO_VENUE_2, "0");
                            editor.apply();
                            quieroIr.setText(Constants.TXT_QUIERO);

                            String estanAhi = (String) goingThere.getText();
                            estanAhi = estanAhi.replace(" Quieren ir", "");
                            int estaranAhi = Integer.parseInt(estanAhi) - 1;
                            setGoingThere(String.valueOf(estaranAhi));
                        }
                    }
                }
            });
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}

