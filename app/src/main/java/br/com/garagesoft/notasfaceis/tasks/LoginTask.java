package br.com.garagesoft.notasfaceis.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.garagesoft.notasfaceis.NotasFaceis;
import br.com.garagesoft.notasfaceis.models.Usuario;

/**
 * Created by Luiz on 16/07/2015.
 */
public class LoginTask extends AsyncTask<Usuario, Integer, Usuario> {

    private Context context;

    private List<TaskListener<Usuario>> listeners = new ArrayList<TaskListener<Usuario>>(0);

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected Usuario doInBackground(Usuario... params) {
        Usuario usuario = null;
        if(params.length>0) {
            usuario = params[0];
        }
        String host = ((NotasFaceis)context.getApplicationContext()).host;
        return usuario;
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        super.onPostExecute(usuario);
    }

    public void addTaskListener(TaskListener<Usuario> listener) {
        listeners.add(listener);
    }

    public void removeTaskListener(TaskListener<Usuario> listener) {
        listeners.remove(listener);
    }
}
