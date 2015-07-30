package br.com.garagesoft.notasfaceis.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
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
        if (params.length > 0) {
            usuario = params[0];
        }
        String host = ((NotasFaceis) context.getApplicationContext()).host;
        String resourceName = "login";
        RESTClient<Usuario> restClient = new RESTClient(host, resourceName, Usuario.class);
        try {
            String token = restClient.post(usuario);
            usuario.setToken(token);
            ((NotasFaceis)context.getApplicationContext()).token = token;
        } catch (IOException e) {
            Log.e("LoginTask", e.getMessage(), e);
            usuario = null;
        }
        return usuario;
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        super.onPostExecute(usuario);
        for (TaskListener<Usuario> listener : listeners) {
            listener.taskCompleted(usuario);
        }
    }

    public void addTaskListener(TaskListener<Usuario> listener) {
        listeners.add(listener);
    }

    public void removeTaskListener(TaskListener<Usuario> listener) {
        listeners.remove(listener);
    }
}
