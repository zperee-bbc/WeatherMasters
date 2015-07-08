package ch.berufsbildungscenter.weathermasters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ch.berufsbildungscenter.weathermasters.Alert.CustomDialog;
import ch.berufsbildungscenter.weathermasters.Location.GPSTracker;
import ch.berufsbildungscenter.weathermasters.Model.Standort;


public class Favorite_cities extends AppCompatActivity implements ActionBar.TabListener {
    private static final String LOG_TAG = Favorite_cities.class.getCanonicalName();
    public static final String FAVORITECITIES = "FavoriteCities";
    public static final String WETTERDATA = "WetterData";
    private ListView citiesListView;
    private ArrayAdapter citiesArrayAdapter;
    private List<String> cities = null;
    Activity activity = this;
    private SharedPreferences.Editor editor;
    private SharedPreferences favoriteCities;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cities);

        loadListView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(R.string.orte).setTabListener(this), true);
        actionBar.addTab(actionBar.newTab().setText(R.string.aktuell).setTabListener(this), false);
        actionBar.addTab(actionBar.newTab().setText(R.string.vorhersage).setTabListener(this), false);
        actionBar.setHomeButtonEnabled(false);

    }

    private void displayList(){
        TextView noCity = (TextView) findViewById(R.id.noCity);
        for(String cities2 : cities) {
            citiesArrayAdapter.add(cities2);
            citiesListView.setAdapter(citiesArrayAdapter);
            noCity.setText("");
        }
        if(cities.size() == 0){
            citiesListView.setAdapter(citiesArrayAdapter);
            noCity.setText("Keine Favoriten vorhanden");
        }
    }

    public void loadListView() {
        favoriteCities = getSharedPreferences(FAVORITECITIES, 0);
        final Map<String, ?> citesList = favoriteCities.getAll();
        cities = new ArrayList<String>((Collection<? extends String>) citesList.values());
        citiesListView = (ListView)findViewById(R.id.favoriteCities);

        citiesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        displayList();

        citiesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle(R.string.removeTitle);
                alertDialog.setMessage("M\u00f6chten Sie " + cities.get(position) + " l\u00f6schen?");
                alertDialog.setIcon(R.mipmap.remove);
                alertDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCity(cities.get(position));
                        loadListView();
                    }
                });
                alertDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

                return true;
            }
        });

        actionListener();
    }

    private void actionListener(){
        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PredictionActivity.class);
                String selected = parent.getItemAtPosition(position).toString();
                if (selected == null){
                    TextView warning = (TextView) findViewById(R.id.warning);
                    warning.setText("Keine Ortschaft ausgewählt");
                    customDialog = new CustomDialog();
                    customDialog.displayAlertDialog(Favorite_cities.this, R.string.warnung, R.string.stadtMessage, R.mipmap.world);
                } else {
                    intent.putExtra("stadt", selected);
                    startActivity(intent);
                }
            }
        };
        citiesListView.setOnItemClickListener(mListClickedHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite_cities, menu);
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
        } else if (tab.getPosition() == 2) {
              Intent intent = new Intent(this, PredictionActivity.class);
            SharedPreferences oldWeatherData = getSharedPreferences(WETTERDATA, 0);
            intent.putExtra("stadt", oldWeatherData.getString("Ortschaft", "Earth"));
            startActivity(intent);
            Log.i(LOG_TAG, "Vorhersage");
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public void addCity(View view) {
        favoriteCities = getSharedPreferences(FAVORITECITIES, 0);
        editor = favoriteCities.edit();

        int i = 0;
        while (favoriteCities.contains("City" + i)){
            i++;
        }
        EditText addCity = (EditText) findViewById(R.id.editSearch);
        editor.putString("City" + i, addCity.getText().toString());
        Log.i(LOG_TAG, "City" + i);
        editor.commit();
        loadListView();
        addCity.setText("");
    }

    public void removeCity(String city){
        SharedPreferences favoriteCities = getSharedPreferences(FAVORITECITIES, 0);
        SharedPreferences.Editor editor = favoriteCities.edit();
        Map<String, ?> allEntries = favoriteCities.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().equals(city)){
                editor.remove(entry.getKey());
                editor.commit();
            }
        }
    }

}
