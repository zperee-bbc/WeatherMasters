package ch.berufsbildungscenter.weathermasters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class JSonLoadingTask extends AsyncTask<String, Void, List<AktuellesWetter>> {

    private static final String LOG_TAG = JSonLoadingTask.class.getCanonicalName();

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&APPID=4095f36f60ae167242be033e0c55ca5c&lang=de&q=Uster,CH";

    private MainActivity activity;

    public JSonLoadingTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected List<AktuellesWetter> doInBackground(String... params) {
        List<AktuellesWetter> result = null;

        String ortschaft = params[0].toString();


        if (isNetworkConnectionAvailable()){
            try {
                URL url = new URL(String.format(API_URL));

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();

                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = parseData(connection.getInputStream());
                } else {
                    Log.e(LOG_TAG, String.format("Ein Fehler ist aufgetreten. Bitte Internetverbindung uberprufen! HTTP status: %d", responseCode));
                }
                connection.disconnect();

            }catch (Exception e) {
                Log.e(LOG_TAG, "Ein Fehler ist aufgetreten", e);
            }

        } else {
            Log.e(LOG_TAG, "Fehler!");
            Toast.makeText(activity, "Fehler", Toast.LENGTH_LONG);
        }

        return result;
    }

    private boolean isNetworkConnectionAvailable() {
        //ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();

        //return null != networkInfo && networkInfo.isConnected();
        return true;
    }

    private List<AktuellesWetter> parseData(InputStream inputStream) throws IOException, JSONException {

        List<AktuellesWetter> result = new ArrayList<AktuellesWetter>();

        String input = readInput(inputStream);
        JSONObject data = new JSONObject(input);
        JSONObject wetterData = data.getJSONObject("main");

        AktuellesWetter aktuellesWetter = new AktuellesWetter();
        aktuellesWetter.setLuftdruck(wetterData.getDouble("pressure"));
        aktuellesWetter.setLuftfaeuchtigkeit(wetterData.getDouble("humidity"));
        aktuellesWetter.setTemp(wetterData.getDouble("temp"));

        result.add(aktuellesWetter);

        return result;
    }

    private String readInput(InputStream inputStream) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line;
        while (null != (line = bufferedReader.readLine())) {
            resultBuilder.append(line);
        }

        return resultBuilder.toString();
    }

    @Override
    protected void onPostExecute(List<AktuellesWetter> result) {
        if (null == result) {
            activity.displayLoadingDataFailedError();

        } else {
            activity.setData(result);
        }

    }



}
