package mx.example.ruben.stir.app.model;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruben on 5/6/15.
 */
public class Venue
{
    private String id;
    private String name;
    private Location location;
    private ArrayList<Category> categories;
    private boolean verified;
    private HereNow hereNow;
    private String url;
    private FeaturedPhotos featuredPhotos;
    private Uri urlImage;

    private double rating;
    private String ratingColor;

    private Contact contact;
    private Price price;
    private Hours hours;

    private double distance; //Between User and Venue,if not set then it will be NULL

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Contact getContact() {
        return contact;
    }

    public Price getPrice()
    {
        if (price!=null)
            return price;
        return new Price(0,"");
    }

    public Hours getHours()
    {
    if (hours != null)
        return hours;

    return new Hours(false,"");
    }

    public String getUrl()
    {
        if (url!=null)
        return url;

        return " ";
    }

    public double getRating() {
        return rating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public int getHereNow()
    {
        return hereNow.getCount();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Location getLocation() {
        return location;
    }
    public Category getCategories() {
        return categories.get(0);
    }
    public boolean isVerified() {
        return verified;
    }
    public FeaturedPhotos getFeaturedPhotos() {
        return featuredPhotos;
    }

    public Uri getUrlImage()
    {
        if (getFeaturedPhotos() != null)
        {
            ItemsPhoto photo = getFeaturedPhotos().getItemPhoto();
            Uri urlImage = photo.getFull();
            return urlImage;
        }
        return Uri.EMPTY;
    }
}
