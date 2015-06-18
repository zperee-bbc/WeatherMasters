package ch.berufsbildungscenter.weathermasters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
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

        dialog = ProgressDialog.show(this, "Lade Informationen,", "Bitte warten...");
        JSonLoadingTask loadingTask = new JSonLoadingTask(this);
        loadingTask.execute(String.valueOf("Uster,CH"));
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    public void setData(List<AktuellesWetter> result) {

        StringBuilder sb = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderTemp = new StringBuilder();
        StringBuilder stringBuilderIcon = new StringBuilder();

        for(AktuellesWetter aktuellesWetter : result){
            sb.append(aktuellesWetter.toString());
            sb.append("\n\n");
        }

        for (AktuellesWetter aktuellesWetter : result){
            stringBuilder.append(aktuellesWetter.descriptionToString());
        }

        for (AktuellesWetter aktuellesWetter : result){
            stringBuilderTemp.append(aktuellesWetter.tempToString());
        }

        for (AktuellesWetter aktuellesWetter : result) {
            stringBuilderIcon.append(aktuellesWetter.getIconPath());
        }

        TextView dataView = (TextView) findViewById(R.id.textViewDetail);
        dataView.setText(sb.toString());

        TextView ortschaftView = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaftView.setText("Uster, Zh");

        ImageView imgView = (ImageView) findViewById(R.id.imageViewWetter);
        if (stringBuilderIcon.toString().equals("01d")) {
            imgView.setImageResource(R.drawable.day_broken_clouds);
        } else {
            imgView.setImageResource(R.drawable.day_broken_clouds);
        }

        TextView temp = (TextView) findViewById(R.id.textViewTemperatur);
        temp.setText(stringBuilderTemp.toString());

        TextView description = (TextView) findViewById(R.id.textViewBeschreibung);
        description.setText(stringBuilder.toString());

        Time now = new Time();
        now.setToNow();
        TextView time = (TextView) findViewById(R.id.textViewAktualisiert);
        time.setText("Zuletzt aktualisiert: " + now.format("%d.%m.%Y") +  " | " + now.format("%k:%M:%S"));

        dialog.dismiss();

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
        if (id == R.id.action_settings) {
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
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
