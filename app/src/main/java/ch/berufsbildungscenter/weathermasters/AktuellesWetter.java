package ch.berufsbildungscenter.weathermasters;

import java.text.DecimalFormat;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class AktuellesWetter extends Wetter{
    private double luftfaeuchtigkeit;
    private double luftdruck;
    private double temp;
    private StringBuilder resuBuilder;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getLuftfaeuchtigkeit() {
        return luftfaeuchtigkeit;
    }

    public void setLuftfaeuchtigkeit(double luftfaeuchtigkeit) {
        this.luftfaeuchtigkeit = luftfaeuchtigkeit;
    }

    public double getLuftdruck() {
        return luftdruck;
    }

    public void setLuftdruck(double luftdruck) {
        this.luftdruck = luftdruck;
    }

    public String detailToString(){
        resuBuilder = new StringBuilder();
        resuBuilder.append("Luftfeuchtigkeit: ");
        resuBuilder.append(luftfaeuchtigkeit + "%");
        resuBuilder.append("\n");
        resuBuilder.append("Luftdruck: ");
        resuBuilder.append(luftdruck + " hPa");
        resuBuilder.append("\n");

        return resuBuilder.toString();
    }

    public String descriptionToString(){
        resuBuilder = new StringBuilder();
        resuBuilder.append(getBeschreibung());
        return resuBuilder.toString();
    }

    public String tempToString(){
        resuBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        resuBuilder.append(Double.valueOf(decimalFormat.format(temp)) + " \u00B0C");
        return resuBuilder.toString();
    }

    public String getIconPath(){
        resuBuilder = new StringBuilder();
        resuBuilder.append(getIcon());
        return resuBuilder.toString();
    }
}
