package br.com.garagesoft.notasfaceis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.garagesoft.notasfaceis.models.Usuario;
import br.com.garagesoft.notasfaceis.tasks.CadastroAsyncTask;
import br.com.garagesoft.notasfaceis.tasks.TaskListener;


public class CadastroActivity extends ActionBarActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void btnCadastrarClick(View view) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etSenha = (EditText) findViewById(R.id.etSenha);
        EditText etConfirmaSenha = (EditText) findViewById(R.id.etConfirmaSenha);

        if (etSenha.getText().toString().equals(etConfirmaSenha.getText().toString())) {
            progressDialog.setMessage("Cadastrando...");
            progressDialog.show();

            Usuario usuario = new Usuario();
            usuario.setEmail(etEmail.getText().toString());
            usuario.setPassword(etSenha.getText().toString());
            String baseUrl = ((NotasFaceis) getApplicationContext()).BASE_URL;
            CadastroAsyncTask cadastroAsyncTask = new CadastroAsyncTask(baseUrl);
            cadastroAsyncTask.addCadastroTaskListener(new TaskListener<Usuario>() {
                @Override
                public void taskCompleted(Usuario usuario) {
                    progressDialog.dismiss();
                    if (usuario != null) {
                        new AlertDialog.Builder(getBaseContext())
                                .setMessage("Sucesso!")
                                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(getBaseContext())
                                .setMessage("Falhou...")
                                .setNeutralButton(R.string.ok, null)
                                .show();
                    }
                }
            });
            cadastroAsyncTask.execute(usuario);
        } else {
            etConfirmaSenha.setError("Senhas Diferentes");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
