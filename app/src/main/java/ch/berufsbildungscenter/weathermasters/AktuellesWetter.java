package ch.berufsbildungscenter.weathermasters;

/**
 * Created by zmartl on 17.06.2015.
 * Version ${VERSION}
 */
public class AktuellesWetter extends Wetter{
    private double luftfaeuchtigkeit;
    private double luftdruck;
    private double temp;
    private StringBuilder resuBuilder;
    private int iconInt;

    public int getIconInt() {
        return iconInt;
    }

    public void setIconInt(int iconInt) {
        this.iconInt = iconInt;
    }

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
        resultBuilder.append(luftfaeuchtigkeit + "%");
        resultBuilder.append("\n");

        resultBuilder.append("Luftdruck: ");
        resultBuilder.append(luftdruck + " hPa");
        resultBuilder.append("\n");

        return resultBuilder.toString();
    }

    public String descriptionToString(){
        resuBuilder = new StringBuilder();
        resuBuilder.append(getBeschreibung());
        return resuBuilder.toString();
    }

    public String tempToString(){
        resuBuilder = new StringBuilder();
        resuBuilder.append(temp + " \u00B0C");
        return resuBuilder.toString();
    }

    public int catchIconAndGetIconPath(){
        resuBuilder = new StringBuilder();
        if (getIcon().equals("04d")){
            setIconInt(R.drawable.day_broken_clouds);
        }
        return getIconInt();
    }




}
