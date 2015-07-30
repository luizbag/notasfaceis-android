package br.com.garagesoft.notasfaceis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.garagesoft.notasfaceis.models.Usuario;
import br.com.garagesoft.notasfaceis.tasks.LoginAsyncTask;
import br.com.garagesoft.notasfaceis.tasks.TaskListener;


public class LoginActivity extends Activity {

    private ProgressDialog progressDialog;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tvNovoUsuarioClick(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void btnEntrarClick(View view) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.setMessage("Entrando...");
        progressDialog.show();
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etSenha = (EditText) findViewById(R.id.etSenha);

        usuario = new Usuario();
        usuario.setEmail("luizbag@gmail.com");//etEmail.getText().toString());
        usuario.setPassword("luizbag");//etSenha.getText().toString());
        String baseUrl = ((NotasFaceis) getApplicationContext()).BASE_URL;
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(baseUrl);
        loginAsyncTask.addLoginTaskListener(new TaskListener<String>() {
            @Override
            public void taskCompleted(String token) {
                loginFeito(token);
            }
        });
        loginAsyncTask.execute(usuario);
    }

    public void loginFeito(String token) {
        progressDialog.dismiss();
        if (token != null && !token.equals("Unauthorized")) {
            usuario.setToken(token);
            usuario.save();

            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getBaseContext())
                    .setMessage("Usuario inválido")
                    .create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
