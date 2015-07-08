package ch.berufsbildungscenter.weathermasters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ch.berufsbildungscenter.weathermasters.Model.Wetter;

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
        TextView temp = (TextView) rowView.findViewById(R.id.textView_temp);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        temp.setText("" + Double.valueOf(decimalFormat.format(wetter.getTemperatur())) + " \u00B0C");

        TextView description = (TextView) rowView.findViewById(R.id.description_text);
        description.setText(wetter.getBeschreibung());

        ImageView imgView = (ImageView) rowView.findViewById(R.id.icon_imageView);
        int wetterIcon = wetter.loadImage(wetter.getIcon());
        imgView.setImageResource(wetterIcon);


        TextView datum = (TextView) rowView.findViewById(R.id.textViewDatum);

        long timestamp = Long.parseLong(wetter.getDatum()) * 1000;
        datum.setText(getDate(timestamp));

        return rowView;
    }

    private CharSequence getDate(long timeStamp) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }
}
