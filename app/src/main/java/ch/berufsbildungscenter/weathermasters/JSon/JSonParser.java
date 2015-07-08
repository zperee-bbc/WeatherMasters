package ch.berufsbildungscenter.weathermasters.JSon;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ch.berufsbildungscenter.weathermasters.Model.AktuellesWetter;
import ch.berufsbildungscenter.weathermasters.Alert.CustomDialog;
import ch.berufsbildungscenter.weathermasters.R;
import ch.berufsbildungscenter.weathermasters.Model.Standort;
import ch.berufsbildungscenter.weathermasters.Model.Vorhersage;
import ch.berufsbildungscenter.weathermasters.Model.Wetter;

/**
 * Created by zperee on 02.07.2015.
 */
public class JSonParser {

    private CustomDialog customDialog = new CustomDialog();
    private Context context;

    protected Vorhersage getVorhersage(String jsonString) {

        Vorhersage vorhersage = new Vorhersage();
        try {
            JSONObject data = new JSONObject(jsonString);
            int i = 0;
            ArrayList<Wetter> arrayListWetter = new ArrayList<Wetter>();
            while (i < 5) {
                Wetter wetter = new Wetter();
                JSONArray arrayList = data.getJSONArray("list");
                JSONObject wetterData = new JSONObject(arrayList.get(i).toString());
                JSONObject dayDataMain = wetterData.getJSONObject("temp");
                wetter.setTemperatur(dayDataMain.getDouble("day"));
                JSONArray arrayListData = wetterData.getJSONArray("weather");
                JSONObject dayDataWeather = new JSONObject(arrayListData.get(0).toString());
                wetter.setBeschreibung(dayDataWeather.getString("description"));
                wetter.setIcon(dayDataWeather.getString("icon"));
                wetter.setDatum(wetterData.getString("dt"));
                JSONObject cityInfo = data.getJSONObject("city");
                JSONObject coordinaten = cityInfo.getJSONObject("coord");
                Standort standortVorhersage = new Standort();
                standortVorhersage.setStadt(cityInfo.getString("name"));
                standortVorhersage.setCityId(cityInfo.getInt("id"));
                standortVorhersage.setLatitude(coordinaten.getDouble("lat"));
                standortVorhersage.setLongitude(coordinaten.getDouble("lon"));
                wetter.setStandort(standortVorhersage);
                arrayListWetter.add(wetter);

                i++;
                vorhersage.setWetterArrayList(arrayListWetter);
            }
        }catch(JSONException e){
            customDialog.displayAlertDialog(context, R.string.warnung, R.string.serviceUnavailable, R.mipmap.warning);
        }
        return vorhersage;
    }

    protected AktuellesWetter getAktuellesWetter(String jsonString) {
            AktuellesWetter aktuellesWetter = new AktuellesWetter();
            try {
                JSONObject data = new JSONObject(jsonString);
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
            } catch (JSONException e) {
                customDialog.displayAlertDialog(context, R.string.warnung, R.string.serviceUnavailable, R.mipmap.warning);
            }
            return aktuellesWetter;
        }
    }
