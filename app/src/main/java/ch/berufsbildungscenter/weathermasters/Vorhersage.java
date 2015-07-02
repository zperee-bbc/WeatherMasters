package ch.berufsbildungscenter.weathermasters;

import java.util.ArrayList;

/**
 * Created by zmartl on 24.06.2015.
 * Version ${VERSION}
 */
public class Vorhersage extends Wetter {
    private ArrayList<Wetter> wetterArrayList;

    public ArrayList<Wetter> getWetterArrayList() {
        return wetterArrayList;
    }

    public void setWetterArrayList(ArrayList<Wetter> wetterArrayList) {
        this.wetterArrayList = wetterArrayList;
    }
}
