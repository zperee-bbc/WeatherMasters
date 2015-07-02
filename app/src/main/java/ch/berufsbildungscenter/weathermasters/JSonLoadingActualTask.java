package ch.berufsbildungscenter.weathermasters;

import android.app.Activity;

/**
 * Created by zperee on 02.07.2015.
 */
public class JSonLoadingActualTask extends JSonLoadingTask {
    private MainActivity mainActivity;


    public JSonLoadingActualTask(Activity activity) {
        super(activity);
        this.mainActivity = (MainActivity)activity;
        Api_Url = "http://api.openweathermap.org/data/2.5/weather?units=metric&lang=de&";
    }

    @Override
    protected void onCostumePostExecute(String jsonString) {
        AktuellesWetter aktuellesWetter = jSonParser.getAktuellesWetter(jsonString);
        mainActivity.setData(aktuellesWetter);
    }
}
