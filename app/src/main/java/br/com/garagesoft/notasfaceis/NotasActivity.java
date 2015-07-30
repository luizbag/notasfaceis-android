package br.com.garagesoft.notasfaceis;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.garagesoft.notasfaceis.models.Caderno;
import br.com.garagesoft.notasfaceis.models.Nota;


public class NotasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        Bundle dados = getIntent().getExtras();

        Caderno caderno = (Caderno) dados.get("caderno");

        List<Nota> notas = Nota.find(Nota.class, "caderno = ?", String.valueOf(caderno.getId()));
        //List<Nota> notas = Lists.newArrayList(Nota.findAll(Nota.class));

        ListView lvNotas = (ListView) findViewById(R.id.lvNotas);
        lvNotas.setAdapter(new ArrayAdapter<Nota>(this, android.R.layout.simple_list_item_1, notas));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notas, menu);
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
