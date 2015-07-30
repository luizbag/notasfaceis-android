package br.com.garagesoft.notasfaceis;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.common.collect.Lists;

import java.util.List;

import br.com.garagesoft.notasfaceis.models.Caderno;
import br.com.garagesoft.notasfaceis.models.Nota;
import br.com.garagesoft.notasfaceis.models.Usuario;
import br.com.garagesoft.notasfaceis.tasks.GetCadernosAsyncTask;
import br.com.garagesoft.notasfaceis.tasks.PostCadernoAsyncTask;
import br.com.garagesoft.notasfaceis.tasks.TaskListener;


public class MainActivity extends Activity {

    private List<Caderno> cadernos;

    private ListView lvCadernos;

    private NotasFaceis notasFaceis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notasFaceis = (NotasFaceis) getApplicationContext();
        List<Usuario> usuarioList = Lists.newArrayList(Usuario.findAll(Usuario.class));
        if (usuarioList.size() == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            notasFaceis.TOKEN = usuarioList.get(0).getToken();
            GetCadernosAsyncTask getCadernosAsyncTask = new GetCadernosAsyncTask(notasFaceis.BASE_URL, notasFaceis.TOKEN);
            getCadernosAsyncTask.addGetCadernosTaskListener(new TaskListener<Caderno[]>() {
                @Override
                public void taskCompleted(Caderno[] cadernos) {
                    gotCadernos(cadernos);
                }
            });
            getCadernosAsyncTask.execute(notasFaceis.TOKEN);
        }
    }

    private void gotCadernos(Caderno[] cadernos) {
        for (Caderno caderno : cadernos) {
            List<Caderno> list = Caderno.find(Caderno.class, "id_origem=?", caderno.getIdOrigem());
            if (list.size() > 0) {
                caderno.setId(list.get(0).getId());
            }
            caderno.save();
            Nota[] notas = caderno.getNotas();
            for (Nota nota : notas) {
                List<Nota> notaList = Nota.find(Nota.class, "id_origem=?", nota.getIdOrigem());
                if (notaList.size() > 0)
                    nota.setId(notaList.get(0).getId());
                nota.setCaderno(caderno);
                nota.save();
            }
        }
        this.cadernos = Lists.newArrayList(Caderno.findAll(Caderno.class));
        lvCadernos = (ListView) findViewById(R.id.lvCadernos);
        lvCadernos.setAdapter(new ArrayAdapter<Caderno>(this, android.R.layout.simple_list_item_1, cadernos));
        lvCadernos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvCadernosItemClick(position);
            }
        });
    }

    private void lvCadernosItemClick(int position) {
        Intent intent = new Intent(getBaseContext(), NotasActivity.class);
        intent.putExtra("caderno", cadernos.get(position));
        startActivity(intent);
    }

    public void btnNovoCadernoClick(View view) {
        EditText etNovoCaderno = (EditText) findViewById(R.id.etNovoCaderno);
        Caderno caderno = new Caderno();
        caderno.setNome(etNovoCaderno.getText().toString());
        PostCadernoAsyncTask postCadernoAsyncTask = new PostCadernoAsyncTask(notasFaceis.BASE_URL, notasFaceis.TOKEN);
        postCadernoAsyncTask.addTaskListener(new TaskListener<Caderno>() {
            @Override
            public void taskCompleted(Caderno caderno) {
                caderno.save();
                cadernos = Lists.newArrayList(Caderno.findAll(Caderno.class));
                lvCadernos.setAdapter(new ArrayAdapter<Caderno>(getBaseContext(), android.R.layout.simple_list_item_1, cadernos));
            }
        });
        postCadernoAsyncTask.execute(caderno);
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
