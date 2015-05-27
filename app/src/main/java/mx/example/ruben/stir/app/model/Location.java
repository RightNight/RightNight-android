package mx.example.ruben.stir.app.model;

import java.util.ArrayList;

/**
 * Created by Ruben on 5/12/15.
 */

public class Location
{

    private String address;
    private String city;
    private String state;
    private double lat;
    private double lng;
    private double distance;

    public double getDistance()
    {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString()
    {

        if (address== null)
            return " ";
        else if(city == null)
            return address;
        else if(state == null)
            return address+" "+city;
        else
        {
            return (address+" "+city+" "+state);
        }
    }
}