package br.com.garagesoft.notasfaceis.models;

import com.orm.SugarRecord;

/**
 * Created by Luiz on 16/07/2015.
 */
public class Usuario extends SugarRecord<Usuario> {
    private String email;

    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
