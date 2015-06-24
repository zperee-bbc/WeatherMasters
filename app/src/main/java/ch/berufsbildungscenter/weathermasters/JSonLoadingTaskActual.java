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

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class JSonLoadingTaskActual extends AsyncTask<String, Void, AktuellesWetter> {

    Intent intent = new Intent();

    private String position = intent.getStringExtra("position");

    private static final String LOG_TAG = JSonLoadingTaskActual.class.getCanonicalName();
    private final String API_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&lang=de&";
    private Context mContext = null;

    private MainActivity activity;

    public JSonLoadingTaskActual(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected AktuellesWetter doInBackground(String... params) {
        AktuellesWetter aktuellesWetter = null;
        String pos = params[0];
        HttpURLConnection connection = null;

        if (isNetworkConnectionAvailable()) {
            try {
                URL url = new URL(String.format(API_URL+pos));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (HttpURLConnection.HTTP_OK == responseCode) {
                    aktuellesWetter = parseData(connection.getInputStream());
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
        return aktuellesWetter;
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }

    private AktuellesWetter parseData(InputStream inputStream) throws IOException, JSONException {

        AktuellesWetter aktuellesWetter = new AktuellesWetter();

        String input = readInput(inputStream);
        JSONObject data = new JSONObject(input);
        String name = data.getString("name");
        JSONObject wetterData = data.getJSONObject("main");

        aktuellesWetter.setLuftdruck(wetterData.getDouble("pressure"));
        aktuellesWetter.setLuftfaeuchtigkeit(wetterData.getDouble("humidity"));
        aktuellesWetter.setTemp(wetterData.getDouble("temp"));

        aktuellesWetter.setStandort(new Standort());
        aktuellesWetter.getStandort().setStadt(name);

        JSONArray arrayList = data.getJSONArray("weather");
        JSONObject weatherArray = new JSONObject(arrayList.get(0).toString());

        aktuellesWetter.setBeschreibung(weatherArray.getString("description"));
        aktuellesWetter.setIcon(weatherArray.getString("icon"));

        return aktuellesWetter;
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
    protected void onPostExecute(AktuellesWetter aktuellesWetter) {
        if (null == aktuellesWetter) {
            activity.displayLoadingDataFailedError();
        } else {
            activity.setData(aktuellesWetter);
        }
    }
}
