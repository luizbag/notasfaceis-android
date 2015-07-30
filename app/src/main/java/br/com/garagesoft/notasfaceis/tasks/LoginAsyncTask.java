package br.com.garagesoft.notasfaceis.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.garagesoft.notasfaceis.models.Usuario;

/**
 * Created by Luiz on 30/07/2015.
 */
public class LoginAsyncTask extends AsyncTask<Usuario, Integer, String> {

    private String baseUrl;

    private List<TaskListener<String>> listeners;

    public LoginAsyncTask(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void addLoginTaskListener(TaskListener<String> listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }

    @Override
    protected String doInBackground(Usuario... params) {
        try {
            RESTClient<String> restClient = new RESTClient<String>(String.class, baseUrl + "/login");
            return restClient.post(params[0]).replace("\"", "");
        } catch (Exception e) {
            Log.e("LoginAsyncTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);
        for (TaskListener<String> listener : listeners) {
            listener.taskCompleted(token);
        }
    }
}
