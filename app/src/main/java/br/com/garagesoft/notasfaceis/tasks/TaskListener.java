package br.com.garagesoft.notasfaceis.tasks;

/**
 * Created by Luiz on 30/07/2015.
 */
public interface TaskListener<T> {

    void taskCompleted(T t);
}
