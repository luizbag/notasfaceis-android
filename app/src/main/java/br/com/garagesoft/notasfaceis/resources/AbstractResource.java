package br.com.garagesoft.notasfaceis.resources;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.garagesoft.notasfaceis.NotasFaceis;

/**
 * Created by Green01 on 16/07/2015.
 */
public class AbstractResource<T> {

    protected Context context;

    protected Class<T> klass;

    protected String resourceName;

    public AbstractResource(Context context, Class<T> klass, String resourceName) {
        this.context = context;
        this.klass = klass;
        this.resourceName = resourceName;
    }

    public void save(T model) {

    }

    public void update(T model) {
    }

    public T get(T model) {
        return model;
    }

    public List<T> query(T model) {
        return null;
    }

    public void delete(T model) {

    }
}
