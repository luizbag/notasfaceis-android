package br.com.garagesoft.notasfaceis.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Luiz on 30/07/2015.
 */
public class Nota extends SugarRecord<Nota> implements Serializable {

    @SerializedName("_id")
    private String idOrigem;

    private String titulo;

    private String conteudo;

    private Date data;

    @SerializedName("data_atualizado")
    private Date dataAtualizado;

    private Caderno caderno;

    public String getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(String idOrigem) {
        this.idOrigem = idOrigem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataAtualizado() {
        return dataAtualizado;
    }

    public void setDataAtualizado(Date dataAtualizado) {
        this.dataAtualizado = dataAtualizado;
    }

    public String toString() {
        return titulo;
    }

    public Caderno getCaderno() {
        return caderno;
    }

    public void setCaderno(Caderno caderno) {
        this.caderno = caderno;
    }
}
