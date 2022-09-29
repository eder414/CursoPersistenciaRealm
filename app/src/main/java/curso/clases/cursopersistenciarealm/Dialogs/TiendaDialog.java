package curso.clases.cursopersistenciarealm.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Interface.IDialogTiendaListener;
import curso.clases.cursopersistenciarealm.R;

public class TiendaDialog extends AppCompatDialogFragment {
    EditText editTextNombreTienda,editTextDireccion;
    private IDialogTiendaListener iDialogTiendaListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_tienda_dialog,null);
        editTextNombreTienda = view.findViewById(R.id.editTextNombreTienda);
        editTextDireccion = view.findViewById(R.id.editTextDireccion);

        builder.setView(view).setTitle("Añadir Tienda")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombre = editTextNombreTienda.getText().toString();
                        String direccion = editTextDireccion.getText().toString();

                        iDialogTiendaListener.GuardarTienda(nombre,direccion);
                    }
                });

        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iDialogTiendaListener =(IDialogTiendaListener)  context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString() + "must implement IDialogTiendaListener");
        }
    }
}
