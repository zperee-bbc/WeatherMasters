package ch.berufsbildungscenter.weathermasters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

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