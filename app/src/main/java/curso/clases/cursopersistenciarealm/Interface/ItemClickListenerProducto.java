package curso.clases.cursopersistenciarealm.Interface;

import android.view.View;

import curso.clases.cursopersistenciarealm.Models.Tiendas;

public interface ItemClickListenerProducto {
    void onItemClick(Tiendas tienda, int position, View view);
}
