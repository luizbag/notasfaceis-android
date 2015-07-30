package br.com.garagesoft.notasfaceis.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Luiz on 30/07/2015.
 */
public class Caderno extends SugarRecord<Caderno> implements Serializable {

    @SerializedName("_id")
    private String idOrigem;

    private String nome;

    private Date data;

    @Ignore
    private Nota[] notas;

    public String getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(String idOrigem) {
        this.idOrigem = idOrigem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String toString() {
        return nome;
    }

    public Nota[] getNotas() {
        return notas;
    }

    public void setNotas(Nota[] notas) {
        this.notas = notas;
    }
}