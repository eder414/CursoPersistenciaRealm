package curso.clases.cursopersistenciarealm.Interface;

import android.view.View;

import curso.clases.cursopersistenciarealm.Models.Tiendas;

public interface ItemClickListener {
    void onItemClick(Tiendas tienda, int position, View view);
}
