package ch.berufsbildungscenter.weathermasters;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by zmartl on 24.06.2015.
 * Version ${VERSION}
 */
public class GPSTracker {
    Activity activity;
    double latitude;
    double longtitude;
    public GPSTracker(Activity activity) {
        this.activity = activity;
        LocationManager locationManager = (LocationManager) this.activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);
        latitude = location.getLatitude();
        longtitude = location.getLongitude();
    }





}