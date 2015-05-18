package mx.example.ruben.stir.app.model;

import android.net.Uri;

import com.google.gson.JsonObject;

/**
 * Created by Ruben on 5/6/15.
 */
public class Club
{
    int id;
    String name;
    String description;
    Uri urlImage;

    public Club(int id, String name, String description, Uri urlImage)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Uri getUrlImage() {
        return urlImage;
    }

     /*private static int extractCharacterAvailableResource(String availableResource, JsonObject characterData) throws IllegalArgumentException
    {
        if(!availableResource.equals(Constants.COMICS_KEY) &&
                !availableResource.equals(Constants.STORIES_KEY) && !availableResource.equals(Constants.SERIES_KEY))
            throw new IllegalArgumentException();
        else
            return characterData.get(availableResource).getAsJsonObject().get(Constants.AVAILABLE_KEY).getAsInt();
    }*/

}