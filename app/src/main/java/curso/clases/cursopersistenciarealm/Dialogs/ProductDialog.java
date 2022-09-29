package curso.clases.cursopersistenciarealm.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Models.Tiendas;
import curso.clases.cursopersistenciarealm.R;

public class ProductDialog extends AppCompatDialogFragment {
    EditText editTextNombre,editTextPrecio,editTextCantidad;
    private DialogListener dialogListener;
    LinearLayout linearLayoutDialogProduct;
    int productoId;
    Tiendas tienda;
    public ProductDialog() {
    }

    public ProductDialog(Tiendas tienda,int productoId) {
        this.tienda = tienda;
        this.productoId = productoId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_product_dialog,null);



        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextPrecio = view.findViewById(R.id.editTextPrecio);
        editTextCantidad = view.findViewById(R.id.editTextCantidad);

        if(tienda != null){
            editTextNombre.setText(tienda.getProductos().get(productoId).getNombre());

            editTextPrecio.setText(""+tienda.getProductos().get(productoId).getPrecio());

            editTextCantidad.setText(""+tienda.getProductos().get(productoId).getCantidad());
        }
        else{
            Toast.makeText(getContext(),"no tiene datos: ",Toast.LENGTH_SHORT).show();
        }

        builder.setView(view).setTitle("Añadir Producto")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombre = editTextNombre.getText().toString();
                        String cantidad = editTextCantidad.getText().toString();
                        String precio = editTextPrecio.getText().toString();

                        dialogListener.GuardarProducto(nombre,precio,cantidad);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener =(DialogListener)  context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        //window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (width * 0.85), (int) (height * 0.50));

        window.setGravity(Gravity.CENTER);
    }
}
