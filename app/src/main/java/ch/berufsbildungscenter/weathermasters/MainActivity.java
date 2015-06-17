package ch.berufsbildungscenter.weathermasters;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Timestamp;
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

        for(AktuellesWetter aktuellesWetter : result){
            sb.append(aktuellesWetter.toString());
            sb.append("\n\n");
        }

        TextView dataView = (TextView) findViewById(R.id.textViewDetail);
        dataView.setText(sb.toString());

        TextView ortschaftView = (TextView) findViewById(R.id.textViewOrtschaft);
        ortschaftView.setText("Uster, Zh");

        int time = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(time);

        TextView timeStamp = (TextView) findViewById(R.id.textViewAktualisiert);
        timeStamp.setText("Zuletzt aktualisiert: " + tsTemp.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
