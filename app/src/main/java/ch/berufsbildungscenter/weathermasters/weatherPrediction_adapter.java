package ch.berufsbildungscenter.weathermasters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class WeatherPrediction_adapter extends ArrayAdapter<Wetter> {
    private static final String LOG_TAG = WeatherPrediction_adapter.class.getCanonicalName();
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
        loadImage(wetter.getIcon(), imgView);

        TextView datum = (TextView) rowView.findViewById(R.id.textViewDatum);
        datum.setText(wetter.getDatum());
        return rowView;
    }

    public void loadImage(String stringBuilderIcon, ImageView imageView) {
        if (stringBuilderIcon.equals("01d")) {
            imageView.setImageResource(R.drawable.day_clear);
        } else if (stringBuilderIcon.equals("02d")) {
            imageView.setImageResource(R.drawable.day_few_clouds);
        } else if (stringBuilderIcon.equals("03d")) {
            imageView.setImageResource(R.drawable.day_scattered_clouds);
        } else if (stringBuilderIcon.equals("04d")) {
            imageView.setImageResource(R.drawable.day_broken_clouds);
        } else if (stringBuilderIcon.equals("09d")) {
            imageView.setImageResource(R.drawable.day_shower_rain);
        } else if (stringBuilderIcon.equals("10d")) {
            imageView.setImageResource(R.drawable.day_rain);
        } else if (stringBuilderIcon.equals("11d")) {
            imageView.setImageResource(R.drawable.day_thunderstorm);
        } else if (stringBuilderIcon.equals("13d")) {
            imageView.setImageResource(R.drawable.day_snow);
        } else if (stringBuilderIcon.equals("50d")) {
            imageView.setImageResource(R.drawable.day_mist);
        } else if (stringBuilderIcon.equals("01n")) {
            imageView.setImageResource(R.drawable.night_clear);
        } else if (stringBuilderIcon.equals("02n")) {
            imageView.setImageResource(R.drawable.night_few_clouds);
        } else if (stringBuilderIcon.equals("03n")) {
            imageView.setImageResource(R.drawable.night_scattered_clouds);
        } else if (stringBuilderIcon.equals("04n")) {
            imageView.setImageResource(R.drawable.night_broken_clouds);
        } else if (stringBuilderIcon.equals("09n")) {
            imageView.setImageResource(R.drawable.night_shower_rain);
        } else if (stringBuilderIcon.equals("10n")) {
            imageView.setImageResource(R.drawable.night_rain);
        } else if (stringBuilderIcon.equals("11n")) {
            imageView.setImageResource(R.drawable.night_thunderstorm);
        } else if (stringBuilderIcon.equals("13n")) {
            imageView.setImageResource(R.drawable.night_snow);
        } else if (stringBuilderIcon.equals("50n")) {
            imageView.setImageResource(R.drawable.night_mist);
        } else {
            Log.i(LOG_TAG, "Kein Icon gefunden");
        }
    }
}
