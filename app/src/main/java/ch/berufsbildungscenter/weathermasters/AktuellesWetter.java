package ch.berufsbildungscenter.weathermasters;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class AktuellesWetter extends Wetter{
    private double luftfaeuchtigkeit;
    private double luftdruck;
    private double temp;

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

    @Override
    public String toString(){
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append("Luftfeuchtigkeit: ");
        resultBuilder.append(luftfaeuchtigkeit + " %");
        resultBuilder.append("\n");

        resultBuilder.append("Luftdruck: ");
        resultBuilder.append(luftdruck + " hPa");
        resultBuilder.append("\n");

        resultBuilder.append("Temperatur: ");
        resultBuilder.append(temp + " \u00B0C");
        resultBuilder.append("\n");

        return resultBuilder.toString();
    }
}
