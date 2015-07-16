package br.com.garagesoft.notasfaceis;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.garagesoft.notasfaceis.models.Usuario;
import br.com.garagesoft.notasfaceis.tasks.LoginTask;
import br.com.garagesoft.notasfaceis.tasks.TaskListener;


public class LoginActivity extends ActionBarActivity {

    private LoginFragment loginFragment;

    private CadastroFragment cadastroFragment;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeFragment(getLoginFragment());
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

    public void btnEntrarClick(View view) {
        progressDialog.setMessage("Entrando...");
        progressDialog.show();
        LoginTask loginTask = new LoginTask(this);
        loginTask.addTaskListener(new TaskListener<Usuario>() {
            @Override
            public void taskCompleted(Usuario result) {
                result.save();
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etSenha = (EditText) findViewById(R.id.etSenha);
        Usuario usuario = Usuario.findByemail("luizbag@gmail.com");
        if (usuario == null) {
            usuario = new Usuario();
            //usuario.setEmail(etEmail.getText().toString());
            usuario.setEmail("luizbag@gmail.com");
        }
        //usuario.setSenha(etSenha.getText().toString());
        usuario.setSenha("luizbag");
        loginTask.execute(usuario);
    }

    public void btnNovoCadastroClick(View view) {
        changeFragment(getCadastroFragment());
    }

    public void btnCadastrarClick(View view) {

    }

    public void btnVoltarClick(View view) {
        changeFragment(getLoginFragment());
    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
    }

    public LoginFragment getLoginFragment() {
        if (loginFragment == null)
            loginFragment = new LoginFragment();
        return loginFragment;
    }

    public CadastroFragment getCadastroFragment() {
        if (cadastroFragment == null)
            cadastroFragment = new CadastroFragment();
        return cadastroFragment;
    }
}
