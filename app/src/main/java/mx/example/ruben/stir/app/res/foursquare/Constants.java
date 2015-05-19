package mx.example.ruben.stir.app.res.foursquare;

/**
 * Created by Ruben on 5/6/15.
 */
public class Constants
{
    public static final String API_DATE_VERSION = "20150506";
    public static final String CLIENT_ID = "KNPEITNU1WH4FVCJ2MJOHXW3JLMZQZ0H2TMCVCZIQOHFXB5H";
    public static final String CLIENT_SECRET = "ECPYYVSODJ2KVRQHJKN5ZHK0VFHHII15BHVNJZEZK5HB5WQG";

    public static final String API_URL = "https://api.foursquare.com/v2";
    public static final String API_URL_VENUES = API_URL+"/venues";

    public static final String EXPLORE = "/explore";
    public static final String SEARCH = "/search";

    public static final String API_OB_PARAMS = "?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&v="+API_DATE_VERSION;
    public static final String NIGHTLIFE_FILTER_PARAM = "&categoryId=4d4b7105d754a06376d81259";

    public static final String CALLBACK_URL = "http://localhost:8888";
    public static final String SHARED_PREF_FILE = "shared_pref";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_INFO = "user_info";

    public static final String CLUB_NAME = "name";
    public static final String CLUB_DESCRIPTION = "description";
    public static final String VENUE_PHOTOS = "&venuePhotos=1" ;
    public static final String SORT_BY_DISTANCE = "&sortByDistance=1" ;
    public static final String CLUB_URL_IMAGE = "photo";
}