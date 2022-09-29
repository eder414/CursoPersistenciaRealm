package curso.clases.cursopersistenciarealm.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Tiendas extends RealmObject{
    @PrimaryKey
    int id;
    @Required
    String nombre;

    String direccion,imagen;
    public RealmList<Producto> productos;

    public Tiendas(int id, String nombre, String direccion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    public Tiendas(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public RealmList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(RealmList<Producto> productos) {
        this.productos = productos;
    }
}
