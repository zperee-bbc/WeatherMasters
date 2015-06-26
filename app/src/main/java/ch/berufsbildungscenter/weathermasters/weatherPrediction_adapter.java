package ch.berufsbildungscenter.weathermasters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class WeatherPrediction_adapter extends ArrayAdapter<Wetter> {

    private Context context;
    private Wetter wetter;

    public WeatherPrediction_adapter(Context context, int resource, List<Wetter> wetter) {
        super(context, resource, wetter);
        this.context = context;
    }

    public View getView(int position, View contentView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_weatherprediction, parent, false);

        wetter = this.getItem(position);





        return rowView;
    }
}
