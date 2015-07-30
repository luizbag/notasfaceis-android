package br.com.garagesoft.notasfaceis.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.garagesoft.notasfaceis.models.Usuario;

/**
 * Created by Green01 on 22/07/2015.
 */
public class CadastroAsyncTask extends AsyncTask<Usuario, Integer, Usuario> {

    private String baseUrl;

    private List<TaskListener<Usuario>> listeners;

    public CadastroAsyncTask(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void addCadastroTaskListener(TaskListener<Usuario> listener) {
        if (listeners == null)
            listeners = new ArrayList<TaskListener<Usuario>>();
        listeners.add(listener);
    }

    @Override
    protected Usuario doInBackground(Usuario... params) {
        try {
            RESTClient<Usuario> restClient = new RESTClient<Usuario>(Usuario.class, baseUrl + "/login/register");
            return restClient.post(params[0]);
        } catch (Exception e) {
            Log.e("CadastroAsyncTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        super.onPostExecute(usuario);
        for (TaskListener<Usuario> listener : listeners) {
            listener.taskCompleted(usuario);
        }
    }
}
