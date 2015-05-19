package mx.example.ruben.stir.app.model;

import android.net.Uri;

/**
 * Created by irata on 19/05/15.
 */
public class ItemsPhoto {
    String prefix;
    String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Uri getFull(){
        String full = prefix + "width400" + suffix;
        return Uri.parse(full);
    }
}
