package br.com.garagesoft.notasfaceis.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.garagesoft.notasfaceis.models.Caderno;

/**
 * Created by Luiz on 30/07/2015.
 */
public class PostCadernoAsyncTask extends AsyncTask<Caderno, Integer, Caderno> {

    private String baseUrl;

    private String token;

    private List<TaskListener<Caderno>> listeners;

    public void addTaskListener(TaskListener<Caderno> listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public PostCadernoAsyncTask(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    @Override
    protected Caderno doInBackground(Caderno... params) {
        try {
            RESTClient<Caderno> restClient = new RESTClient<Caderno>(Caderno.class, baseUrl + "/cadernos", token);
            return restClient.post(params[0]);
        } catch (IOException e) {
            Log.e("PostCadernoAsyncTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Caderno caderno) {
        super.onPostExecute(caderno);
        for (TaskListener<Caderno> listener : listeners) {
            listener.taskCompleted(caderno);
        }
    }
}
