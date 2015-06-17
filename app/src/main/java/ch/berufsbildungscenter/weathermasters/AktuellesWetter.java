package ch.berufsbildungscenter.weathermasters;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class AktuellesWetter extends Wetter{
    private double luftfaeuchtigkeit;
    private double luftdruck;

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
}
