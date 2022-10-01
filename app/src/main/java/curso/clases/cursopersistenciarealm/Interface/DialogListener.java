package curso.clases.cursopersistenciarealm.Interface;

import android.net.Uri;

import curso.clases.cursopersistenciarealm.Models.UpdateVariable;

public interface DialogListener {
    public void GuardarProducto(String editTextNombre, String editTextPrecio, String editTextCantidad, String path, UpdateVariable updateVariable);
}
