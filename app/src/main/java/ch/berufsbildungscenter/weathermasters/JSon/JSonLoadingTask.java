package ch.berufsbildungscenter.weathermasters.JSon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.berufsbildungscenter.weathermasters.Alert.CustomDialog;
import ch.berufsbildungscenter.weathermasters.R;

/**
 * Created by zperee on 02.07.2015.
 */
public abstract class JSonLoadingTask extends AsyncTask<String, Void, String> {

    private URL url;
    protected JSonParser jSonParser;
    private CustomDialog customDialog;
    private static final String LOG_TAG = JSonLoadingTask.class.getCanonicalName();
    private Context mContext = null;
    private Activity activity;
    protected String Api_Url = "";

    public JSonLoadingTask(Activity activity) {
        this.activity = activity;
        jSonParser = new JSonParser();
        customDialog = new CustomDialog();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String pos = params[0];
        HttpURLConnection connection = null;

        if (isNetworkConnectionAvailable()) {
            try {

                url = new URL(String.format(Api_Url +  pos.replaceAll(" ", "")));
                url = new URL(String.format(Api_Url + pos.replaceAll(" ", "")));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = readInput(connection.getInputStream());
                } else {

                    Log.e(LOG_TAG, String.format("Ein Fehler ist aufgetreten. Service nicht verfugbar.", responseCode));

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle(R.string.errorTitle);
                    alertDialog.setMessage(R.string.serviceUnavailable);
                    alertDialog.setIcon(R.mipmap.warning);
                }

            } catch (Exception e) {
                customDialog.displayAlertDialog(mContext, R.string.networkTitle, R.string.error, R.mipmap.network);
            } finally {
                connection.disconnect();
            }
        } else {
            customDialog.displayAlertDialog(mContext, R.string.networkTitle, R.string.noConnection, R.mipmap.network);
        }
        return result;
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
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

    protected abstract void onCostumePostExecute(String jsonString);

    protected void onPostExecute(String jsonString) {
        if (null == jsonString) {
            //ToDo Better Exception Handling
        } else {
            onCostumePostExecute(jsonString);
        }
    }


}
