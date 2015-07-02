package ch.berufsbildungscenter.weathermasters;

import android.app.Activity;

/**
 * Created by zperee on 02.07.2015.
 */
public class JSonLoadingPredictionTask extends JSonLoadingTask {

    private PredictionActivity predictionActivity;


    public JSonLoadingPredictionTask(Activity activity) {
        super(activity);
        this.predictionActivity = (PredictionActivity)activity;
        Api_Url = "http://api.openweathermap.org/data/2.5/forecast/daily?units=metric&lang=de&q=";
    }

    @Override
    protected void onCostumePostExecute(String jsonString) {
        Vorhersage vorhersage = jSonParser.getVorhersage(jsonString);
        predictionActivity.setData(vorhersage);
    }
}
