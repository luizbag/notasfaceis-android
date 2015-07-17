package br.com.garagesoft.notasfaceis.resources;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Green01 on 16/07/2015.
 */
public class AbstractResource<T extends SugarRecord<T>> {

    protected Context context;

    protected Class<T> klass;

    protected RESTClient restClient;

    public AbstractResource(String resourceName, String host, String token, Context context, Class<T> klass) {
        this.context = context;
        this.klass = klass;
        this.restClient = new RESTClient(host, resourceName, token);
    }

    public T save(T model) throws IOException {
        return convertFromJson(restClient.post(convertToJson(model)), klass);
    }

    public T update(T model) throws IOException {
        return convertFromJson(restClient.put(convertToJson(model)), klass);
    }

    public void delete(T model) throws IOException {
        restClient.delete(model.getId());
    }

    public T get(T model) throws IOException {
        return convertFromJson(restClient.get(model.getId().toString()), klass);
    }

    public ArrayList<T> query() throws IOException {
        return convertFromJson(restClient.get(), ArrayList.class);
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
