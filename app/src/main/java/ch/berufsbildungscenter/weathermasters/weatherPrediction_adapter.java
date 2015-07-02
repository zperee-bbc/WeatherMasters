package ch.berufsbildungscenter.weathermasters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        int wetterIcon = wetter.loadImage(wetter.getIcon());
        imgView.setImageResource(wetterIcon);


        TextView datum = (TextView) rowView.findViewById(R.id.textViewDatum);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(wetter.getDatum()));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        datum.setText(date);
        return rowView;
    }
}
