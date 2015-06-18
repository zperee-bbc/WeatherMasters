package ch.berufsbildungscenter.weathermasters;

import android.app.ActionBar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.Time;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSonLoadingTask loadingTask = new JSonLoadingTask(this);
        loadingTask.execute(String.valueOf("Uster,CH"));
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    public void setData(List<AktuellesWetter> result) {

        StringBuilder sb = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderTemp = new StringBuilder();
        StringBuilder stringBuilderIcon = new StringBuilder();

        for(AktuellesWetter aktuellesWetter : result){
            sb.append(aktuellesWetter.toString());
            sb.append("\n\n");
        }

        for (AktuellesWetter aktuellesWetter : result){
            stringBuilder.append(aktuellesWetter.descriptionToString());
        }

        for (AktuellesWetter aktuellesWetter : result){
            stringBuilderTemp.append(aktuellesWetter.tempToString());
        }

        for (AktuellesWetter aktuellesWetter : result) {
            stringBuilderIcon.append(aktuellesWetter.getIconPath());
        }

        TextView dataView = (TextView) findViewById(R.id.textViewDetail);
        dataView.setText(sb.toString());

        TextView ortschaftView = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaftView.setText("Uster, Zh");

        ImageView imgView = (ImageView) findViewById(R.id.imageViewWetter);
        if (stringBuilderIcon.toString().equals("01d")) {
            imgView.setImageResource(R.drawable.day_broken_clouds);
            imgView.getLayoutParams().height = 500;
            imgView.getLayoutParams().width = 1000;
        }


        TextView timeStamp = (TextView) findViewById(R.id.textViewAktualisiert);
        timeStamp.setText("Zuletzt aktualisiert: ");

        TextView temp = (TextView) findViewById(R.id.textViewTemperatur);
        temp.setText(stringBuilderTemp.toString());

        TextView description = (TextView) findViewById(R.id.textViewBeschreibung);
        description.setText(stringBuilder.toString());

        Time now = new Time();
        now.setToNow();
        TextView time = (TextView) findViewById(R.id.textViewAktualisiert);
        time.setText("Zuletzt aktualisiert: " + now.format("%k:%M:%S"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
