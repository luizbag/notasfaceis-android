package br.com.garagesoft.notasfaceis.tasks;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Green01 on 16/07/2015.
 */
public class RESTClient<T> {

    private String url;

    private String token;

    private Class<T> klass;

    public RESTClient(String host, String resourceName, Class<T> klass) {
        this(host, resourceName, klass, null);
    }

    public RESTClient(String host, String resourceName, Class<T> klass, String token) {
        this.url = host + "/" + resourceName;
        this.token = token;
        this.klass = klass;
    }

    public String get(String id) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url + "/" + id);
        if (token != null && !token.isEmpty()) httpGet.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String get() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        if (token != null && !token.isEmpty()) httpGet.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String post(T model) throws IOException {
        String json = convertToJson(model);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        if (json != null && !json.isEmpty()) {
            httpPost.setEntity(new StringEntity(json, "UTF8"));
            httpPost.setHeader("Content-Type", "application/json");
        }
        if (token != null && !token.isEmpty()) httpPost.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String put(String json) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        if (json != null && !json.isEmpty()) {
            httpPut.setEntity(new StringEntity(json, "UTF8"));
            httpPut.setHeader("Content-Type", "application/json");
        }
        if (token != null && !token.isEmpty()) httpPut.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpPut);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String delete(Long id) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(url + "/" + id);
        if (token != null && !token.isEmpty()) httpDelete.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpDelete);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String treatResponse(HttpResponse httpResponse) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    private String convertToJson(T model) {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("tableName");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create().toJson(model);
    }

    private <M> M convertFromJson(String json, Class<M> klass) {
        return new GsonBuilder().create().fromJson(json, klass);
    }

}
