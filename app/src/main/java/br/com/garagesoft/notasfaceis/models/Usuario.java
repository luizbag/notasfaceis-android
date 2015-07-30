package br.com.garagesoft.notasfaceis.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Luiz on 16/07/2015.
 */
public class Usuario extends SugarRecord<Usuario> {
    private String email;

    private String password;

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

    public static Usuario findByemail(String email) {
        List<Usuario> usuarios = find(Usuario.class, "email=?", new String[]{email});
        if (usuarios.size() > 0)
            return usuarios.get(0);
        else
            return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
