package ch.berufsbildungscenter.weathermasters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class JSonLoadingTaskPrediction extends AsyncTask<String, Void, Vorhersage> {

    Intent intent = new Intent();

    private String position = intent.getStringExtra("position");
    private URL url;

    private static final String LOG_TAG = JSonLoadingTaskPrediction.class.getCanonicalName();

    private final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?units=metric&lang=de&q=";
    private Context mContext = null;

    private PredictionActivity activity;

    public JSonLoadingTaskPrediction(PredictionActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Vorhersage doInBackground(String... params) {
        Vorhersage vorhersage = null;
        String pos = params[0];
        HttpURLConnection connection = null;

        if (isNetworkConnectionAvailable()) {
            try {
                    url = new URL(String.format(API_URL + pos));
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    if (HttpURLConnection.HTTP_OK == responseCode) {
                        vorhersage = parseData(connection.getInputStream());
                    } else {
                        Log.e(LOG_TAG, String.format("Ein Fehler ist aufgetreten. Service nicht verfugbar.", responseCode));
                    }

            } catch (Exception e) {
                Log.e(LOG_TAG, "Ein Fehler ist aufgetreten", e);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle(R.string.networkTitle);
                alertDialog.setMessage(R.string.error);
                alertDialog.setIcon(R.mipmap.network);
            } finally {
                connection.disconnect();
            }
        } else {
            Log.e(LOG_TAG, "Keine Internetverbindung!");
            Toast.makeText(activity, "Keine Internetverbindung", Toast.LENGTH_LONG);
        }
        return vorhersage;
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }

    private Vorhersage parseData(InputStream inputStream) throws IOException, JSONException {


        String input = readInput(inputStream);
        JSONObject data = new JSONObject(input);
        int i = 0;
        Vorhersage vorhersage = new Vorhersage();
        ArrayList<Wetter> arrayListWetter = new ArrayList<Wetter>();
        while(i < 5){
            Wetter wetter = new Wetter();
            JSONArray arrayList = data.getJSONArray("list");
            JSONObject wetterData = new JSONObject(arrayList.get(i).toString());
            JSONObject dayDataMain = wetterData.getJSONObject("main");
            wetter.setTemperatur(dayDataMain.getDouble("temp"));


            JSONArray arrayListData = wetterData.getJSONArray("weather");
            JSONObject dayDataWeather = new JSONObject(arrayListData.get(0).toString());
            wetter.setBeschreibung(dayDataWeather.getString("description"));
            wetter.setIcon(dayDataWeather.getString("icon"));

            arrayListWetter.add(wetter);
            i++;
        }
        vorhersage.setWetterArrayList(arrayListWetter);

        return vorhersage;
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

    protected void onPostExecute(Vorhersage vorhersage) {
        if (null == vorhersage) {
            //Errors
        } else {
            activity.setData(vorhersage);
        }
    }
}
