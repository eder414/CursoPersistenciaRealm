package curso.clases.cursopersistenciarealm.Interface;

import androidx.appcompat.app.AppCompatDialogFragment;

import curso.clases.cursopersistenciarealm.Models.Tiendas;

public interface IChangeDialog {
    public void ChangeDialog(AppCompatDialogFragment dialogFragment, Tiendas tienda,int productId);
}
