package curso.clases.cursopersistenciarealm.Dialogs;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.loader.content.CursorLoader;

import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Models.Tiendas;
import curso.clases.cursopersistenciarealm.Models.UpdateVariable;
import curso.clases.cursopersistenciarealm.R;
import curso.clases.cursopersistenciarealm.Util.Utilities;

public class ProductDialog extends AppCompatDialogFragment implements View.OnClickListener{
    EditText editTextNombre,editTextPrecio,editTextCantidad;
    private DialogListener dialogListener;
    LinearLayout linearLayoutDialogProduct;
    int productoId;
    Tiendas tienda;

    Button editTextImagen;

    ImageView imageProduct;

    Uri uriImage;
    String pathImage;
    UpdateVariable updateVariable;
    String path;
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

        editTextImagen = view.findViewById(R.id.editTextImagen);
        editTextImagen.setOnClickListener(this);

        imageProduct = view.findViewById(R.id.imageProduct);
        UpdateVariable updateVariable = new UpdateVariable();

        if(tienda != null){
            editTextNombre.setText(tienda.getProductos().get(productoId).getNombre());

            editTextPrecio.setText(""+tienda.getProductos().get(productoId).getPrecio());

            editTextCantidad.setText(""+tienda.getProductos().get(productoId).getCantidad());

            if( tienda.getProductos().get(productoId).getImagen() != null && !tienda.getProductos().get(productoId).getImagen().equals("")){
                Uri uri = Uri.parse(tienda.getProductos().get(productoId).getImagen());
                imageProduct.setImageURI(uri);
                path = tienda.getProductos().get(productoId).getImagen();
            }else{
                path = null;
            }
            updateVariable.setUpdateVariable(1);
            updateVariable.setProductId(productoId);
        }
        else{
            Toast.makeText(getContext(),"no tiene datos: ",Toast.LENGTH_SHORT).show();
            updateVariable.setUpdateVariable(0);
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

                        dialogListener.GuardarProducto(nombre,precio,cantidad,path,updateVariable);
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

    @Override
    public void onClick(View view) {
        pickPhotoFromGallery();
    }

    private void pickPhotoFromGallery() {
        //check permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //permiso denegado
                String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show ppopup for runtime permission
                requestPermissions(permissions, Utilities.PERMISSION_CODE);
            }
            else{
                pickImageFromGallery();
            }
        }
        else{
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,Utilities.IMAG_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Utilities.RESUL_OK && requestCode == Utilities.IMAG_PICK_CODE){
            // set image to imageview
            imageProduct.setImageURI(data.getData());
            this.uriImage = data.getData();
            this.path = getRealPathFromURI_API11to18(getContext(),uriImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Utilities.PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(getContext(),"Permiso denegado...",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

}
