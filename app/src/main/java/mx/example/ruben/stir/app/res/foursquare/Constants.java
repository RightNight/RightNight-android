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

    public static final String LIMIT_PARAM = "&limit=";
    public static final String LOCATION_PARAM = "&ll=";
    public static final String RADIUS_PARAM = "&radius=";
    public static final String OFFSET_PARAM = "&offset=";
    public static final String NEAR_MEXICO_CITY_PARAM = "&near=Mexico+City";
    public static final String QUERY_PARAM = "&query=";


    public static final String API_OB_PARAMS = "?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&v="+API_DATE_VERSION;
    public static final String NIGHTLIFE_FILTER_PARAM = "&categoryId=4d4b7105d754a06376d81259";
    public static final String VENUE_PHOTOS = "&venuePhotos=1" ;
    public static final String SORT_BY_DISTANCE = "&sortByDistance=1" ;
    public static final String CLUB_URL_IMAGE = "photo";

    public static final String CALLBACK_URL = "http://localhost:8888";
    public static final String SHARED_PREF_FILE = "shared_pref";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_INFO = "user_info";

///BUNDLE STUFF
    public static final String CLUB_ID = "id";
    public static final String CLUB_NAME = "name";
    public static final String CLUB_DESCRIPTION = "description";
    public static final String VENUE_LOCATION = "location";
    public static final String VENUE_COST = "cost";
    public static final String VENUE_HOURS = "hours";
    public static final String VENUE_LNG = "lng";
    public static final String VENUE_LAT = "lat";
    public static final String VENUE_RATING = "rating";

    public static final String VENUE_LINK = "link";
    public static final String VENUE_PHONE = "phone";
    public static final String VENUE_FB = "fb";
    public static final String VENUE_TWITTER = "twitter";

//SEARCH ACTIVITY BUNDLE
    public static final String QUERY_SEARCH = "busqueda";

    // GNERAL STUFF
    public static final String EMPTY_STRING = "---";
    public static final String SETTINGS_RADIO = "radio_map";
    public static final String QUIERO_VENUE_1 = "quiero_v1";
    public static final String QUIERO_VENUE_2 = "quiero_v2";
    public static final String TXT_QUIERO = "QUIERO IR";
    public static final String TXT_NO_QUIERO = "NO QUIERO IR";


    //FACEBOOK
    public static final String SHARED_FB_PREFS = "fb_user_prefs";
    public static final String FB_FIRST_NAME = "first_name";
    public static final String FB_LAST_NAME = "last_name";
    public static final String FB_ID = "fb_id";
    public static final String FB_IMAGE_PROFILE = "img_profile";
    public static final String FB_LOGIN = "is_login";




}