package curso.clases.cursopersistenciarealm.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Usuario extends RealmObject {
    @PrimaryKey
    private int id;
    @Required private String password;
    @Required private String nombre;
    @Required private String nick;

    public Usuario() {
    }

    public Usuario(int id, String password, String nombre, String nick) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
        this.nick = nick;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
