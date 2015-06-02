package mx.example.ruben.stir.app;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ruben on 5/30/15.
 */
public class DistanceHelper
{
    public static final double EARTH_RADIUS_IN_KM = 6371;

    public DistanceHelper() {}

    public double getDistanceBetween(LatLng user,LatLng venue)
    {
        double latDistance = Math.toRadians(user.latitude-venue.latitude);
        double lngDistance = Math.toRadians(user.longitude-venue.longitude);

        double a = Math.sin(latDistance/2)*Math.sin(latDistance/2) +
                   Math.cos(Math.toRadians(user.latitude))*Math.cos(Math.toRadians(venue.latitude))*
                           Math.sin(lngDistance/2)*Math.sin(lngDistance/2);

        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        return c*EARTH_RADIUS_IN_KM;
    }
    public double getDistanceBetween(double userLat,double userLng,double venueLat,double venueLng)
    {
        double latDistance = Math.toRadians(userLat-venueLat);
        double lngDistance = Math.toRadians(userLng-venueLng);

        double a = Math.sin(latDistance/2)*Math.sin(latDistance/2) +
                Math.cos(Math.toRadians(userLat))*Math.cos(Math.toRadians(venueLat))*
                        Math.sin(lngDistance/2)*Math.sin(lngDistance/2);

        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        return c*EARTH_RADIUS_IN_KM;
    }
    //We must add one in Milles
}
