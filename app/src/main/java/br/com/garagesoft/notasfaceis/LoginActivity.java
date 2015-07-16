package br.com.garagesoft.notasfaceis;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class LoginActivity extends ActionBarActivity {

    private LoginFragment loginFragment;

    private CadastroFragment cadastroFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    }

    public void btnNovoCadastroClick(View view) {
        changeFragment(getCadastroFragment());
    }

    public void btnCadastrarClick(View view){

    }

    public void btnVoltarClick(View view) {
        changeFragment(getLoginFragment());
    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
    }

    public LoginFragment getLoginFragment() {
        if(loginFragment == null)
            loginFragment = new LoginFragment();
        return loginFragment;
    }

    public CadastroFragment getCadastroFragment() {
        if(cadastroFragment == null)
            cadastroFragment = new CadastroFragment();
        return cadastroFragment;
    }
}
