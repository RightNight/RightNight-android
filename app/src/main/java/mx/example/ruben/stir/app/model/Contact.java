package mx.example.ruben.stir.app.model;

import android.net.Uri;

/**
 * Created by Ruben on 5/21/15.
 */
public class Contact
{
    private String phone;
    private String twitter;
    private String facebook;

    public String getPhone()
    {
        return phone;
    }

    public Uri getTwitter()
    {
        if (twitter!= null)
        return Uri.parse("www.twitter.com/"+twitter);
        else {return Uri.EMPTY;}
    }

    public Uri getFacebook()
    {
        if (facebook!=null)
        return Uri.parse("www.facebook.com/"+facebook);
        else
        {
            return Uri.EMPTY;
        }
    }
}
