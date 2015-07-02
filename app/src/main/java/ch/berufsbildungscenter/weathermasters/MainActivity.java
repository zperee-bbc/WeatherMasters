package ch.berufsbildungscenter.weathermasters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    public static final String WETTERDATA = "WetterData";
    private Standort standort;
    private AktuellesWetter aktuellesWetter;
    private long lastRefresh;
    private String temperature;
    private String details;
    private String beschreibung;
    private String stadt;
    private String icon;
    private GPSTracker gpsTracker;

    Dialog dialog;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(R.string.orte).setTabListener(this), false);
        actionBar.addTab(actionBar.newTab().setText(R.string.aktuell).setTabListener(this), true);
        actionBar.addTab(actionBar.newTab().setText(R.string.vorhersage).setTabListener(this), false);
        actionBar.setHomeButtonEnabled(false);

        checkAndSetOfflineData();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
                gpsTracker = new GPSTracker(MainActivity.this);
                if (gpsTracker.canGetLocation()){
                    standort = new Standort();
                    standort.setLatitude(gpsTracker.getLatitude());
                    standort.setLongitude(gpsTracker.getLongitude());
                } else {
                    gpsTracker.showSettingsAlert();
                }
            gpsTracker.stopUsingGPS();



//                GPSTracker gpsTracker = new GPSTracker();
//                gpsDialog = ProgressDialog.show(MainActivity.this, "Suche genaue GPS Position", "Bitte warten...");
//                standort = new Standort();
//                standort.setLongitude(gpsTracker.location.getLongitude());
//                standort.setLatitude(gpsTracker.location.getLatitude());
//                gpsDialog.dismiss();

                Calendar checkCalendar = Calendar.getInstance();
                Date checkNow = checkCalendar.getTime();
                long timeStampCheck = checkNow.getTime() / 1000;

                if (timeStampCheck - lastRefresh / 1000 > 600) {
                    dialog = ProgressDialog.show(this, "Lade Informationen", "Bitte warten...");
                    JSonLoadingActualTask jSonLoadingActualTask = new JSonLoadingActualTask(this);
                    jSonLoadingActualTask.execute("lat=" + standort.getLatitude() + "&lon=" + standort.getLongitude());
                } else {
                    checkAndSetOfflineData();
                }
        }
        else {
            checkAndSetOfflineData();
            TextView connection = (TextView) findViewById(R.id.textViewConnection);
            connection.setText(R.string.noConnection);
            connection.setTextColor(Color.RED);
        }
    }

    public void setData(AktuellesWetter aktuellesWetter) {

        this.aktuellesWetter = aktuellesWetter;

        TextView dataView = (TextView) findViewById(R.id.textViewDetail);
        dataView.setText(aktuellesWetter.detailToString());

        TextView ortschaftView = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaftView.setText(aktuellesWetter.getStandort().getStadt());

        ImageView imgView = (ImageView) findViewById(R.id.imageViewWetter);
        loadImage(aktuellesWetter.getIconPath(), imgView);

        TextView temp = (TextView) findViewById(R.id.textViewTemperatur);
        temp.setText(aktuellesWetter.tempToString());

        TextView description = (TextView) findViewById(R.id.textViewBeschreibung);
        description.setText(aktuellesWetter.descriptionToString());

        TextView ortschaft = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaft.setText(aktuellesWetter.getStandort().getStadt());

        //Timestamp
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        long timeStamp = now.getTime();
        Timestamp currentTimestamp = new Timestamp(timeStamp);

        TextView time = (TextView) findViewById(R.id.textViewAktualisiert);
        time.setText("Zuletzt aktualisiert: " + currentTimestamp);

        //Schreibt aktuellen Timestamp in File
        SharedPreferences timeStampFile = getSharedPreferences(WETTERDATA, 0);
        SharedPreferences.Editor editor = timeStampFile.edit();
        editor.putLong("TimeStamp", timeStamp);
        editor.putString("Temperatur", aktuellesWetter.tempToString());
        editor.putString("Details", aktuellesWetter.descriptionToString());
        editor.putString("Beschreibung", aktuellesWetter.detailToString());
        editor.putString("Icon", aktuellesWetter.getIconPath());
        editor.putString("Ortschaft", aktuellesWetter.getStandort().getStadt());

        editor.commit();

        Log.i(LOG_TAG, "TimeStamp " + String.valueOf(timeStamp));

        dialog.dismiss();
    }

    public void loadImage(String stringBuilderIcon, ImageView imageView) {
        if (stringBuilderIcon.equals("01d")) {
            imageView.setImageResource(R.drawable.day_clear);
        } else if (stringBuilderIcon.equals("02d")) {
            imageView.setImageResource(R.drawable.day_few_clouds);
        } else if (stringBuilderIcon.equals("03d")) {
            imageView.setImageResource(R.drawable.day_scattered_clouds);
        } else if (stringBuilderIcon.equals("04d")) {
            imageView.setImageResource(R.drawable.day_broken_clouds);
        } else if (stringBuilderIcon.equals("09d")) {
            imageView.setImageResource(R.drawable.day_shower_rain);
        } else if (stringBuilderIcon.equals("10d")) {
            imageView.setImageResource(R.drawable.day_rain);
        } else if (stringBuilderIcon.equals("11d")) {
            imageView.setImageResource(R.drawable.day_thunderstorm);
        } else if (stringBuilderIcon.equals("13d")) {
            imageView.setImageResource(R.drawable.day_snow);
        } else if (stringBuilderIcon.equals("50d")) {
            imageView.setImageResource(R.drawable.day_mist);
        } else if (stringBuilderIcon.equals("01n")) {
            imageView.setImageResource(R.drawable.night_clear);
        } else if (stringBuilderIcon.equals("02n")) {
            imageView.setImageResource(R.drawable.night_few_clouds);
        } else if (stringBuilderIcon.equals("03n")) {
            imageView.setImageResource(R.drawable.night_scattered_clouds);
        } else if (stringBuilderIcon.equals("04n")) {
            imageView.setImageResource(R.drawable.night_broken_clouds);
        } else if (stringBuilderIcon.equals("09n")) {
            imageView.setImageResource(R.drawable.night_shower_rain);
        } else if (stringBuilderIcon.equals("10n")) {
            imageView.setImageResource(R.drawable.night_rain);
        } else if (stringBuilderIcon.equals("11n")) {
            imageView.setImageResource(R.drawable.night_thunderstorm);
        } else if (stringBuilderIcon.equals("13n")) {
            imageView.setImageResource(R.drawable.night_snow);
        } else if (stringBuilderIcon.equals("50n")) {
            imageView.setImageResource(R.drawable.night_mist);
        } else {
            Log.i(LOG_TAG, "Kein Icon gefunden");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.buttonSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            Log.i(LOG_TAG, "Settings Clicked");
            return true;
        } else if (id == R.id.buttonSearch) {
            Intent intent = new Intent(this, Favorite_cities.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAndSetOfflineData(){
        //Check zuletzt aktualisiert
        SharedPreferences timeStampFile = getSharedPreferences(WETTERDATA, 0);
        lastRefresh = timeStampFile.getLong("TimeStamp", 0);
        temperature = timeStampFile.getString("Temperatur", "fail");
        details = timeStampFile.getString("Details", "fail");
        beschreibung = timeStampFile.getString("Beschreibung", "fail");
        stadt = timeStampFile.getString("Ortschaft", "fail");
        icon = timeStampFile.getString("Icon", "fail");
        TextView temp = (TextView) findViewById(R.id.textViewTemperatur);
        temp.setText(temperature);
        Timestamp lastRefreshText = new Timestamp(lastRefresh);
        TextView time = (TextView) findViewById(R.id.textViewAktualisiert);
        time.setText("Zuletzt aktualisiert: " + lastRefreshText);
        TextView dataView = (TextView) findViewById(R.id.textViewDetail);
        dataView.setText(details);
        TextView description = (TextView) findViewById(R.id.textViewBeschreibung);
        description.setText(beschreibung);
        TextView ortschaft = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaft.setText(stadt);
        ImageView imgView = (ImageView) findViewById(R.id.imageViewWetter);
        loadImage(icon, imgView);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 2) {
            Log.i(LOG_TAG, "Vorhersage");
            Intent intent = new Intent(this, PredictionActivity.class);
            TextView textViewOrtschaft =(TextView)findViewById(R.id.textViewOrtschaft);
            intent.putExtra("stadt", textViewOrtschaft.getText());
            startActivity(intent);
        } else if (tab.getPosition() == 0) {
            Log.i(LOG_TAG, "Orte");
            Intent intent = new Intent(this, Favorite_cities.class);

            startActivity(intent);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}