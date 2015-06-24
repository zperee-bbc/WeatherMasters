package ch.berufsbildungscenter.weathermasters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //Check zuletzt aktualisiert
        SharedPreferences timeStampFile = getSharedPreferences(WETTERDATA, 0);
        long lastRefresh = timeStampFile.getLong("TimeStamp", 0);
        String temperature = timeStampFile.getString("Temperatur", "fail");
        String details = timeStampFile.getString("Details", "fail");
        String beschreibung = timeStampFile.getString("Beschreibung", "fail");
        String icon = timeStampFile.getString("Icon", "fail");

        Calendar checkCalendar = Calendar.getInstance();
        Date checkNow = checkCalendar.getTime();
        long timeStampCheck = checkNow.getTime() / 1000;

        if (timeStampCheck - lastRefresh/1000 > 600) {
            dialog = ProgressDialog.show(this, "Lade Informationen,", "Bitte warten...");
            JSonLoadingTask loadingTask = new JSonLoadingTask(this);
            loadingTask.execute(String.valueOf("Uster,CH"));
        } else {
            Log.i(LOG_TAG, "Nicht aktuallisiert ");
            TextView temp = (TextView) findViewById(R.id.textViewTemperatur);
            temp.setText(temperature);
            Timestamp lastRefreshText = new Timestamp(lastRefresh);
            TextView time = (TextView) findViewById(R.id.textViewAktualisiert);
            time.setText("Zuletzt aktualisiert: " + lastRefreshText);
            TextView dataView = (TextView) findViewById(R.id.textViewDetail);
            dataView.setText(details);
            TextView description = (TextView) findViewById(R.id.textViewBeschreibung);
            description.setText(beschreibung);
            ImageView imgView = (ImageView) findViewById(R.id.imageViewWetter);
            loadImage(icon, imgView);
        }
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    public void setData(AktuellesWetter aktuellesWetter) {

        StringBuilder sb = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderTemp = new StringBuilder();
        StringBuilder stringBuilderIcon = new StringBuilder();

        sb.append(aktuellesWetter.toString());
        sb.append("\n\n");

        stringBuilder.append(aktuellesWetter.descriptionToString());
        stringBuilderTemp.append(aktuellesWetter.tempToString());
        stringBuilderIcon.append(aktuellesWetter.getIconPath());

        TextView dataView = (TextView) findViewById(R.id.textViewDetail);
        dataView.setText(sb.toString());

        TextView ortschaftView = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaftView.setText("Uster, Zh");

        ImageView imgView = (ImageView) findViewById(R.id.imageViewWetter);
        loadImage(stringBuilderIcon.toString(), imgView);

        TextView temp = (TextView) findViewById(R.id.textViewTemperatur);
        temp.setText(stringBuilderTemp.toString());

        TextView description = (TextView) findViewById(R.id.textViewBeschreibung);
        description.setText(stringBuilder.toString());

        //Timestamp
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        long timeStamp = now.getTime();
        Timestamp currentTimestamp = new Timestamp(timeStamp);
//        timeStamp = timeStamp / 1000;

        TextView time = (TextView) findViewById(R.id.textViewAktualisiert);
        time.setText("Zuletzt aktualisiert: " + currentTimestamp);

        //Schreibt aktuellen Timestamp in File
        SharedPreferences timeStampFile = getSharedPreferences(WETTERDATA, 0);
        SharedPreferences.Editor editor = timeStampFile.edit();
        editor.putLong("TimeStamp", timeStamp);
        editor.putString("Temperatur", stringBuilderTemp.toString());
        editor.putString("Details", sb.toString());
        editor.putString("Beschreibung", stringBuilder.toString());
        editor.putString("Icon", stringBuilderIcon.toString());

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
            Log.i(LOG_TAG, "Settings Clicked");
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 2) {
            Log.i(LOG_TAG, "Vorhersage");
            Intent intent = new Intent(this, Weather_prediction.class);
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
