package ch.berufsbildungscenter.weathermasters;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class Wetter{
    private Standort standort;
    private String beschreibung;
    private String icon;
    private Temperatur temperatur;
    private Wind wind;
    private String datum;

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

    public Temperatur getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(Temperatur temperatur) {
        this.temperatur = temperatur;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
