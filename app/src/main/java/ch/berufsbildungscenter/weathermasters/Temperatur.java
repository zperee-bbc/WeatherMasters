package ch.berufsbildungscenter.weathermasters;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class Temperatur {
    private double temp;
    private double maxTemp;
    private double minTemp;

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getTemp() {

        return temp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }
}
