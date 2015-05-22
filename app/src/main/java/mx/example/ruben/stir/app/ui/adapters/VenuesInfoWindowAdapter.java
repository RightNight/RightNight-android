package mx.example.ruben.stir.app.ui.adapters;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Ruben on 5/20/15.
 */
public class VenuesInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{

    VenuesInfoWindowAdapter()
    {}

    @Override
    public View getInfoWindow(Marker marker) //First one to call
    {

        return null;
    }

    @Override
    public View getInfoContents(Marker marker)
    {

        return null;
    }

    /*
    The first of these (getInfoWindow()) allows you to provide a view that will be used for the entire info window.
    The second of these (getInfoContents()) allows you to just customize the contents of the window but still keep
    the default info window frame and background.

    Note: The info window that is drawn is not a live view.
    The view is rendered as an image (using View.draw(Canvas)) at the time it is returned.
    This means that any subsequent changes to the view will not be reflected by the info window on the map.
    To update the info window later (for example, after an image has loaded), call showInfoWindow().
    Furthermore, the info window will not respect any of the interactivity typical for a normal view such as touch or gesture events.
    However you can listen to a generic click event on the whole info window as described in the section below.

    DO NOT USE BUTTONS OR LISTENERS INSIDE THIS
     */

}
