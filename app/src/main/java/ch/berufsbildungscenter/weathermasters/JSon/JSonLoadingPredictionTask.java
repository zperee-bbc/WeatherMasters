package ch.berufsbildungscenter.weathermasters.JSon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ch.berufsbildungscenter.weathermasters.PredictionActivity;
import ch.berufsbildungscenter.weathermasters.Model.Vorhersage;

/**
 * Created by zperee on 02.07.2015.
 */
public class JSonLoadingPredictionTask extends JSonLoadingTask {

    private PredictionActivity predictionActivity;
    private Activity activity;

    public JSonLoadingPredictionTask(Activity activity) {
    super(activity);
        this.activity = activity;
        this.predictionActivity = (PredictionActivity)activity;

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(activity);

        String item = prefs.getString("example_list", "metric");
        if (item.equals("metric"))
        {
            apiUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?units=metric&lang=de&q=";
        } else {
            apiUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?units=imperial&lang=de&q=";
        }


    }

    @Override
    protected void onCostumePostExecute(String jsonString) {
        Vorhersage vorhersage = jSonParser.getVorhersage(jsonString);
        predictionActivity.setData(vorhersage);
    }
}
