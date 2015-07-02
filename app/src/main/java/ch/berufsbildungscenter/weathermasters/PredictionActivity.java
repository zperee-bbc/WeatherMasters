package ch.berufsbildungscenter.weathermasters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class PredictionActivity extends AppCompatActivity implements ActionBar.TabListener {

    private static final String LOG_TAG = PredictionActivity.class.getCanonicalName();
    Dialog dialog;
    private String ortschaft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_prediction);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(R.string.orte).setTabListener(this), false);
        actionBar.addTab(actionBar.newTab().setText(R.string.aktuell).setTabListener(this), false);
        actionBar.addTab(actionBar.newTab().setText(R.string.vorhersage).setTabListener(this), true);
        actionBar.setHomeButtonEnabled(false);

        dialog = ProgressDialog.show(this, "Lade Informationen", "Bitte warten...");
        JSonLoadingTaskPrediction loadingTask = new JSonLoadingTaskPrediction(this);
        Intent intent = getIntent();

        ortschaft = intent.getStringExtra("stadt");

        TextView textViewOrtschaft = (TextView) findViewById(R.id.textViewWetterOrt);
        textViewOrtschaft.setText(ortschaft);
        loadingTask.execute(ortschaft);


    }

    public void setData(Vorhersage vorhersage){
        WeatherPrediction_adapter weatherPrediction_adapter = new WeatherPrediction_adapter(this, R.id.weatherPredictionItem, vorhersage.getWetterArrayList());
        ListView prediction_listView = (ListView) findViewById(R.id.prediction_listView);
        prediction_listView.setAdapter(weatherPrediction_adapter);

        TextView standort = (TextView) findViewById(R.id.textViewWetterOrt);
        standort.setText(vorhersage.getWetterArrayList().get(0).getStandort().getStadt());

        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_details, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 1) {
            Log.i(LOG_TAG, "Aktuell");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (tab.getPosition() == 0) {
            Intent intent = new Intent(this, Favorite_cities.class);
            startActivity(intent);
            Log.i(LOG_TAG, "Orte");
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
