package mx.example.ruben.stir.app.model;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by irata on 19/05/15.
 */
public class FeaturedPhotos {

    private ArrayList<ItemsPhoto> items;

    public ItemsPhoto getItemPhoto() {
        return items.get(0);
    }
}
