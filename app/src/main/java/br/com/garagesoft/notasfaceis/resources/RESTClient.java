package br.com.garagesoft.notasfaceis.resources;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Green01 on 16/07/2015.
 */
public class RESTClient {

    private String host;

    private String resourceName;

    private String token;

    public RESTClient(String host, String resourceName) {
        this(host, resourceName, null);
    }

    public RESTClient(String host, String resourceName, String token) {
        this.host = host;
        this.resourceName = resourceName;
        this.token = token;
    }

    public String get(String id) throws IOException {
        HttpContext httpContext = new BasicHttpContext();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(host + "/" + resourceName + "/" + id);
        if (token != null && !token.isEmpty()) httpGet.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        return treatResponse(httpResponse);
    }

    public String post(Object obj) throws IOException {
        HttpContext httpContext = new BasicHttpContext();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(host + "/" + resourceName);
        if (obj != null) {
            httpPost.setEntity(new StringEntity(convertToJson(obj), "UTF8"));
            httpPost.setHeader("Content-Type", "application/json");
        }
        if (token != null && !token.isEmpty()) httpPost.addHeader("Authorization", token);
        HttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
        if (httpResponse.getStatusLine().getStatusCode() == 200)
            return treatResponse(httpResponse);
        else
            return null;
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
        })
                .create().toJson(obj);
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
