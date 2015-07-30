package br.com.garagesoft.notasfaceis.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Green01 on 24/07/2015.
 */
public class RESTClient<T> {

    private String url;

    private String token;

    private Gson gson;

    private Class<T> klass;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public RESTClient(Class<T> klass, String url) {
        this(klass, url, null);
    }

    public RESTClient(Class<T> klass, String url, String token) {
        this.klass = klass;
        this.url = url;
        this.token = token;
        this.gson = createGson();
    }

    public T get() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        if (token != null)
            httpGet.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        return convertFromJson(treatResponse(httpResponse));
    }

    public T get(String id) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url + "/" + id);
        if (token != null)
            httpGet.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        return convertFromJson(treatResponse(httpResponse));
    }

    public T post(Object obj) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json");
        if (token != null)
            httpPost.addHeader("Authorization", token);
        httpPost.setEntity(new StringEntity(convertToJson(obj)));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        return convertFromJson(treatResponse(httpResponse));
    }

    private String treatResponse(HttpResponse httpResponse) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    private String convertToJson(Object obj) {
        return gson.toJson(obj);
    }

    private T convertFromJson(String json) {
        return gson.fromJson(json, klass);
    }

    private Gson createGson() {
        return new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
    }
}
