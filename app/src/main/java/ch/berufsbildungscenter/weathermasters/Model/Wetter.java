package ch.berufsbildungscenter.weathermasters.Model;

import java.util.HashMap;

import ch.berufsbildungscenter.weathermasters.R;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class Wetter{
    private Standort standort;
    private String beschreibung;
    private String icon;
    private double temperatur;
    private String datum;

    public double getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(double temperatur) {
        this.temperatur = temperatur;
    }

    public Standort getStandort() {
        return standort;
    }

    public void setStandort(Standort standort) {
        this.standort = standort;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }


    public int loadImage(String imageName) {
        HashMap iconMap = new HashMap<String, Integer>();
        iconMap.put("01d", R.drawable.day_clear);
        iconMap.put("02d", R.drawable.day_few_clouds);
        iconMap.put("03d", R.drawable.day_scattered_clouds);
        iconMap.put("04d", R.drawable.day_broken_clouds);
        iconMap.put("09d", R.drawable.day_shower_rain);
        iconMap.put("10d", R.drawable.day_rain);
        iconMap.put("11d", R.drawable.day_thunderstorm);
        iconMap.put("13d", R.drawable.day_snow);
        iconMap.put("50d", R.drawable.day_mist);
        iconMap.put("01n", R.drawable.night_clear);
        iconMap.put("02n", R.drawable.night_few_clouds);
        iconMap.put("03n", R.drawable.night_scattered_clouds);
        iconMap.put("04n", R.drawable.night_broken_clouds);
        iconMap.put("09n", R.drawable.night_shower_rain);
        iconMap.put("10n", R.drawable.night_rain);
        iconMap.put("11n", R.drawable.night_thunderstorm);
        iconMap.put("13n", R.drawable.night_snow);
        iconMap.put("50n", R.drawable.night_mist);

        return (int) iconMap.get(imageName);
    }
}
