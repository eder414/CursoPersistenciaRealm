package curso.clases.cursopersistenciarealm.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Producto extends RealmObject {


    @PrimaryKey private int id;
    private int cantidad;
    private float precio;
    @Required String nombre;
    String imagen;

    public Producto(String nombre, String imagen, int id, int cantidad, float precio) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
