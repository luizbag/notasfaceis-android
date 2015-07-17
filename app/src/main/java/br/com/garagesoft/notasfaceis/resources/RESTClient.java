package br.com.garagesoft.notasfaceis.resources;

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
public class RESTClient {

    private String url;

    private String token;

    public RESTClient(String host, String resourceName) {
        this(host, resourceName, null);
    }

    public RESTClient(String host, String resourceName, String token) {
        this.url = host + "/" + resourceName;
        this.token = token;
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

    public String post(Object obj) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        if (obj != null) {
            httpPost.setEntity(new StringEntity(convertToJson(obj), "UTF8"));
            httpPost.setHeader("Content-Type", "application/json");
        }
        if (token != null && !token.isEmpty()) httpPost.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String put(Object obj) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        if (obj != null) {
            httpPut.setEntity(new StringEntity(convertToJson(obj), "UTF8"));
            httpPut.setHeader("Content-Type", "application/json");
        }
        if (token != null && !token.isEmpty()) httpPut.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpPut);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    public String delete(String id) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(url + "/" + id);
        if (token != null && !token.isEmpty()) httpDelete.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpDelete);
        if (httpResponse.getStatusLine().getStatusCode() == 200) return treatResponse(httpResponse);
        else return null;
    }

    private String convertToJson(Object obj) {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("tableName");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create().toJson(obj);
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

}
