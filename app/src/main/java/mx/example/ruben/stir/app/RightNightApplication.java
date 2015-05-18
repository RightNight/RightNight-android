package mx.example.ruben.stir.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ruben on 5/12/15.
 */
public class RightNightApplication extends Application
{

    private RequestQueue mRequestQ;
    public static final String TAG = "VolleyPatterns";
    private static RightNightApplication sInstance;



    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;
    }
    public static synchronized RightNightApplication getInstance()
    {
        return sInstance;
    }
    public RequestQueue getRequestQueue()
    {
        if (mRequestQ == null)
        {
            mRequestQ = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQ;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQ != null)
        {
            mRequestQ.cancelAll(tag);
        }
    }
}
