package br.com.garagesoft.notasfaceis.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.garagesoft.notasfaceis.models.Caderno;

/**
 * Created by Luiz on 30/07/2015.
 */
public class GetCadernosAsyncTask extends AsyncTask<String, Integer, Caderno[]> {

    private String baseUrl;

    private String token;

    private List<TaskListener<Caderno[]>> listeners;

    public GetCadernosAsyncTask(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    public void addGetCadernosTaskListener(TaskListener<Caderno[]> listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }

    @Override
    protected Caderno[] doInBackground(String... params) {
        try {
            RESTClient<Caderno[]> restClient = new RESTClient<>(Caderno[].class, baseUrl + "/cadernos", token);
            return restClient.get();
        } catch (Exception e) {
            Log.e("GetCadernosAsyncTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Caderno[] cadernos) {
        super.onPostExecute(cadernos);
        for (TaskListener<Caderno[]> listener : listeners) {
            listener.taskCompleted(cadernos);
        }
    }
}
