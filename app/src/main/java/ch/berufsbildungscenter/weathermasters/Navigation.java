package ch.berufsbildungscenter.weathermasters;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by zperee on 18.06.2015.
 */
public class Navigation extends ActionBarActivity implements ActionBar.TabListener {
    private static final String LOG_TAG = Navigation.class.getCanonicalName();
    protected ActionBar actionBar = null;

    public void createNavigation() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(R.string.orte).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.aktuell).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.vorhersage).setTabListener(this));
        actionBar.setHomeButtonEnabled(false);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 2) {
            Log.i(LOG_TAG, "Vorhersage");
            Intent intent = new Intent(this, Weather_prediction.class);
            startActivity(intent);
        } else if (tab.getPosition() == 1) {
            Log.i(LOG_TAG, "Aktuell");
            Intent intent = new Intent(this, MainActivity.class);
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
